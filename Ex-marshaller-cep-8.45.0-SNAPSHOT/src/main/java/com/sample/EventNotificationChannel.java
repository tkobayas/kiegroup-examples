package com.sample;

import org.kie.api.runtime.Channel;

import java.util.HashMap;
import java.util.Map;

public class EventNotificationChannel implements Channel {

    private Map<String, Event> eventMap = new HashMap<>();
   
    @Override
    public void send(Object object) {
        System.out.println("send : " + object);
        Event event = (Event)object;
        eventMap.put(event.getEventId(), event);
    }

    public Event getClosedEvent(String eventId) {
        Event event = eventMap.get(eventId);
        if (event != null && event.getStatus().equals("closed")) {
            return event;
        } else {
            return null;
        }
    }

}