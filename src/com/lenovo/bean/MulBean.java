package com.lenovo.bean;

/**
 * Created by Administrator on 2017/5/20.
 */
public class MulBean {
    //倍数
    private Integer beishu;
    //次数
    private Integer cishu;
    //第几次
    private Integer id;

    public MulBean() {
    }

    public MulBean(Integer beishu, Integer cishu, Integer id) {
        this.beishu = beishu;
        this.cishu = cishu;
        this.id = id;
    }

    public Integer getBeishu() {
        return beishu;
    }

    public void setBeishu(Integer beishu) {
        this.beishu = beishu;
    }

    public Integer getCishu() {
        return cishu;
    }

    public void setCishu(Integer cishu) {
        this.cishu = cishu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
