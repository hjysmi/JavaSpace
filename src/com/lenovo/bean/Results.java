package com.lenovo.bean;

/**
 * Created by Administrator on 2017/4/15.
 */
public class Results {
    private Integer id;
    private String qihao;//开奖期号
    private String result;//开奖结果
    private Integer yAxis;//y坐标
    private Integer xAxis;//x坐标
    private Integer ma;

    @Override
    public String toString() {
        return "Results{" +
                "id=" + id +
                ", qihao='" + qihao + '\'' +
                ", result='" + result + '\'' +
                ", yAxis=" + yAxis +
                ", xAxis=" + xAxis +
                ", ma=" + ma +
                '}';
    }

    public Integer getyAxis() {
        return yAxis;
    }

    public void setyAxis(Integer yAxis) {
        this.yAxis = yAxis;
    }

    public Integer getxAxis() {
        return xAxis;
    }

    public void setxAxis(Integer xAxis) {
        this.xAxis = xAxis;
    }

    public Integer getMa() {
        return ma;
    }

    public void setMa(Integer ma) {
        this.ma = ma;
    }

    public Results() {
    }
    public Results(String qihao, String result) {
        super();
        this.qihao = qihao;
        this.result = result;
    }
    public  Results(String result, String qihao, Integer id) {
        this.id = id;
        this.qihao = qihao;
        this.result = result;
    }
    /*public  Results(Integer id, String qihao, String result) {
        this.id = id;
        this.qihao = qihao;
        this.result = result;
    }*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQihao() {
        return qihao;
    }

    public void setQihao(String qihao) {
        this.qihao = qihao;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
