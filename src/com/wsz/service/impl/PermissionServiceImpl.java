package com.wsz.service.impl;

import com.wsz.common.util.JsonUtil;
import com.wsz.common.util.ObjectUtil;
import com.wsz.dao.IPermissionDao;
import com.wsz.pojo.po.PermissionPO;
import com.wsz.pojo.vo.PermissionVO;
import com.wsz.service.IPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanshenzhen  2017/3/17.
 */
@Service("permissionService")
public class PermissionServiceImpl implements IPermissionService{
    private final static Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    private IPermissionDao permissionDao;

    @Override
    public PermissionVO getPermissionById(long id) {
        String sql = "select id,name_cn,name_en,pid from permission where id=?";
        List<Object[]> objects = permissionDao.findListBySql(sql,id);
        List<PermissionVO> dataVOs = new ArrayList<>();
        for (Object[] obj:objects){
            PermissionVO dataVO = objectsIntoVo(obj);//注意obj的顺序
            dataVOs.add(dataVO);
        }
        if (dataVOs.size() > 0){
            return dataVOs.get(0);
        }
        return null;
    }

    @Override
    public List<String> listPermissionByUserId(long userId) {
        String sql = "select p.name_en from permission p left join role_permission rp on rp.permission_id=p.id" +
                " left join user_role ur on ur.role_id=rp.role_id where ur.user_id=?";
        List<String> objects = permissionDao.findListBySql(sql,userId);
        return objects;
    }

    @Override
    public String listAllPermissonJson4Role(long roleId) {
        //查找出所有权限数据 list VO
        String sql = "select id,name_cn,name_en,pid from permission";
        List<Object[]> objects = permissionDao.findListBySql(sql);

        //查找出当前觉得的权限数据 list<Object>
        String sqlPerm = "select permission_id from role_permission where role_id=?";
        List<Object> rolePerms = permissionDao.findListBySql(sqlPerm, roleId);

        List<PermissionVO> dataVOs = new ArrayList<>();
        for (Object[] obj:objects){
            PermissionVO dataVO = objectsIntoVo(obj);//注意obj的顺序
            dataVO.setChecked(false);
            perms:for (Object p:rolePerms){
                if (dataVO.getId() == Long.parseLong(p.toString())){
                    dataVO.setChecked(true);
                    break perms;
                }
            }
            dataVOs.add(dataVO);
        }

        //对数据遍历，json对象组合
        List<PermissionVO> rootVo = new ArrayList<>();
        long root = 0L;//根ID
        for (PermissionVO vo:dataVOs){
            if (vo.getPid()==root){
                rootVo.add(vo);
                //开始递归
                Children(dataVOs, vo);
            }
        }

        //将json 转换成字符串 JsonUtil
        String json = JsonUtil.ObjectToJson(rootVo);
        return json;
    }

    @Override
    public String listAllPermissonJson() {
        //查找出所有权限数据 list VO
        String sql = "select id,name_cn,name_en,pid from permission";
        List<Object[]> objects = permissionDao.findListBySql(sql);
        List<PermissionVO> dataVOs = new ArrayList<>();
        for (Object[] obj:objects){
            PermissionVO dataVO = objectsIntoVo(obj);//注意obj的顺序
            dataVOs.add(dataVO);
        }

        //对数据遍历，json对象组合
        List<PermissionVO> rootVo = new ArrayList<>();
        long root = 0L;//根ID
        for (PermissionVO vo:dataVOs){
            if (vo.getPid()==root){
                rootVo.add(vo);
                //开始递归
                Children(dataVOs, vo);
            }
        }

        //将json 转换成字符串 JsonUtil
        String json = JsonUtil.ObjectToJson(rootVo);
        return json;
    }

    /**
     * 为当前权限对象添加子类
     * @param dataVOs 所有权限数据列表
     * @param currentVo 当前权限对象
     */
    private void Children(List<PermissionVO> dataVOs, PermissionVO currentVo){
        for (PermissionVO vo : dataVOs){
            if (vo.getPid() == currentVo.getId()){//当前对象的子对象
                currentVo.getChildren().add(vo);
                Children(dataVOs, vo);
            }
        }
    }

    @Override
    public String savePermission(PermissionPO permissionPO) throws Exception {
        //验证nameEn唯一
        String sql = "select id from permission where name_en=?";
        List<Object> objects = permissionDao.findListBySql(sql,permissionPO.getNameEn());
        if(ObjectUtil.isNullOrEmpty(objects)){
            //唯一
            permissionDao.saveOrUpdateObject(permissionPO);
            return "ok";
        }
        return "not";//验证不通过
    }

    @Override
    public Boolean updatePermission(PermissionPO permissionPO) throws Exception {
        String sql = "update permission set name_cn=?,pid=? where id=?";
        return permissionDao.executeBySql(sql,permissionPO.getNameCn(),permissionPO.getPid(),permissionPO.getId());
    }

    @Override
    public void removePermissonById(long id) throws Exception {
        PermissionPO permissionPO = new PermissionPO();
        permissionPO.setId(id);
        permissionDao.deleteObject(permissionPO);
    }

    /**
     * vo赋值
     * @param obj 值数组，必须按照方法指定的顺序,obj[0]不能为空表示ID
     * @return
     */
    private PermissionVO objectsIntoVo(Object... obj){
        PermissionVO dataVO = new PermissionVO();
        dataVO.setId(Long.valueOf(String.valueOf(obj[0])));
        if(obj.length>1 && !ObjectUtil.isNullOrEmpty(obj[1])){
            dataVO.setNameCn(obj[1].toString());
        }
        if(obj.length>2 && !ObjectUtil.isNullOrEmpty(obj[2])){
            dataVO.setNameEn(obj[2].toString());
        }
        if(obj.length>3 && !ObjectUtil.isNullOrEmpty(obj[3])){
            dataVO.setPid(Long.parseLong(obj[3].toString()));
        }
        return dataVO;
    }
}
