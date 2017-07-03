package com.wsz.controller;

import com.wsz.common.util.CurrentUserUtil;
import com.wsz.pojo.po.UserPO;
import com.wsz.pojo.vo.UserVO;
import com.wsz.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 个人中心 控制层
 * @author wanshenzhen  2017/4/24.
 */
@Controller
@RequestMapping("/admin/personal")
public class PersonalCenterController {
    @Autowired
    private IUserService userService;

    /**
     * 修改 当前用户个人信息
     */
    @RequiresPermissions("grzx_xggrxx")
    @PostMapping("/updateInfo.do")
    @ResponseBody
    public String updateInfo(UserPO userPO, BindingResult bindingResult){
        String result = "error";

        if (bindingResult.hasErrors()){
            return "param_error";//参数绑定错误
        }

        //验证是当前用户
        long userId = CurrentUserUtil.getUserId();
        if (userId == userPO.getId()){
            try {
                boolean updateInfo = userService.updateUser(userPO);
                if (updateInfo) {
                    result = "ok";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            result = "notUser"; //非当前用户
        }
        return result;
    }

    /**
     * 修改 当前用户密码
     */
    @RequiresPermissions("grzx_xgmm")
    @PostMapping("/updatePwd.do")
    @ResponseBody
    public String updatePwd(UserPO userPO, String oldPassWord){
        String result = "error";
        //验证是当前用户
        long userId = CurrentUserUtil.getUserId();

        if (userId == userPO.getId()){
            try {
                //验证 原密码正确
                String oldPwd = userService.getUserPassword(userId);
                String oldPwd2 = DigestUtils.md5DigestAsHex(oldPassWord.getBytes());
                if (!oldPwd2.equals(oldPwd)){
                    return "oldNot"; //原密码不正确
                }
                String newPwd = userPO.getPassword(); //新密码
                userPO.setPassword(DigestUtils.md5DigestAsHex(newPwd.getBytes()));
                boolean updateInfo = userService.updateUser(userPO);
                if (updateInfo) {
                    result = "ok";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            result = "notUser"; //非当前用户
        }
        return result;
    }
}
