package com.lenovo.dao;

import com.lenovo.bean.AvgBean;
import com.lenovo.bean.ClosingPrice;
import com.lenovo.bean.ClosingPricePage;
import com.lenovo.db.DBAccess;
import com.lenovo.util.ExeclWrite;
import com.lenovo.util.Utils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/30.
 */
public class ClosingPriceDao {
    /**
     * 根据Id查询结果
     *
     * @param id
     */
    public ClosingPrice queryResultById(int id) {
        DBAccess access = new DBAccess();
        SqlSession sqlSession = null;
        ClosingPrice price = null;
        try {
            sqlSession = access.getSqlSession();
            price = sqlSession.selectOne("queryPriceById", id);
            sqlSession.clearCache();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != sqlSession) {
                sqlSession.close();
            }
        }
        return price;
    }

    /**
     * 查询在区间内连中三期的间距
     * 1.读取execl数据
     * 2.求间距
     * 2.1
     */
    public void query_jvli(String isIntersection) {
        isIntersection = "金叉";
        ExeclWrite write = new ExeclWrite();
        List<Map<String, Object>> list = write.read();
        List<String> queDingfangan = Utils.getArrayToList(Utils.getFromAssets());//决定的方案
        int i = 0, lianzhong_count = 0;
        int fail_count=0;
        List<Map<String,String>> list_jvli = new ArrayList<>();
        //Map<String, Object> map_6 = list.get(6372);
        while (true) {
            if (i >= write.read_lastId()) break;
            Map<String, Object> map;
            Map<String, Object> map_next;
            try {
                map = list.get(i);
                map_next = list.get(i + 1);
            } catch (Exception e) {
                System.out.println("list没值了,i : "+i);
                break;
            }
            if (!isIntersection.equals(map.get("isIntersection"))) continue;
            ClosingPrice price = (ClosingPrice) map.get("price");
            ClosingPrice price_next = (ClosingPrice) map_next.get("price");
            //
            for (int j = price.getId(); j < price_next.getId(); j++) {
                ClosingPrice price_j = queryResultById(j);
                if (queDingfangan.contains(price_j.getResult())) {
                    //中了
                    lianzhong_count++;
                    //连中三期
                    if (lianzhong_count == 3) {
                        System.out.println("连中3次了,id:" + price_j.getId() + ",qihao:" + price_j.getQihao());
                        Map map_fail = new HashMap();
                        map_fail.put("fail_count","连挂"+String.valueOf(fail_count)+"期");
                        map_fail.put("IsZhong","连中3次了 ,qihao:" + price_j.getQihao());
                        list_jvli.add(map_fail);
                        lianzhong_count = 0;
                        fail_count = 0;

                    }
                } else {
                    //没中
                    fail_count++;
                    System.out.println("挂了, qihao:" + price_j.getQihao());
                    Map map_fail = new HashMap();
                    map_fail.put("fail_count",String.valueOf(fail_count));
                    map_fail.put("IsZhong","挂了, qihao:" + price_j.getQihao());
                    list_jvli.add(map_fail);
                    //map_fail.clear();
                    lianzhong_count = 0;


                }
            }
            i = i + 2;
        }
        //写到execl中
         ExeclWrite execlWrite = new ExeclWrite();
         execlWrite.write_jvli(list_jvli);
    }

    /**
     * 求ma20和ma60的交叉点
     * 1.ma20上穿ma60、金叉
     * 2.ma20下穿ma60、死叉
     */
    public void queryIntersection() {
        DBAccess access = new DBAccess();
        SqlSession sqlSession = null;
        List<Map<String, Object>> list_result = new ArrayList<>();
        int id = 1;
        int loop = 500;
        int VALUE = 500;
        while (true) {
            try {
                sqlSession = access.getSqlSession();
                ClosingPricePage page = new ClosingPricePage(1, 330000);
                List<ClosingPrice> list_select_limit = sqlSession.selectList("selectMaIntersection", page);
                List<ClosingPrice> list_select = new ArrayList<>();
                //比较ma20和ma60是否有交点,1>ma20,ma60都不为null.
                for (int j = 0; j < list_select_limit.size(); j++) {
                    ClosingPrice price = list_select_limit.get(j);
                    if (!Utils.isEmpty(price.getMa20()) && !Utils.isEmpty(price.getMa60())) {
                        list_select.add(price);
                    }
                }
                int flag_intersection_jin = 0, flag_intersection_si = 0;
                for (int j = 0; j < list_select.size(); j++) {
                    Map<String, Object> map = new HashMap<>();
                    ClosingPrice price = list_select.get(j);
                    if (price.getId() < 62) continue;
                    ClosingPrice price_pre = list_select.get(j - 1);
                    if (price_pre.getMa20().equals(price_pre.getMa60())) {
                        if (price.getMa20().equals(price.getMa60())) {
                            //ma20与ma60重合
                            // flag_chonghe = true;
                           /* map.put("isIntersection", "重合");
                            map.put("price", price);
                            list_result.add(map);*/
                        } else if (price.getMa20() > (price.getMa60())) {
                            flag_intersection_jin++;
                            if (flag_intersection_jin == 2) {
                                map.put("isIntersection", "金叉");
                                map.put("price", price);
                                list_result.add(map);
                                flag_intersection_jin = 0;
                            }
                        } else if (price.getMa20() < price.getMa60()) {
                            flag_intersection_si++;
                            if (flag_intersection_si == 2) {
                                map.put("isIntersection", "死叉");
                                map.put("price", price);
                                list_result.add(map);
                                flag_intersection_si = 0;
                            }
                        }
                    } else if (price_pre.getMa20() > price_pre.getMa60()) {
                        if (price.getMa20() < price.getMa60()) {
                            //相交(死叉)
                            map.put("isIntersection", "死叉");
                            map.put("price", price);
                            list_result.add(map);
                            flag_intersection_si = 0;
                        } else if (price.getMa20().equals(price.getMa60())) {
                            flag_intersection_si++;
                            if (flag_intersection_si == 2) {
                                map.put("isIntersection", "死叉");
                                map.put("price", price);
                                list_result.add(map);
                                flag_intersection_si = 0;
                            }
                        } else {
                            //现在没有交叉(上升阶段)
                            flag_intersection_jin = 0;
                            flag_intersection_si = 0;

                        }
                    } else if (price_pre.getMa20() < price_pre.getMa60()) {
                        if (price.getMa20() > price.getMa60()) {
                            map.put("isIntersection", "金叉");
                            map.put("price", price);
                            list_result.add(map);
                            flag_intersection_jin = 0;
                        } else if (price.getMa20().equals(price.getMa60())) {
                            flag_intersection_jin++;
                            if (flag_intersection_jin == 2) {
                                map.put("isIntersection", "金叉");
                                map.put("price", price);
                                list_result.add(map);
                                flag_intersection_jin = 0;
                            }
                        } else {
                            //现在没有交叉(下降阶段)
                            flag_intersection_jin = 0;
                            flag_intersection_si = 0;
                        }
                    }

                }
                id += VALUE;
                if (id > 500) break;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != sqlSession) {
                    sqlSession.close();
                }
            }
        }
        ExeclWrite write = new ExeclWrite();
        write.create(list_result);
