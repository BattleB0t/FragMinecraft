package com.Prince.FragMinecraft.fragbot;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import com.Prince.FragMinecraft.fragbot.events.ChatEvent;
import com.Prince.FragMinecraft.fragbot.events.JoinEvent;
import com.Prince.FragMinecraft.minecraftevents.EventHandler;
import com.Prince.FragMinecraft.minecraftevents.events.FragBotBannedEvent;
import com.Prince.FragMinecraft.minecraftevents.events.MinecraftChatEvent;
import com.Prince.FragMinecraft.minecraftevents.events.ServerJoinEvent;
import com.Prince.FragMinecraft.utils.ChatUtils;
import com.Prince.FragMinecraft.utils.EmbedBuilder;
import com.github.steveice10.mc.auth.data.GameProfile;
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

import java.net.Proxy;
import java.util.Arrays;
import java.util.Scanner;

public class FragBot {
    private final String email;
    private final String password;
    private final String host;
    private final int port;
    private final EventHandler eventHandler;
    private final FragBotConfig config;
    private Session client;
    private Session baseClient;
    private FragBot fragBot;
    private QueueHandler queueHandler;
    private WebhookClient webhookClient;
    private String botName;
    public FragBot(String email, String password, String host, int port, FragBotConfig config) {
        this.email = email;
        this.password = password;
        this.host = host;
        this.port = port;
        this.eventHandler = new EventHandler();
        this.config = config;
        this.fragBot = this;
        this.queueHandler = new QueueHandler(fragBot);
        this.webhookClient = new WebhookClientBuilder(config.getWebhookUrl()).build();
        loadDefaultEvents();
    }
    public WebhookClient getWebhookClient(){
        return webhookClient;
    }
    public QueueHandler getQueueHandler(){
        return queueHandler;
    }
    public FragBotConfig getConfig(){
        return config;
    }
    public EventHandler getEventHandler(){
        return eventHandler;
    }
    public void start(){
        getServerStatus();
        login();
    }
    public String getBotName() {
        return botName;
    }
    public void sendMessage(String message){
        client.send(new ClientChatPacket(message));
    }
    public void getServerStatus(){


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
                if(botName==null){
                    botName = ((GameProfile) client.getFlag("profile")).getName();
                }
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
                String reason = ChatUtils.parseChatMessage(event.getReason());
                System.out.println("Disconnected: " + reason);
                if(reason.contains("banned")){
                    getWebhookClient().send(new EmbedBuilder(fragBot).setDescription("Bot has been BANNED fuck u hypixel").build());
                    getEventHandler().callEvent(new FragBotBannedEvent(fragBot));
                }else{
                    getWebhookClient().send(new EmbedBuilder(fragBot).setDescription("Bot has been disconnected, reconnecting...").build());
                    System.out.println("Reconnecting in 5 seconds");
                    JoinEvent.sent = false;
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    start();
                }
                if(event.getCause() != null) {
                    event.getCause().printStackTrace();
                }
            }
        });
        client.connect();
        while(client.isConnected()) {
            try {
                Thread.sleep(5);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void loadDefaultEvents() {
        getEventHandler().registerEvents(new ChatEvent());
        getEventHandler().registerEvents(new JoinEvent());
    }
    public void log(String msg){
        System.out.println("["+getConfig().getBotName()+"] "+msg);
    }
    public Session getClient(){
        return client;
    }
}
