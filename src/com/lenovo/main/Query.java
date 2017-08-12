package com.lenovo.main;

import com.lenovo.bean.MulBean;
import com.lenovo.bean.Results;
import com.lenovo.bean.Shouyi;
import com.lenovo.dao.ResultsDao;
import com.lenovo.util.Utils;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by Administrator on 2017/4/15.
 */
public class Query {
    public static final double i = 1900 / 2;//投资回报率(买大卖小的回报率都是这个数)

    public static final int REPEAT = 3;//最大重复投资次数
    public static int count = REPEAT;//最大重复投资次数
    //public double zhongjiangMoney;//本次中奖金额
    double subMoney = 0;//本周期收益
    double totalMoney = 0;//全部收益（开始到结束）
    //连续中出的期号
    Map<Integer, String> repeatMap = new HashMap<>();
    int money = 2;//每注金额
    //投入倍数
    int MULT = 1;
    public static Results perRes = new Results("", "");
    //连中REPEAT次，获取的收益
    double MAX_PROFIT = Math.pow(1.9, REPEAT - 1) * i * money - 2000;
    private static int id;
    static boolean flag = true;
    private Shouyi shouyi;
    static List<String> zhongjiangfangan;//中奖方案
    StringBuffer sb = new StringBuffer();
    int end_id = 0;
    static Map<Integer, MulBean> map;

    static {
        map = new LinkedHashMap<>();
        map.put(1, new MulBean(1,null,1));
        map.put(2, new MulBean(4,5,2));
        map.put(3, new MulBean(20,6,3));
        map.put(4, new MulBean(40,6,4));
    }


    /*public static void main(String[] args)
    {

        String begin = "20151229038";
        String end = "20151229109";
        Query query = new Query();
        try {
            query.start(perRes,begin,end,0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }*/

