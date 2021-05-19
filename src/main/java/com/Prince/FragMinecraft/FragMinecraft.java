package com.Prince.FragMinecraft;

import com.Prince.FragMinecraft.MinecraftEvents.BotEvent;
import com.Prince.FragMinecraft.MinecraftEvents.EventHandler;
import com.Prince.FragMinecraft.MinecraftEvents.Listener;
import com.Prince.FragMinecraft.MinecraftEvents.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class FragMinecraft {
    public static void main(String[] args){
        new EventHandler().registerEvents(new Test());
    }
}
