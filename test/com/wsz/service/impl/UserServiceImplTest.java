package com.wsz.service.impl;

import com.wsz.common.util.ObjectUtil;
import com.wsz.pojo.po.UserPO;
import com.wsz.pojo.vo.UserVO;
import com.wsz.service.IUserService;
import com.wsz.test.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 测试用户管理业务接口实现类方法
 * @author wanshenzhen  2017/3/17.
 */
public class UserServiceImplTest extends BaseTest{
    private String msg = "测试用户管理service方法：";

    @Autowired
    private IUserService userService;

    @Test
    public void getUserBaseMsgById(){
        long id = 1L;
        UserVO userVO = userService.getUserBaseMsgById(id);
        System.out.println(userVO==null?"无数据":userVO.toString());
        System.out.println(msg + "getUserBaseMsgById 获取指定ID的用户基本信息（用户表）成功=============");
    }

    @Test
    public void listAllUser(){
        List<UserVO> userVOs = userService.listAllUser();
        if (ObjectUtil.isNullOrEmpty(userVOs)){
            System.out.println("无数据");
        }else{
            for (UserVO userVO:userVOs){
                System.out.println(userVO.toString());
            }
        }
        System.out.println(msg + "listAllUser 获取所有用户基本信息列表成功==========");
    }

    @Test
    public void listUserByAnyParam(){
//        List<UserVO> userVOs = userService.listUserByAnyParam("","2","1");
//        if (ObjectUtil.isNullOrEmpty(userVOs)){
//            System.out.println("无数据");
//        }else{
//            for (UserVO userVO:userVOs){
//                System.out.println(userVO.toString());
//            }
//        }
//        System.out.println(msg + "listUserByAnyParam 条件查询用户成功==========");
    }

    @Test
    public void saveUser() throws Exception {
        UserPO userPO = new UserPO();
        userService.saveUser(userPO);
        System.out.println(msg + "saveUser 增加用户信息成功=============");
    }

    @Test
    public void removeUserById() throws Exception {
        long id = 1L;
        boolean result = userService.removeUserById(id);
        if (result){
            System.out.println(msg + "saveUser 删除用户信息成功=============");
            return;
        }
        System.out.println("删除失败");
    }

    @Test
    public void test(){
        String str = "2017-04-24T23:59:00";
        str = str.replace("T", " ");

        System.out.println(str);
    }
}
