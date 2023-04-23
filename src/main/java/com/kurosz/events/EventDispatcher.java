package com.kurosz.events;

import java.util.LinkedList;
import java.util.List;

public abstract class EventDispatcher<T extends Event> {
    protected List<EventHandler<T>> handlers;

    public EventDispatcher(){
        this.handlers = new LinkedList<>();
    }

    public void addHandler(EventHandler<T> handler){
        handlers.add(handler);
    }

    public void removeHandler(EventHandler<T> handler){
        handlers.remove(handler);
    }

    protected void dispatchEvent(T event){
        for (EventHandler<T> handler: handlers){
            handler.handle(event);
        }
    }
}
