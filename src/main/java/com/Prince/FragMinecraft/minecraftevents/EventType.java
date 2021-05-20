package com.Prince.FragMinecraft.minecraftevents;

public enum EventType {
    MinecraftChatEvent,ServerJoinEvent;
    public static EventType getTypeFromClass(Class<?> clazz) {
        try{
            return EventType.valueOf(clazz.getSimpleName());
        }catch(IllegalArgumentException e){
            return null;
        }
    }
}
