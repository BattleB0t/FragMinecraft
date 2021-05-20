package com.Prince.FragMinecraft.fragbot;

import com.Prince.FragMinecraft.fragbot.events.ChatEvent;
import com.Prince.FragMinecraft.fragbot.events.JoinEvent;
import com.Prince.FragMinecraft.minecraftevents.EventHandler;
import com.Prince.FragMinecraft.minecraftevents.events.MinecraftChatEvent;
import com.Prince.FragMinecraft.minecraftevents.events.ServerJoinEvent;
import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.auth.service.AuthenticationService;
import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoHandler;
import com.github.steveice10.mc.protocol.data.status.handler.ServerPingTimeHandler;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import jdk.internal.util.xml.impl.Input;

import java.net.Proxy;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class FragBot {
    private final String email;
    private final String password;
    private final String host;
    private final int port;
    private final EventHandler eventHandler;
    private final String botname;
    private Session client;
    private FragBot fragBot;
    public FragBot(String email, String password, String host, int port, String botname) {
        this.email = email;
        this.password = password;
        this.host = host;
        this.port = port;
        this.eventHandler = new EventHandler();
        this.botname = botname;
        this.fragBot = this;
        loadDefaultEvents();
    }
    public EventHandler getEventHandler(){
        return eventHandler;
    }
    public void start(){
        getServerStatus();
        login();
    }
    public void log(){
        System.out.println("");
    }
    public void getServerStatus(){
        SessionService sessionService = new SessionService();
        sessionService.setProxy(Proxy.NO_PROXY);

        MinecraftProtocol protocol = new MinecraftProtocol();
        Session client = new TcpClientSession(host, port, protocol, null);
        client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
        client.setFlag(MinecraftConstants.SERVER_INFO_HANDLER_KEY, (ServerInfoHandler) (session, info) -> {
            System.out.println("Version: " + info.getVersionInfo().getVersionName()
                    + ", " + info.getVersionInfo().getProtocolVersion());
            System.out.println("Player Count: " + info.getPlayerInfo().getOnlinePlayers()
                    + " / " + info.getPlayerInfo().getMaxPlayers());
            System.out.println("Players: " + Arrays.toString(info.getPlayerInfo().getPlayers()));
            System.out.println("Description: " + info.getDescription());
            System.out.println("Icon: " + Arrays.toString(info.getIconPng()));
        });

        client.setFlag(MinecraftConstants.SERVER_PING_TIME_HANDLER_KEY, (ServerPingTimeHandler) (session, pingTime) ->
                System.out.println("Server ping took " + pingTime + "ms"));

        client.connect();
        while(client.isConnected()) {
            try {
                Thread.sleep(5);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void login() {
        MinecraftProtocol protocol;
        try {
            AuthenticationService authService = new AuthenticationService();
            authService.setUsername(email);
            authService.setPassword(password);
            authService.setProxy(Proxy.NO_PROXY);
            authService.login();

            protocol = new MinecraftProtocol(authService.getSelectedProfile(), authService.getAccessToken());
            System.out.println("Successfully authenticated user.");
        } catch(RequestException e) {
            e.printStackTrace();
            return;
        }

        SessionService sessionService = new SessionService();
        sessionService.setProxy(Proxy.NO_PROXY);
        Session client = new TcpClientSession(host, port, protocol, null);
        this.client = client;
        client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
        client.addListener(new SessionAdapter() {
            @Override
            public void packetReceived(PacketReceivedEvent event) {
                if(event.getPacket() instanceof ServerJoinGamePacket){
                    ServerJoinGamePacket packet = event.getPacket();
                    getEventHandler().callEvent(new ServerJoinEvent(packet,fragBot));
                }
                if(event.getPacket() instanceof ServerChatPacket) {
                    ServerChatPacket packet = event.getPacket();
                    String message = packet.getMessage().toString();
                    getEventHandler().callEvent(new MinecraftChatEvent(message,fragBot));
                }
            }

            @Override
            public void disconnected(DisconnectedEvent event) {
                System.out.println("Disconnected: " + event.getReason());
                if(event.getCause() != null) {
                    event.getCause().printStackTrace();
                }
            }
        });
        client.connect();
        new Thread(()->{
            while (true) {
                Scanner sc = new Scanner(System.in);
                String command = sc.nextLine();
                getClient().send(new ClientChatPacket(command));
            }
        }).start();
    }
    private void loadDefaultEvents() {
        getEventHandler().registerEvents(new ChatEvent());
        getEventHandler().registerEvents(new JoinEvent());
    }
    public Session getClient(){
        return client;
    }
}