//        int i=1;
//        for (Map map : list_result) {
//            System.out.println("result: " + map);
//            write.create(map,i);
//            i++;
//        }
    }

    /**
     * @param
     * @return
     */
    public Double queryAvgPriceById(ClosingPricePage page) {
        DBAccess access = new DBAccess();
        SqlSession sqlSession = null;
        Double price = null;
        int ma = 60;
        int id = 1;
        int loop = 500;
        int Value = 500;
        int total = 330000;
        while (true) {
            try {
                sqlSession = access.getSqlSession();
                List<ClosingPricePage> list_avg = new ArrayList<>();
                int checkNum = 0;
                for (int i = id; i <= loop; i++) {
                    checkNum = i;
                    if (i >= (total - ma)) {
                        break;
                    }
                    list_avg.add(new ClosingPricePage(i, i + ma - 1));
                }
                List<AvgBean> list = null;
                if (list_avg != null && list_avg.size() > 0) {

                    list = sqlSession.selectList("selectAvg", list_avg);

                }
                sqlSession.clearCache();
                //id+1
                if (list != null && list.size() > 0) {
                    for (AvgBean ab : list) {
                        if (null != ab) {
                            ab.setId(ab.getId() + 1);
                            ab.setAvg(Double.valueOf(Utils.get2Value(ab.getAvg())));
                        }
                    }
                    for (AvgBean ab : list) {
                        sqlSession.update("batchUpdate", ab);
                    }
                }
                //将获取的list批量update到表里
                sqlSession.commit();
                if (checkNum >= (total - ma)) break;
                id += Value;
                loop += Value;
                System.out.println("id:" + id);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != sqlSession) {
                    sqlSession.close();
                }
            }
        }
        return price;
    }

    public void updateBatch(List<AvgBean> list, SqlSession sqlSession) {
       /* DBAccess access = new DBAccess();
       SqlSession sqlSession = null;
        try {

            sqlSession = access.getSqlSession();
            for(AvgBean ab:list)
            {
                sqlSession.update("batchUpdate",ab);
            }
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != sqlSession) {
                sqlSession.close();
            }
        }*/
    }
}
