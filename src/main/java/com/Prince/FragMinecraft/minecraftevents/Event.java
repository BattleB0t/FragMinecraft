package com.Prince.FragMinecraft.minecraftevents;

public abstract interface Event {
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
