package com.wsz.common;

import com.wsz.common.consts.UserConsts;
import com.wsz.pojo.po.UserPO;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * session失效判断拦截器
 * @author wanshenzhen  2017/5/16.
 */
public class SessionTimeoutInterceptor implements HandlerInterceptor{

    private List<String> excludedUrls;

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false 则退出本拦截器，本拦截器后面的postHandle与afterCompletion不再执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
//        String requestUri = request.getRequestURI();
//        for (String url : excludedUrls) {
//            if (requestUri.contains(url)) {
//                return true;
//            }
//        }
        System.out.println("========= session 拦截 ==============");
        HttpSession session = request.getSession();
        UserPO login = (UserPO) session.getAttribute(UserConsts.USER_ADMIN);
        if (login == null || "".equals(login.getName())) {
            //System.out.println(request.getContextPath());
            response.sendRedirect(request.getContextPath() + "/login/goSessionTimeout.do");
            return false;
        }
        return true;
    }

    //在业务处理器处理请求执行完成后,生成视图之前执行的动作
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     * 当拦截器抛出异常时,依然会从当前拦截器往回执行所的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }
}
