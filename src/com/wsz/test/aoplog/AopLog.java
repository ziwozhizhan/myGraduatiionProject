package com.wsz.test.aoplog;

import org.springframework.stereotype.Component;

/**
 * @author wanshenzhen  2017/5/22.
 */
@Component("aopLog")
public class AopLog {

    public void myLog(){
        throw new RuntimeException("测试aop异常日志");
    }
}
