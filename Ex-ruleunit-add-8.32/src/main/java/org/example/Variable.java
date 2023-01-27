package org.example;

public class Variable {

    private String id;

    public Variable(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Variable [id=" + id + "]";
    }

}
