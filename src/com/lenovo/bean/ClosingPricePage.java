package com.lenovo.bean;

/**
 * Created by Administrator on 2017/4/30.
 */
public class ClosingPricePage {
    private int begin;
    private int end;

    @Override
    public String toString() {
        return "ClosingPricePage{" +
                "begin=" + begin +
                ", end=" + end +
                '}';
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public ClosingPricePage(int begin, int end) {

        this.begin = begin;
        this.end = end;
    }
}
