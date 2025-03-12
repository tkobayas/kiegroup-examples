package com.sample;

import java.util.ArrayList;
import java.util.List;

public class InputData {

    private String id;

    private List<Span> spanList;

    public InputData() {}

    public InputData(String id) {
        this.id = id;
    }

    public List<Span> getSpanList() {
        return spanList;
    }

    public void setSpanList(List<Span> spanList) {
        this.spanList = spanList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
