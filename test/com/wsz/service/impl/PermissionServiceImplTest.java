package com.wsz.service.impl;

import com.wsz.pojo.po.PermissionPO;
import com.wsz.pojo.vo.PermissionVO;
import com.wsz.service.IPermissionService;
import com.wsz.test.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.SocketUtils;

/**
 * 单元测试 权限管理业务接口实现类方法
 * @author wanshenzhen  2017/3/17.
 */
public class PermissionServiceImplTest extends BaseTest {
    private String msg = "测试权限管理service方法：";

    @Autowired
    private IPermissionService permissionService;

    @Test
    public void getPermissionById(){
        long id = 1L;
        PermissionVO permissionVO = permissionService.getPermissionById(id);
        System.out.println(permissionVO==null?"无数据":permissionVO.toString());
        System.out.println(msg + "getPermissionById 获取指定ID的权限信息成功");
    }

    @Test
    public void listAllPermissonJson(){
//        String json = permissionService.listAllPermissonJson();
//        System.out.println(json);
    }

    @Test
    public void savePermission() throws Exception {
        //新增
        PermissionPO permissionPoSave = new PermissionPO();
        permissionPoSave.setNameEn("s");
        String result = permissionService.savePermission(permissionPoSave);
        System.out.println(result);
        System.out.println(msg + "savePermission 增加权限信息成功===============");
    }

    @Test
    public void updatePermission() throws Exception {
        PermissionPO permissionPo = new PermissionPO();
        permissionPo.setId(3L);
        permissionPo.setNameCn("xx");
        permissionPo.setPid(0L);
        Boolean result = permissionService.updatePermission(permissionPo);
        System.out.println("结果："+result);
        System.out.println(msg + "updatePermission 修改权限信息成功===============");
    }

    @Test
    @Rollback(false)
    public void removePermissonById() throws Exception {
        permissionService.removePermissonById(3L);
        System.out.println(msg + "removePermissonById 删除权限信息成功===============");
    }
}
