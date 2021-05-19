package com.Prince.FragMinecraft.MinecraftEvents;

public abstract class MinecraftEvent {
    private String name;

    public String getName() {
        if(name == null){
            return this.getClass().getSimpleName();
        }
        return name;
    }
}
