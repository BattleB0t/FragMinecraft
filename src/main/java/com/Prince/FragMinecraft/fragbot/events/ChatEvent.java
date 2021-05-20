package com.Prince.FragMinecraft.fragbot.events;

import com.Prince.FragMinecraft.minecraftevents.BotEvent;
import com.Prince.FragMinecraft.minecraftevents.Listener;
import com.Prince.FragMinecraft.minecraftevents.events.MinecraftChatEvent;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatEvent implements Listener {
    @BotEvent
    public void onChatMessage(MinecraftChatEvent e) {
        System.out.println("Chat Message Recieved: " + e.getMessageText());

        Pattern partyInvitePattern = Pattern.compile("-----------------------------\\\\n(?:\\[[a-zA-Z+]+\\] *)?(.+) has",Pattern.MULTILINE);
        Matcher getIgn = partyInvitePattern.matcher(e.getMessageText());
        String ign = null;
        while (getIgn.find()) {
            for (int i = 1; i <= getIgn.groupCount(); i++) {
                ign=getIgn.group(i);
            }
        }
        if(ign==null){
            return;
        }
        System.out.println("Recieved party invite from user: "+ign);
        e.getBotInstance().getClient().send(new ClientChatPacket("/party join "+ign));
        new Thread(()->{
            try {
                Thread.sleep(3500);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            e.getBotInstance().getClient().send(new ClientChatPacket("/party leave"));
        }).start();

    }
}
