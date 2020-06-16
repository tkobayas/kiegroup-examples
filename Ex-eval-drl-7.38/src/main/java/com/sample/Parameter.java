package com.sample;

import java.util.ArrayList;
import java.util.List;

public class Parameter {

    private List<String> list = new ArrayList<>();

    private String item;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Parameter [list=" + list + ", item=" + item + "]";
    }

}
