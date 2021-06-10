package com.Prince.FragMinecraft.minecraftevents.events;

import com.Prince.FragMinecraft.fragbot.FragBot;
import com.Prince.FragMinecraft.minecraftevents.Event;

public class FragBotCheckEvent implements Event {
    private String ign;
    private FragBot client;
    public FragBotCheckEvent(String ign, FragBot client){
        this.ign = ign;
        this.client = client;
    }

    public FragBot getClient() {
        return client;
    }

    public String getIgn() {
        return ign;
    }
}
