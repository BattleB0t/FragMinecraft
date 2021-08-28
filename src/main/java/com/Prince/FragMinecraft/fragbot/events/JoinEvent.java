package com.Prince.FragMinecraft.fragbot.events;

import com.Prince.FragMinecraft.minecraftevents.BotEvent;
import com.Prince.FragMinecraft.minecraftevents.Listener;
import com.Prince.FragMinecraft.minecraftevents.events.ServerJoinEvent;
import com.Prince.FragMinecraft.utils.EmbedBuilder;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;

public class JoinEvent implements Listener {
    public static boolean sent = false;
    @BotEvent
    public void onJoin(ServerJoinEvent event) {
        if(!sent) {
            if(!event.getBotInstance().testMode) {
                event.getBotInstance().getWebhookClient().send(new EmbedBuilder(event.getBotInstance()).setDescription("Bot: `" + event.getBotInstance().getBotName() + "` has joined server with ip: `" + event.getBotInstance().getClient().getHost() + "`").build());
            }
            sent = true;
        }
        event.getBotInstance().getClient().send(new ClientChatPacket("/lobby housing"));
    }
}
