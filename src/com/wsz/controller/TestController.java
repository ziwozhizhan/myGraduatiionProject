package com.wsz.controller;

import com.wsz.test.aoplog.AopLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wanshenzhen  2017/5/22.
 */
@Controller
@RequestMapping()
public class TestController {

    @RequestMapping("/test.do")
    public void test(){
        System.out.println("sssss==========");
    }
}
