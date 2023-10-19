package com.sample;

import org.kie.api.definition.type.Role;

import java.io.Serializable;


@Role(Role.Type.EVENT)
public class Event implements Serializable {

    private String eventId;
    private String status;
    private String reason;

    public Event() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Event(String eventId, String status, String reason) {
        super();
        this.eventId = eventId;
        this.status = status;
        this.reason = reason;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Event [eventId=" + eventId + ", status=" + status + ", reason=" + reason + "]";
    }

}