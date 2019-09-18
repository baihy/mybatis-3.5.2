package com.baihy.test;


import com.baihy.domain.User;
import com.baihy.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @projectName: mybatis
 * @packageName: com.baihy.mapper.baihy.test
 * @description:
 * @author: huayang.bai
 * @date: 2019/07/25 9:50
 */
@Slf4j
public class MybatisTest {

    public static void main(String[] args){
        try {
            new MybatisTest().test2();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void test1() throws IOException {
        // 通过类加载器，获取到一个文件流
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        // SqlSession对象实际上就是DefaultSqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("com.baihy.mapper.baihy.com.baihy.mapper.UserMapper.selectOne", 30);
        log.info("user:{}", user);
    }


    @Test
    public void test2() throws IOException {
        // 通过类加载器，获取到一个文件流
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // userMapper是一个代理对象
        String userMapperClassName = userMapper.getClass().getName();
        System.out.println(userMapperClassName);
        User user = userMapper.selectOne(30); //  只有执行这一行代码的时候，才会执行动态代理对象的invoke方法
        User user1 = userMapper.selectOne(30);
        log.info("user:{}", user);
        log.info("user1:{}", user1);
    }
    
    @Test
    public void testLog(){
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.selectOne(1);
            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log log = LogFactory.getLog(MybatisTest.class);
        System.out.println(log.isDebugEnabled());
    }


}
