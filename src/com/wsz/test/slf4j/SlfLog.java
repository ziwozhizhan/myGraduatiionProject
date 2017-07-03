package com.wsz.test.slf4j;

/**
 * @author wanshenzhen  2017/5/21.
 */
public class SlfLog extends SlfLogBase {

    public void myLog(){
        logger.debug("SlfLog测试debug");
        logger.info("SlfLog测试info");
        logger.error("SlfLog测试error");
    }
}
