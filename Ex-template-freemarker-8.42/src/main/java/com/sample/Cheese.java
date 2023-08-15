package com.sample;

import java.io.Serializable;

public class Cheese implements Serializable {

    private String type;

    public Cheese() {}

    public Cheese(final String type) {
        super();
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

}
