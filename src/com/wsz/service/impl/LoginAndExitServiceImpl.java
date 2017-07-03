package com.wsz.service.impl;

import com.wsz.common.consts.UserConsts;
import com.wsz.service.ILoginAndExitService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanshenzhen  2017/4/6.
 */
@Service("loginAndExitService")
public class LoginAndExitServiceImpl implements ILoginAndExitService {
    @Override
    public Map<String,String> login(String uname, String password, String code) {
        //操作结果信息
        Map<String,String> resultMap = new HashMap<String, String>();
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();

        //验证：非空
        if (StringUtils.isEmpty(uname) || StringUtils.isEmpty(password) ||StringUtils.isEmpty(code)){
            resultMap.put("error","用户名、密码或验证码为空！");
            return  resultMap;
        }

        //验证：验证码
        Session session = currentUser.getSession();
        if (!code.equalsIgnoreCase(session.getAttribute(UserConsts.VALIDATE_CODE).toString())){
            resultMap.put("error","验证码不正确！");
            return  resultMap;
        }

        //登录验证
        UsernamePasswordToken token = new UsernamePasswordToken(uname, password.toCharArray());
        token.setRememberMe(false);

        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            System.out.println("对用户[" + uname + "]进行登录验证..验证开始");
            currentUser.login(token);
            System.out.println("对用户[" + uname + "]进行登录验证..验证通过");
            resultMap.put("success", "登录验证通过");
        }catch(UnknownAccountException uae){
            System.out.println("对用户[" + uname + "]进行登录验证..验证未通过,未知账户");
            resultMap.put("error", "未知账户");
        }catch(IncorrectCredentialsException ice){
            System.out.println("对用户[" + uname + "]进行登录验证..验证未通过,错误的凭证");
            resultMap.put("error", "用户名或密码不正确");
        }catch(LockedAccountException lae){
            System.out.println("对用户[" + uname + "]进行登录验证..验证未通过,账户已锁定");
            resultMap.put("error", "账户已锁定");
        }catch(ExcessiveAttemptsException eae){
            System.out.println("对用户[" + uname + "]进行登录验证..验证未通过,错误次数过多");
            resultMap.put("error", "用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            System.out.println("对用户[" + uname + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            resultMap.put("error", "用户名或密码不正确");
        }

        //验证是否登录成功
        if(currentUser.isAuthenticated()){
            System.out.println("用户[" + uname + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
        }else{
            token.clear();
        }

        return resultMap;
    }

    @Override
    public void exit() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();  //session删除、RememberMe cookie 也将被删除

    }
}
