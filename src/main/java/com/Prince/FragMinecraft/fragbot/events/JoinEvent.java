package com.Prince.FragMinecraft.fragbot.events;

import com.Prince.FragMinecraft.minecraftevents.BotEvent;
import com.Prince.FragMinecraft.minecraftevents.Listener;
import com.Prince.FragMinecraft.minecraftevents.events.ServerJoinEvent;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;

public class JoinEvent implements Listener {
    @BotEvent
    public void onJoin(ServerJoinEvent event) {
        event.getBotInstance().getClient().send(new ClientChatPacket("/achat §a"));
    }
}
