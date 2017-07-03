package com.wsz.service;

import java.util.Map;

/**
 * 登录和退出 业务接口
 * @author wanshenzhen  2017/4/6.
 */
public interface ILoginAndExitService {
    /**
     * 登录
     * @param uname 用户名
     * @param password 密码
     * @param code 验证码
     * @return Map：success-成功，error-错误信息
     */
    Map<String,String> login(String uname, String password, String code);

    /**
     * 退出
     */
    void exit();
}
