package com.Prince.FragMinecraft.minecraftevents.events;

import com.Prince.FragMinecraft.fragbot.FragBot;
import com.Prince.FragMinecraft.minecraftevents.Event;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;

public class ServerJoinEvent extends Event {
    private FragBot botInstance;
    private ServerJoinGamePacket gamePacket;
    public ServerJoinEvent(ServerJoinGamePacket packet, FragBot client){
        this.gamePacket = packet;
        this.botInstance = client;
    }

    public FragBot getBotInstance() {
        return botInstance;
    }

    public ServerJoinGamePacket getGamePacket() {
        return gamePacket;
    }
}
