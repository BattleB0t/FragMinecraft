package com.Prince.FragMinecraft;

import com.Prince.FragMinecraft.fragbot.FragBot;
import com.Prince.FragMinecraft.fragbot.FragBotConfig;
import com.Prince.FragMinecraft.fragbot.events.ChatEvent;
import com.Prince.FragMinecraft.minecraftevents.EventHandler;
import com.Prince.FragMinecraft.minecraftevents.events.MinecraftChatEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragMinecraft {
    public static void main(String[] args){
        FragBot bot = new FragBot("emal","password","mc.hypixel.net",25565,new FragBotConfig(3500,"PrinceBot"));
        bot.start();
    }
}
