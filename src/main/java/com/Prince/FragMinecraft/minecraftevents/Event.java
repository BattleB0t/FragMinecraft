package com.Prince.FragMinecraft.minecraftevents;

public abstract class Event {
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
