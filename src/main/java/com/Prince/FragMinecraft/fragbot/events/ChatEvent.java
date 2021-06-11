package com.Prince.FragMinecraft.fragbot.events;

import com.Prince.FragMinecraft.minecraftevents.BotEvent;
import com.Prince.FragMinecraft.minecraftevents.Listener;
import com.Prince.FragMinecraft.minecraftevents.events.FragBotCheckEvent;
import com.Prince.FragMinecraft.minecraftevents.events.MinecraftChatEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatEvent implements Listener {
    @BotEvent
    public void onChatMessage(MinecraftChatEvent e) {
        e.getBotInstance().log("Chat Message Received: " + e.getMessageText());

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
        List<Object> checks = e.getBotInstance().getEventHandler().callEvent(new FragBotCheckEvent(ign,e.getBotInstance()));
        for(Object check : checks){
            Boolean checkbool = (Boolean) check;
            if(!checkbool){
                e.getBotInstance().log("Check denied from player: "+ign);
                return;
            }
        }
        e.getBotInstance().log("Received party invite from player: "+ign);
        e.getBotInstance().getQueueHandler().addToQueue(ign);
    }
}
