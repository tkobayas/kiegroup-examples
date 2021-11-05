package com.sample;

public class MyGlobal {

    private int ageA;
    private int ageB;
    private int ageC;

    public MyGlobal() {
    }

    public MyGlobal(int ageA, int ageB, int ageC) {
        this.ageA = ageA;
        this.ageB = ageB;
        this.ageC = ageC;
    }

    public int getAgeA() {
        return ageA;
    }

    public void setAgeA(int ageA) {
        this.ageA = ageA;
    }

    public int getAgeB() {
        return ageB;
    }

    public void setAgeB(int ageB) {
        this.ageB = ageB;
    }

    public int getAgeC() {
        return ageC;
    }

    public void setAgeC(int ageC) {
        this.ageC = ageC;
    }

}
