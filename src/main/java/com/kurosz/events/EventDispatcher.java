package com.kurosz.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public abstract class EventDispatcher<T extends Event> {
    protected List<FxmlEventHandler<T>> handlers;

    private static final Logger logger = LoggerFactory.getLogger(EventDispatcher.class);

    public EventDispatcher(){
        this.handlers = new LinkedList<>();
    }

    public void addHandler(FxmlEventHandler<T> handler){
        logger.info("Add handler {}", handler);
        handlers.add(handler);
    }

    public void removeHandler(FxmlEventHandler<T> handler){
        logger.info("Remove handler {}", handler);
        handlers.remove(handler);
    }

    protected void dispatchEvent(T event){
        logger.info("Dispatch event {}", event);
        for (FxmlEventHandler<T> handler: handlers){
            handler.handle(event);
        }
    }
}
