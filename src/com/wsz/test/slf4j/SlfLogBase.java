package com.wsz.test.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wanshenzhen  2017/5/21.
 */
public class SlfLogBase {
    /**
     * 子类打印的都是父类的名称，所以这样使用继承简写日志不适用
     */
    protected static Logger logger = LoggerFactory.getLogger(SlfLogBase.class);
}
