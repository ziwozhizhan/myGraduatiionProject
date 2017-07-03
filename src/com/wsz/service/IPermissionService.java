package com.wsz.service;

import com.wsz.pojo.po.PermissionPO;
import com.wsz.pojo.vo.PermissionVO;

import java.util.List;

/**
 * 权限管理 业务接口
 * @author wanshenzhen  2017/3/17.
 */
public interface IPermissionService {

    /**
     * 获取指定ID的权限信息
     * @param id 权限ID
     * @return
     */
    PermissionVO getPermissionById(long id);

    /**
     * 获取用户所有权限编码
     * @param userId 用户id
     * @return String 权限编码
     */
    List<String> listPermissionByUserId(long userId);

    /**
     * 获取某个角色的权限树形，返回json格式字符串
     * @param roleId 角色ID
     * @return
     */
    String listAllPermissonJson4Role(long roleId);

    /**
     * 获取所有权限树形结构列表，返回json格式字符串
     * @return
     */
    String listAllPermissonJson();

    /**
     * 增加权限信息
     * @param permissionPO 权限对象
     */
    String savePermission(PermissionPO permissionPO) throws Exception;

    /**
     * 修改权限信息
     * @param permissionPO 权限对象
     * @return true 成功，false 失败
     */
    Boolean updatePermission(PermissionPO permissionPO) throws Exception;

    /**
     * 移除指定ID的权限信息
     * @param id 权限ID
     */
    void removePermissonById(long id) throws Exception;
}