    /**
     * @param res   上期开奖对象
     * @param begin 本期开奖号
     * @return 本期开奖对象
     * @throws SQLException
     */
    public void start(Results res, String begin, String end, double kunsun) throws SQLException {
        ResultsDao dao = new ResultsDao();
        id = dao.queryResultByqihao(begin).getId();
        end_id = dao.queryResultByqihao(end).getId();
        totalMoney = 0;
        if (MULT == 0) {
            MULT = 1;
        }
        double periodMoney = 0;
        while (end_id <= id) {
            if (!flag) {
                //连中两期，第三期挂掉
                flag = true;
                count = REPEAT;
                res = new Results("", "");
            } else {
                id = dao.queryResultByqihao(begin).getId();
                periodMoney = 0;
            }
            List<String> arrayToList = Utils.getArrayToList(Utils.getFromAssets());//决定的方案
            List<String> quedingfangan = Utils.quedingfangan(arrayToList);//第二个方案
            Shouyi s = new Shouyi();
            while (flag) {
                MULT = getMult(MULT, kunsun, periodMoney);
                if (MULT == 0) {
                    log("mult=0");
                    return;
                }
                log("mult=" + MULT + "\n");
                if (res.getQihao().equals("") && "".equals(res.getResult())) {
                    // 第一期
                    repeatMap.clear();
                    log("###########################\n");
                    subMoney = 0;
                    count = REPEAT;
                    Results resultByqihao = dao.queryResultById(id);
                    res = resultByqihao;
                    String result = resultByqihao.getResult();
                    id = resultByqihao.getId();
                    if (arrayToList.contains(result)) {
                        //第一种方案中奖
                        log("第1个方案中奖了....\n");
                        log("期号： "+resultByqihao.getQihao()+"\n");
                        repeatMap.put(count, resultByqihao.getQihao());
                        zhongjiangfangan = arrayToList;
                        s.setTouziedu(500 * money * MULT);
                        s.setZhongjiangjine(money * i * MULT);
                        s.setTouzifangan(arrayToList);
                    } else {
                        //第二种方案中奖
                        log("第2个方案中奖了....\n");
                        log("期号： "+resultByqihao.getQihao()+"\n");
                        zhongjiangfangan = quedingfangan;
                        s.setTouziedu(500 * money * MULT);
                        //s.setZhongjiangjine(money * i * MULT);
                        s.setZhongjiangjine(0);
                        s.setTouzifangan(quedingfangan);
                        count = REPEAT;
                        res = new Results("", "");
                        repeatMap.clear();
                    }
                } else {
                    count--;
                    //不是第一期
                    Results resultById = dao.queryResultById(id);
                    String result = resultById.getResult();
                    res = resultById;
                    if (!zhongjiangfangan.contains(result)) {
                        log("************************************\n");
                        res = new Results("", "");
                    }
                    if (arrayToList.contains(result)) {
                        //第一种方案中奖
                        zhongjiangfangan = arrayToList;
                        log("第1个方案中奖了....\n");
                        log("期号： "+resultById.getQihao()+"\n");
                        repeatMap.put(count, resultById.getQihao());
                        s.setTouziedu(shouyi.getZhongjiangjine());
                        //s.setTouziedu(500 * money + shouyi.getZhongjiangjine());
                        s.setZhongjiangjine(shouyi.getZhongjiangjine() * 1.9);
                        s.setTouzifangan(arrayToList);
                    } else {
                        //第二种方案中奖
                        zhongjiangfangan = quedingfangan;

                        log("第2个方案中奖了....\n");
                        log("期号： "+resultById.getQihao()+"\n");
                        repeatMap.clear();
                        if (count != REPEAT) {
                            s.setTouziedu(shouyi.getZhongjiangjine());
                            s.setZhongjiangjine(0);
                        } else {
                            s.setTouziedu(500 * money * MULT);
                            s.setZhongjiangjine(money * i * MULT);
                        }
                        s.setTouzifangan(quedingfangan);
                    }
                    if (count == 1) {
                        flag = false;
                    }
                }
                log("投资额度：  "+s.getTouziedu()+" 中奖金额：  "+Utils.get2Value(s.getZhongjiangjine()) + " 本次:" + (s.getZhongjiangjine() - s.getTouziedu()));
                log("\n计数器：  " + count + "\n");
                if (id <= end_id) {
                    flag = false;
                }
                shouyi = s;
                subMoney += s.getZhongjiangjine() - s.getTouziedu();
                totalMoney += s.getZhongjiangjine() - s.getTouziedu();
                periodMoney += s.getZhongjiangjine() - s.getTouziedu();
                if (count == 1 || !arrayToList.contains(dao.queryResultById(id).getResult())) {
                    //如果是第一方案的最后一期，或者第二方案的第一期 >>结束
                    log("本轮收益:" + subMoney + " 总收益:" + totalMoney + " 期间收益:" + periodMoney + "\n");
                    log("###########################\n");
                }
                id--;
            }
            //如果第一种方案连续中出三次,则重新计算periodMoney
            if (repeatMap.size() == 3) {
                log("装进口袋: " + periodMoney + "\n");
                periodMoney = 0;
            }
        }
    }
    private int getMult(int nativeMult, double kunsun, double periodMoney) {
        if (nativeMult == 0) {
            log("投入倍数==0,请重新查询");
            return 0;
        }
        //上次连中三期到目前为止的收益情况
        double j = -(kunsun + periodMoney);
        Set<Map.Entry<Integer, MulBean>> entries = map.entrySet();
        Integer key_remove = null;
        for (Map.Entry i : entries) {
            //log("目前为止的收益j:"+j+",nativeMult * MAX_PROFIT:"+nativeMult * MAX_PROFIT);
            if (j >= nativeMult * MAX_PROFIT) {
                /*int key = (int) i.getKey() + 1;
                log("\n小伙子,开始加倍了key = " + key + "\n");
                if (key >= map.size()) return 0;
                nativeMult = map.get(key).getBeishu();
                key_remove = key-1;
                break;*/
                int key = (int) i.getKey();
                if ( map.size()==0) return 0;
                if(map.size()>1)
                nativeMult= map.get(key+1).getBeishu();

                key_remove = key++;
                log("\n小伙子,开始加倍了key = " + key + "\n");
                break;
            }
        }
        if(null!=key_remove) map.remove(key_remove);
        return nativeMult;
    }
    private void log(String log)
    {
        System.out.println(log);
    }
}
