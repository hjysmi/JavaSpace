package com.lenovo.dao;

import com.lenovo.bean.ClosingPrice;
import com.lenovo.bean.ClosingPricePage;
import com.lenovo.bean.Results;
import com.lenovo.db.DBAccess;
import com.lenovo.util.Utils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/29.
 */
public class MaResultDao {
    /**
     * 1.查询20天前的移动平均线的点
     * 1)输入期号查询该期号的移动平均线的点
     * 2.查询55天前的移动平均线的点
     * 3.通过高斯消元法求得移动平均线的交点
     * 1)判断是金叉还是死叉
     */
    public Results queryMaByQihao(int ma, String qihao) {
        ResultsDao dao = new ResultsDao();
        Results result = dao.queryResultByqihao(qihao);
        boolean flag = isBeyondMax(ma, result.getId());
        if (flag) {
            System.out.println("对不起,越界了");
        } else {
            //投入的方案


        }
        return result;
    }

    /**
     * 计算移动平均线的值
     */
    public void CalMa(int ma)
    {
        ClosingPriceDao dao = new ClosingPriceDao();
        ClosingPricePage page = new ClosingPricePage(1,3);
        Double price = dao.queryAvgPriceById(page);
        System.out.println("price:"+price);



        /*DBAccess access = new DBAccess();
        SqlSession sqlSession = null;
        try {
            sqlSession = access.getSqlSession();

            sqlSession.commit();
            sqlSession.clearCache();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != sqlSession) {
                sqlSession.close();
            }
        }*/
    }
    public void update()
    {
        DBAccess access = new DBAccess();
        SqlSession sqlSession = null;
        try {
            sqlSession = access.getSqlSession();
            /*for(int i=0;i<list.size();i++)
            {
                sqlSession.update("batchUpdate",list.get(i));
            }*/
            sqlSession.commit();
            sqlSession.clearCache();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != sqlSession) {
                sqlSession.close();
            }
        }
    }
    public void insert() {
        long time = System.currentTimeMillis();
        int i = 0;
        ResultsDao dao = new ResultsDao();
        int max = dao.queryCount();
        int loop=10000;
        while (max >= 1) {
            List<ClosingPrice> list = new ArrayList<>();
            for (int j = 0; j < loop; j++) {
                Results result = dao.queryResultById(max);
                List<String> arrayToList = Utils.getArrayToList(Utils.getFromAssets());
                if (arrayToList.contains(result.getResult())) {//+1
                    i++;
                } else {//-1
                    i--;
                }
                ClosingPrice price = new ClosingPrice(i, result.getId(), result.getQihao(),
                        result.getResult());
                list.add(price);
                max--;
                System.out.println("j==" + j);
            }
            DBAccess access = new DBAccess();
            SqlSession sqlSession = null;
            try {
                sqlSession = access.getSqlSession();
                sqlSession.insert("insertDataBatch", list);
                sqlSession.commit();
                sqlSession.clearCache();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != sqlSession) {
                    sqlSession.close();
                }
            }
            System.out.println("max=" + max);
            if(max<loop) break;
        }
        System.out.println("时间差:" + (System.currentTimeMillis() - time));

    }

    /**
     * 根据Id计算该点的收盘价
     *
     * @param id
     */
    public void queryMaByid(int ma, int id) {

    }

    /**
     * 判断是否越界
     *
     * @param ma
     * @param id
     * @return
     */
    public boolean isBeyondMax(int ma, int id) {
        ResultsDao dao = new ResultsDao();
        int max = dao.queryCount();
        if (id + ma > max) {
            return true;
        }
        return false;

    }
}
