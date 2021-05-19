package com.Prince.FragMinecraft.MinecraftEvents;

import java.lang.reflect.Method;
import java.util.HashMap;

public class EventHandler {
    private HashMap<Method,MinecraftEvent> events;
    public EventHandler(){
        events = new HashMap<Method,MinecraftEvent>();
    }
    public EventHandler registerEvents(Listener listener){
        for(Method func : listener.getClass().getDeclaredMethods()){
            if(func.isAnnotationPresent(BotEvent.class)){
                if(func.getParameterTypes().length!=1){
                    throw new IllegalArgumentException("Argument length of one exceeded, Class: "+listener.getClass().getName()+" Argument Length: "+func.getParameterTypes().length);
                }
                if(!func.getParameterTypes()[0].isAssignableFrom(MinecraftEvent.class)){
                    throw new IllegalArgumentException("Passed in invalid object, Class: "+listener.getClass().getName()+" Object: "+func.getParameterTypes()[0]);
                }
            }
        }
        return  this;
    }
}
