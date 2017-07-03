package com.wsz.common;

import com.wsz.common.consts.UserConsts;
import com.wsz.common.util.CurrentUserUtil;
import com.wsz.pojo.po.UserPO;
import com.wsz.pojo.vo.UserVO;
import com.wsz.service.IPermissionService;
import com.wsz.service.IUserService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统安全认证，shiro
 * @author wanshenzhen  2017/4/6.
 */
public class SysAuthenRealm extends AuthorizingRealm{
    @Autowired
    private IUserService userService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    CacheManager catchPool;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserPO userPO = CurrentUserUtil.getUserPo();
        if(null == userPO){
            return null;
        }
        // 缓存操作 key=userperm+用户id 确保唯一
        String cacheKey = "userperm_"+userPO.getId();
        Cache upermCache = catchPool.getCache("userpermCache");
        Element element = upermCache.get(cacheKey);

        if(element == null){ //缓存中无数据
            //从数据库中查询数据
            String currentUsername = (String)super.getAvailablePrincipal(principalCollection);
            SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
            UserPO user = userService.getUserPo(currentUsername);
            //放入角色信息（一般不用）
//            List<EsRole> listrole = user.getList_role();
//            Set<String> set = new HashSet<String>();
//            if(listrole!=null&&listrole.size()>0){
//                for(EsRole role:listrole){
//                    set.add(role.getRolename());
//                }
//            }
//            if(set!=null&&set.size()>0){
//                simpleAuthorInfo.setRoles(set);
//            }
            //放入权限信息
            Set<String> permisSet = new HashSet<String>();
            //获取用户权限名称
            List<String> pernames = permissionService.listPermissionByUserId(user.getId());
            if(pernames!=null&&pernames.size()>0){
                for(String pers:pernames){
                    permisSet.add(pers);
                }
            }
            if(permisSet!=null&&permisSet.size()>0){
                simpleAuthorInfo.setStringPermissions(permisSet);
            }

            //放入缓存
            element = new Element(cacheKey, simpleAuthorInfo);
            upermCache.put(element);
        }
        return (AuthorizationInfo) element.getObjectValue();
//        return simpleAuthorInfo;
    }

    //身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());

        UserPO userPO = userService.getUserPo(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        if (userPO == null){
            throw new IncorrectCredentialsException();
        }

        //将用户信息放入session
//        UserVO userVO = userService.getUserBaseMsgById(userPO.getId());
        Subject currenUser = SecurityUtils.getSubject();
        Session session = currenUser.getSession();
        session.setAttribute(UserConsts.USER_ADMIN,userPO);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                username, password.toCharArray(), getName());
        return info;
    }
}
