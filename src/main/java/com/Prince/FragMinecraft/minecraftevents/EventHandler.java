package com.Prince.FragMinecraft.minecraftevents;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventHandler {
    private ArrayList<CallableEvent> events;
    public EventHandler(){
        events = new ArrayList<>();
    }
    public EventHandler registerEvents(Listener listener){
        for(Method func : listener.getClass().getDeclaredMethods()){
            if(func.isAnnotationPresent(BotEvent.class)){
                if(func.getParameterTypes().length!=1){
                    throw new IllegalArgumentException("Argument length of one exceeded, Class: "+listener.getClass().getName()+" Argument Length: "+func.getParameterTypes().length);
                }
                EventType eventType = EventType.getTypeFromClass(func.getParameterTypes()[0]);
                if(eventType==null){
                    throw new IllegalArgumentException("Passed in invalid object, Class: "+listener.getClass().getName()+" Object: "+func.getParameterTypes()[0]);
                }
                if(eventType == EventType.FragBotCheckEvent) {
                    if (!func.getReturnType().equals(Boolean.class)&&!func.getReturnType().equals(boolean.class)) {
                        throw new IllegalArgumentException("Function needs to return a boolean, Class: " + listener.getClass().getName() + " Object: " + func.getParameterTypes()[0]);
                    }
                }
                events.add(new CallableEvent(listener,func,eventType));
                System.out.println("Added event hook: "+func.getName());

            }
        }
        return  this;
    }
    public List<Object> callEvent(Event e){
        String eventName = e.getName();
        ArrayList<Object> returned = new ArrayList<>();
        events.forEach((callableEvent) -> {
            if(callableEvent.getEventType().equals(EventType.valueOf(eventName))){
                returned.add(callableEvent.call(e));
            }
        });
        return returned;
    }
}
