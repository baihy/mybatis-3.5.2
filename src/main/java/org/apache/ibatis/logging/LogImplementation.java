package org.apache.ibatis.logging;

/**
 * @projectName: mybatis
 * @packageName: org.apache.ibatis.logging
 * @description: todo baihuayang 提供一个无参，无返回值的函数式接口
 * @author: huayang.bai
 * @date: 2019/09/18 11:11
 */
@FunctionalInterface
public interface LogImplementation {

    void handle();

}
