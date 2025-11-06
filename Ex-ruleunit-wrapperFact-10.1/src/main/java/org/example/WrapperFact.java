package org.example;

public class WrapperFact {

    private Object actualFact;

    public WrapperFact(Object actualFact) {
        this.actualFact = actualFact;
    }

    public Object getActualFact() {
        return actualFact;
    }

    public void setActualFact(Object actualFact) {
        this.actualFact = actualFact;
    }

    @Override
    public String toString() {
        return "WrapperFact{" +
                "actualFact=" + actualFact +
                '}';
    }
}
