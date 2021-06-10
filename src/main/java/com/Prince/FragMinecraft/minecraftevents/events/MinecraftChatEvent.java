package com.Prince.FragMinecraft.minecraftevents.events;

import com.Prince.FragMinecraft.fragbot.FragBot;
import com.Prince.FragMinecraft.minecraftevents.Event;
import com.Prince.FragMinecraft.utils.ChatUtils;
import com.github.steveice10.packetlib.Session;

import java.util.UUID;

public class MinecraftChatEvent implements Event {
    private String rawText;
    private String messageText;
    private FragBot botInstance;
    public MinecraftChatEvent(String rawText, FragBot botInstance){
        this.rawText = rawText;
        this.botInstance = botInstance;
        this.messageText = ChatUtils.parseChatMessage(rawText);
    }
    public FragBot getBotInstance(){
        return botInstance;
    }
    public String getRawText(){
        return rawText;
    }
    public String getMessageText(){
        return messageText;
    }
}
