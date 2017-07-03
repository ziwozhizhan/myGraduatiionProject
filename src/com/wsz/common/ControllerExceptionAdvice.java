package com.wsz.common;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * controller控制层 异常统一处理
 * @author wanshenzhen  2017/5/17.
 */
@ControllerAdvice
public class ControllerExceptionAdvice {
//    @ModelAttribute
//    public User newUser() {
//        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");
//        return new User();
//    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");
//    }

    @ExceptionHandler(UnauthorizedException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void processUnauthenticatedException(HttpServletRequest request, HttpServletResponse response, UnauthorizedException e) {
        System.out.println("===========应用到所有@RequestMapping注解的方法，在其抛出UnauthenticatedException异常时执行");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //判断是ajax请求
        if (request.getHeader("x-requested-with") != null&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))
        {
            response.setHeader("myErrorValue", "unauthenticated");
            response.setStatus(403);
        } else{
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.write("<script>alert('【权限限制】您没有此功能操作权限'); history.go(-1);</script>");
            } catch (IOException e1) {
                e1.printStackTrace();
            }finally {
                if (out != null){
                    out.close();
                }
            }

        }
//        return "theme/unauthenticated"; //返回一个逻辑视图名
    }
}
