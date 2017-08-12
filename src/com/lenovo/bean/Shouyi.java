package com.lenovo.bean;

import com.lenovo.util.Utils;

import java.util.List;

public class Shouyi {
    private double touziedu=0;//投资金额
    private double zhongjiangjine=0;//中奖金额
    /**
     * 本期投注方案
     */
    private List<String> touzifangan;
    /**
     * 下期投注方案
     */
    private List<String> touzifanganXiaQi;
    /**
     * 投资收益
     */
    private double touzhisouyi;

    /**
     * 判断是否赚钱
     * @return
     */
    public boolean isMakeMoney(){
        return getTouzhisouyi()>0;
    }

    public double getTouzhisouyi() {
        return Utils.sub(zhongjiangjine,touziedu);
    }


    public List<String> getTouzifanganXiaQi() {
        return touzifanganXiaQi;
    }

    public void setTouzifanganXiaQi(List<String> touzifanganXiaQi) {
        this.touzifanganXiaQi = touzifanganXiaQi;
    }

    public double getTouziedu() {
        return Double.valueOf(Utils.get2Value(touziedu));
    }

    public void setTouziedu(double touziedu) {
        this.touziedu = touziedu;
    }

    public double getZhongjiangjine() {
        return Double.valueOf(Utils.get2Value(zhongjiangjine));
    }

    public void setZhongjiangjine(double zhongjiangjine) {
        this.zhongjiangjine = zhongjiangjine;
    }

    public List<String> getTouzifangan() {
        return touzifangan;
    }

    public void setTouzifangan(List<String> touzifangan) {
        this.touzifangan = touzifangan;
    }

    @Override
    public String toString() {
        return "Shouyi{" +
                "touziedu=" + touziedu +
                ", zhongjiangjine=" + zhongjiangjine +
                ", touzifangan=" + touzifangan +
                ", touzifanganXiaQi=" + touzifanganXiaQi +
                '}';
    }
}
