package com.wsz.common;

import com.wsz.common.util.CurrentUserUtil;
import com.wsz.dao.IUserDao;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面头部获取用户基本信息：姓名、角色
 * @author wanshenzhen  2017/4/25.
 */
public class UserInfoTag implements TemplateMethodModelEx{
    @Autowired
    private IUserDao userDao;

    @Override
    public Object exec(List list) throws TemplateModelException {
        Map<String,Object> result = new HashMap<>();

        //获取当前用户
        long userId = CurrentUserUtil.getUserId();
        //获取用户姓名
        String sqlName = "select name from user where id=?";
        List<Object> names = userDao.findListBySql(sqlName, userId);
        if (names!=null && names.size()>0){
            result.put("name",names.get(0));
        }

        //获取用户角色
        String sqlRoles = "select r.role_name from user_role ur left join role r on ur.role_id=r.id where ur.user_id=?";
        List<Object> roles = userDao.findListBySql(sqlRoles, userId);
        String userRoles = "";
        for (Object obj:roles){
            userRoles = userRoles + obj + "|";
        }
        result.put("userRoles", userRoles);
        return result;
    }
}
