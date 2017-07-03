package com.wsz.service.impl;

import com.wsz.pojo.vo.RoleVO;
import com.wsz.service.IRoleService;
import com.wsz.test.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;

/**
 * 单元测试 角色管理业务接口实现类方法
 * @author wanshenzhen  2017/3/17.
 */
public class RoleServiceImplTest extends BaseTest {
    private String msg = "测试角色管理service方法";

    @Autowired
    private IRoleService roleService;

    @Test
    public void getRoleById(){
        long id = 1L;
        RoleVO roleVO = roleService.getRoleById(id);
        System.out.println(roleVO==null?"无数据":roleVO.toString());
        System.out.println(msg + "getRoleById 获取指定ID的角色信息成功=========");
    }

    @Test
    public void listAllRole(){
        List<RoleVO> roleVOs = roleService.listAllRole();
        if (roleVOs != null){
            for (RoleVO roleVO:roleVOs){
                System.out.println(roleVO.toString());
            }
        }else {
            System.out.println("无数据");
        }
        System.out.println(msg + "listAllRole 获取所有角色列表成功=============");
    }

    @Test
    public void listUserRoles(){
        long userId = 1L;
        List<RoleVO> roleVOs = roleService.listUserRoles(userId);
        if (roleVOs != null){
            for (RoleVO roleVO:roleVOs){
                System.out.println(roleVO.toString());
            }
        }else {
            System.out.println("无数据");
        }
        System.out.println(msg + "listUserRoles 获取所有角色列表成功=============");
    }

    @Test
    public void savePermission4Role() throws Exception {
        //测试一下是否回滚了--已经回滚，service事务ok
        long id = 3L;
        String[] permIds = new String[]{"1","2"};
        roleService.savePermission4Role(id,permIds);
        System.out.println(msg + "savePermission4Role 为角色赋予权限成功============");
    }

    @Test
    public void removeRoleById() throws Exception {
        long id = 1L;
        Boolean result = roleService.removeRoleById(id);
        System.out.println("删除结果：" + result);
        System.out.println(msg + "removeRoleById 移出指定ID的角色及其赋予的权限成功===========");
    }
}