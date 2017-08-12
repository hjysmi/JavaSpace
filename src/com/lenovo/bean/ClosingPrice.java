package com.lenovo.bean;

/**
 * Created by Administrator on 2017/4/29.
 */
public class ClosingPrice {
    private Integer id;
    private Integer closing;
    private Integer resultId;
    private String qihao;
    private String result;
    private Double ma20;
    private Double ma60;
    @Override
    public String toString() {
        return "ClosingPrice{" +
                "id=" + id +
                ", closing=" + closing +
                ", resultId=" + resultId +
                ", qihao='" + qihao + '\'' +
                ", result='" + result + '\'' +
                ", ma20=" + ma20 +
                ", ma60=" + ma60 +
                '}';
    }

    public Double getMa20() {
        return ma20;
    }

    public void setMa20(Double ma20) {
        this.ma20 = ma20;
    }

    public Double getMa60() {
        return ma60;
    }

    public void setMa60(Double ma60) {
        this.ma60 = ma60;
    }



    public ClosingPrice(Integer id, Integer closing, Integer resultId, String qihao, String result) {
        this.id = id;
        this.closing = closing;
        this.resultId = resultId;
        this.qihao = qihao;
        this.result = result;
    }

    public ClosingPrice(Integer closing, Integer resultId, String qihao, String result) {
        this.closing = closing;
        this.resultId = resultId;
        this.qihao = qihao;
        this.result = result;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClosing() {
        return closing;
    }

    public void setClosing(Integer closing) {
        this.closing = closing;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public ClosingPrice() {

    }

}
