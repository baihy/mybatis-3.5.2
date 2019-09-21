package com.baihy.mapper;

import com.baihy.domain.User;
import org.apache.ibatis.annotations.Select;

/**
 * @projectName: mybatis
 * @packageName: com.baihy.mapper.baihy.com.baihy.mapper
 * @description:
 * @author: huayang.bai
 * @date: 2019/07/25 9:53
 */
public interface UserMapper {

    // 注意：通过底层代码我们知道，如果@Select注解中有多个sql语句，那么这些sql语句是通过StringBuilder来拼接的，是通过空格来进行连接的。
    // 源码体现是：org.apache.ibatis.builder.annotation.MapperAnnotationBuilder.buildSqlSourceFromStrings
    @Select({"", ""})
    User selectOne(Integer id);

}
