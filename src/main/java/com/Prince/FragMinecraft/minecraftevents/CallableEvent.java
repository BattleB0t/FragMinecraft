package com.Prince.FragMinecraft.minecraftevents;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CallableEvent {
    private Listener listener;
    private Method func;
    private EventType eventType;
    public CallableEvent(Listener listener, Method func, EventType eventType){
        this.listener = listener;
        this.func = func;
        this.eventType = eventType;
    }

    public Listener getListener() {
        return listener;
    }

    public Method getFunc() {
        return func;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Object call(Event e){
        try {
            return func.invoke(listener,e);
        } catch (IllegalAccessException | InvocationTargetException illegalAccessException) {
            illegalAccessException.printStackTrace();
            return null;
        }
    }
}
