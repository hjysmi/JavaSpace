package com.lenovo.dao;

import com.lenovo.bean.Results;
import com.lenovo.db.DBAccess;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

/**
 * Created by Administrator on 2017/4/15.
 */
public class ResultsDao {
    /**
     * 根据期号查询Result
     */
    public Results queryResultById(int id) {
        DBAccess access = new DBAccess();
        SqlSession sqlSession = null;
        Results  results = new Results();
        results.setId(id);
        Results  results_1 = null;
        try {

            sqlSession = access.getSqlSession();
            results_1 = sqlSession.selectOne("queryResultById",results);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != sqlSession) {
                sqlSession.close();
            }
        }
        return results_1;
    }

    /**
     * 查询数量
     */
    public int queryCount() {
        DBAccess access = new DBAccess();
        SqlSession sqlSession = null;
        int results = 0;
        try {

            sqlSession = access.getSqlSession();
            results = sqlSession.selectOne("queryCount");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != sqlSession) {
                sqlSession.close();
            }
        }
        return results;
    }

    /**
     * 通过期号查询结果
     *
     * @param qihao
     * @return
     */
    public Results queryResultByqihao(String qihao) {
        DBAccess access = new DBAccess();
        SqlSession sqlSession = null;
        Results results = null;
        try {
            Results result_1 = new Results();
            result_1.setQihao(qihao);
            sqlSession = access.getSqlSession();
            results = sqlSession.selectOne("queryResultByqihao", result_1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != sqlSession) {
                sqlSession.close();
            }
        }
        return results;
    }
}
