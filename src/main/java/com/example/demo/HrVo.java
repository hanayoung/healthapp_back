package com.example.demo;

public class HrVo {

    private String time;
    private int value;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "HrVo{"
                +"value='"
                +value
                +'\''
                +", time='"
                +time
                +'\''
                +'}';
    }
}
