package com.lenovo.db;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by Administrator on 2017/4/15.
 * 访问数据库
 */
public class DBAccess {
    public SqlSession getSqlSession() throws IOException {
        Reader reader = Resources.getResourceAsReader("com/lenovo/conf/Configuration.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = build.openSession();
        return sqlSession;
    }
}
