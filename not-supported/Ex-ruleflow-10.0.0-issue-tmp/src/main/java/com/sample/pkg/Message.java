package com.sample.pkg;

public class Message {

    private int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Message [count=" + count + "]";
    }

}