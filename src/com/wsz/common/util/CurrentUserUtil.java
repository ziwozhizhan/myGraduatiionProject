package com.wsz.common.util;

import com.wsz.common.consts.UserConsts;
import com.wsz.pojo.po.UserPO;
import com.wsz.pojo.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * 当前用户信息
 * @author wanshenzhen  2017/4/19.
 */
public class CurrentUserUtil {
    private CurrentUserUtil(){}

    /**
     * 获取当前用户id
     */
    public static long getUserId(){
        return getUserPo().getId();
    }

    /**
     * 获取当前用户 用户名
     */
    public static String getUserUname(){
        return getUserPo().getUname();
    }

    /**
     * 获取当前用户 姓名
     */
    public static String getUserName(){
        return getUserPo().getName();
    }

    /**
     * 获取当前用户信息
     */
    public static UserPO getUserPo(){
        return (UserPO) getSession().getAttribute(UserConsts.USER_ADMIN);
    }

    /**
     * 获得shiro session
     */
    private static Session getSession(){
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.getSession();
    }
}
