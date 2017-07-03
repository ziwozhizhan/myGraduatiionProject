package com.wsz.service.impl;

import com.wsz.common.util.ObjectUtil;
import com.wsz.dao.IRoleDao;
import com.wsz.pojo.po.RolePO;
import com.wsz.pojo.vo.RoleVO;
import com.wsz.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanshenzhen  2017/3/17.
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService {
    private final static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private IRoleDao roleDao;

    @Override
    public RoleVO getRoleById(long id) {
        String sql = "select id,role_name,role_describe from role where id=?";
        List<Object[]> objects = roleDao.findListBySql(sql,id);
        List<RoleVO> dataVOs = new ArrayList<>();
        for (Object[] obj:objects) {
            RoleVO dataVO = objectsIntoVo(obj);//注意obj的顺序
            dataVOs.add(dataVO);
        }
        if (dataVOs.size() > 0){
            return dataVOs.get(0);
        }
        return null;
    }

    @Override
    public List<RoleVO> listAllRole() {
        String sql = "select id,role_name,role_describe from role";
        List<Object[]> objects = roleDao.findListBySql(sql);
        List<RoleVO> dataVOs = new ArrayList<>();
        for (Object[] obj:objects) {
            RoleVO dataVO = objectsIntoVo(obj);//注意obj的顺序
            dataVOs.add(dataVO);
        }
        if (dataVOs.size() > 0){
            return dataVOs;
        }
        return null;
    }

    @Override
    public List<RoleVO> listUserRoles(long userId) {
        String sql = "select r.id,r.role_name,r.role_describe,ur.user_id from role r " +
                "left join user_role ur on ur.role_id=r.id and ur.user_id=?";
        List<Object[]> objects = roleDao.findListBySql(sql,userId);
        List<RoleVO> dataVOs = new ArrayList<>();
        for (Object[] obj:objects) {
            RoleVO dataVO = objectsIntoVo(obj);//注意obj的顺序
            dataVOs.add(dataVO);
        }
        if (dataVOs.size() > 0){
            return dataVOs;
        }
        return null;
    }

    @Override
    public void saveOrUpdateRole(RolePO rolePO) throws Exception {
        roleDao.saveOrUpdateObject(rolePO);
    }

    @Override
    public void savePermission4Role(long id, String[] permIds) throws Exception {
        //删除角色原有权限
        String deleteSql = "delete from role_permission where role_id=?";
        roleDao.executeBySql(deleteSql,id);

        if (null != permIds && permIds.length > 0){
            //重新赋予权限
            StringBuilder addSql = new StringBuilder("insert role_permission(role_id,permission_id) values");
            for (int i = 0; i < permIds.length; i++) {
                addSql.append("("+id+","+permIds[i]+"),");
            }
            addSql.deleteCharAt(addSql.lastIndexOf(","));
            addSql.append(";");
            roleDao.executeBySql(addSql.toString());
        }
    }

    @Override
    public Boolean removeRoleById(long id) throws Exception {
        String sql = "delete r.*,rp.* from role r left join role_permission rp on r.id=rp.role_id where r.id=?";
        return roleDao.executeBySql(sql, id);
    }

    /**
     * vo赋值
     * @param obj 值数组，必须按照方法指定的顺序,obj[0]不能为空表示ID
     * @return
     */
    private RoleVO objectsIntoVo(Object... obj){
        RoleVO dataVO = new RoleVO();
        dataVO.setId(Long.valueOf(String.valueOf(obj[0])));
        if(obj.length>1 && !ObjectUtil.isNullOrEmpty(obj[1])){
            dataVO.setRoleName(obj[1].toString());
        }
        if(obj.length>2 && !ObjectUtil.isNullOrEmpty(obj[2])){
            dataVO.setDescribe(obj[2].toString());
        }
        if(obj.length>3 && !ObjectUtil.isNullOrEmpty(obj[3])){
            dataVO.setUserRole(true);
        }
        return dataVO;
    }
}
