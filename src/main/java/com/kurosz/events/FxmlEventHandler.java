package com.kurosz.events;

public interface FxmlEventHandler<T extends Event> {

    void handle(T event);
}
