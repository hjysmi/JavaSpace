package com.lenovo.bean;

/**
 * Created by Administrator on 2017/4/30.
 */
public class AvgBean {
    public Double avg;
    public Integer id;

    public AvgBean() {
    }

    @Override
    public String toString() {
        return "AvgBean{" +
                "avg=" + avg +
                ", id=" + id +
                '}';
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AvgBean(Double avg, Integer id) {
        this.avg = avg;
        this.id = id;
    }

}
