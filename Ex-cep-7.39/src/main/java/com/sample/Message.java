package com.sample;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String text;

    public Message(long id, String text) {
        super();
        this.id = id;
        this.text = text;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "Message [id=" + id + ", text=" + text + "]";
    }



}
