package com.wsz.controller;

import com.wsz.common.consts.UserConsts;
import com.wsz.common.util.JsonUtil;
import com.wsz.common.util.ValidateCodeUtil;
import com.wsz.service.ILoginAndExitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 登录与生成验证码
 * @author wanshenzhen  2017/3/29.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private ILoginAndExitService loginAndExitService;
    /**
     * 生成验证码
     */
    @RequestMapping("validate.do")
    private void generateValidate(HttpServletRequest request,HttpServletResponse response){
        //生成验证码字符串
        String textCode = ValidateCodeUtil.generateTextCode(ValidateCodeUtil.TYPE_NUM_UPPER, 4, "0O");

        HttpSession session = request.getSession();
        session.setAttribute(UserConsts.VALIDATE_CODE, textCode);

        //生成验证码图片
        BufferedImage image = ValidateCodeUtil.generateImageCode(textCode, 150, 50, 3, true, null, null, null);

        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            System.out.println("生成验证码出错");
            e.printStackTrace();
        }
    }

    /**
     * 登录
     */
    @PostMapping(value = "/login.do", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String login(String uname, String password, String code){
        Map<String,String> resultMap = loginAndExitService.login(uname, password, code);
        return JsonUtil.ObjectToJson(resultMap);
    }

    /**
     * 跳转登录页面
     * @return
     */
    @GetMapping("/goLogin.do")
    public String goLogin(){
        return "theme/login";
    }

    /**
     * 跳转 session失效页面
     */
    @GetMapping("/goSessionTimeout.do")
    public String goSessionTimeout(){
        return "theme/sessiontimeout";
    }
}
