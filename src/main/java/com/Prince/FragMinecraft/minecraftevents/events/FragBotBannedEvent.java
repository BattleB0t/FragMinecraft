package com.Prince.FragMinecraft.minecraftevents.events;

import com.Prince.FragMinecraft.fragbot.FragBot;
import com.Prince.FragMinecraft.minecraftevents.Event;

public class FragBotBannedEvent implements Event {
    private FragBot client;
    public FragBotBannedEvent(FragBot client){
        this.client = client;
    }
}
