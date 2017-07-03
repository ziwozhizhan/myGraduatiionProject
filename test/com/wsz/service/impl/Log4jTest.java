package com.wsz.service.impl;

import com.wsz.test.aoplog.AopLog;
import com.wsz.test.base.BaseTest;
import com.wsz.test.slf4j.SlfLog;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wanshenzhen  2017/5/21.
 */
public class Log4jTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(Log4jTest.class);
    @Autowired
    private AopLog aopLog;

    @Test
    public void testLog(){
        System.out.println("============== log4j 测试start ========================");
        logger.debug("debug级测试");
        logger.info("info级测试");
        logger.error("error级测试");
        System.out.println("============== log4j 测试end ========================");
    }

    @Test
    public void testSlf4j(){
        System.out.println("============ slf4j 测试 start =================");
        new SlfLog().myLog();
        System.out.println("============ slf4j 测试 end =================");
    }

    @Test
    public void testAopLog(){
        System.out.println("============ AopLog 测试 start =================");
        aopLog.myLog();
        System.out.println("============ AopLog 测试 start =================");
    }

    @Test
    public void test(){
        System.out.println(System.getProperty("java.io.tmpdir"));
    }
}
