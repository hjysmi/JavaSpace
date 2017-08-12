package com.lenovo.main;

import com.lenovo.bean.Results;
import com.lenovo.dao.ClosingPriceDao;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/4/29.
 */
public class MaQuery {
    /**
     * 1.查询20天前的移动平均线的点
     * 1)输入期号查询该期号的移动平均线的点
     * 2.查询55天前的移动平均线的点
     * 3.通过高斯消元法求得移动平均线的交点
     * 1)判断是金叉还是死叉
     * 4.
     *
     * @param args
     */
    //private static Logger log = LoggerFactory.class(MaQuery.class);
    public static void main(String[] args) {
        MaQuery query = new MaQuery();
        query.query_jvli();

    }

    /*
    查询资金收益情况
     */
    private void query_shouyi() {
        Results perRes = new Results("", "");
        String begin = "20070403009";
        String end = "20070403012";
        Query query = new Query();
        try {
            query.start(perRes, begin, end, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    查询金叉死叉
     */
    private void query_jin_si() {
        ClosingPriceDao dao = new ClosingPriceDao();
        dao.queryIntersection();
    }

    /**
     * 查询相邻连中3期之间的距离
     */
    private void query_jvli() {
        ClosingPriceDao dao = new ClosingPriceDao();
        dao.query_jvli("金叉");
    }
}
