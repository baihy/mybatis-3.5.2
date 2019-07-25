package com.baihy.mapper;

import com.baihy.domain.User;

/**
 * @projectName: mybatis
 * @packageName: com.baihy.mapper
 * @description:
 * @author: huayang.bai
 * @date: 2019/07/25 9:53
 */
public interface UserMapper {

    User selectOne(Integer id);

}
