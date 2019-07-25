package com.baihy.test;


import com.baihy.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @projectName: mybatis
 * @packageName: com.baihy.test
 * @description:
 * @author: huayang.bai
 * @date: 2019/07/25 9:50
 */
@Slf4j
public class MybatisTest {

    public static void main(String[] args) throws IOException {
        // 通过类加载器，获取到一个文件流
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("com.baihy.mapper.UserMapper.selectOne", 30);
        log.info("user:{}", user);
    }


}
