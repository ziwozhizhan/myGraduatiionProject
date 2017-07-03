package com.wsz.service;

import com.wsz.pojo.po.RolePO;
import com.wsz.pojo.vo.RoleVO;

import java.util.List;

/**
 * 角色管理 业务接口
 * @author wanshenzhen  2017/3/17.
 */
public interface IRoleService {
    /**
     * 获取指定ID的角色信息
     * @param id 角色ID
     * @return
     */
    RoleVO getRoleById(long id);

    /**
     * 获取所有角色列表,按ID正序排列
     * @return 空时返回null
     */
    List<RoleVO> listAllRole();

    /**
     * 获取所有角色列表,按ID正序排列,并标注哪些是userId的角色
     * @param userId
     * @return
     */
    List<RoleVO> listUserRoles(long userId);

    /**
     * 添加或修改一个新的角色
     * @param rolePO 角色对象
     */
    void saveOrUpdateRole(RolePO rolePO) throws Exception;

    /**
     * 为角色赋予权限，添加前删除原有角色的权限
     * @param id 角色ID
     * @param permIds 权限数组IDs
     */
    void savePermission4Role(long id,String[] permIds) throws Exception;

    /**
     * 移出指定ID的角色及其赋予的权限
     * @param id 角色ID
     * @return true 删除成功
     */
    Boolean removeRoleById(long id) throws Exception;
}
