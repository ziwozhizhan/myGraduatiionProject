package com.wsz.service.impl;

import com.wsz.common.Page;
import com.wsz.common.consts.DataDictionaryConsts;
import com.wsz.common.util.ObjectUtil;
import com.wsz.dao.IUserDao;
import com.wsz.pojo.po.UserPO;
import com.wsz.pojo.vo.ArticleVO;
import com.wsz.pojo.vo.UserVO;
import com.wsz.service.IDataDictionaryService;
import com.wsz.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanshenzhen  2017/3/17.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IDataDictionaryService dataDictionaryService;


    @Override
    public UserVO getUserBaseMsgById(long id) {
        String sql = "select id,uname,name,sex,department,position,contact_way from user where id=?";
        List<Object[]> objects = userDao.findListBySql(sql,id);
        List<UserVO> dataVOs = new ArrayList<>();
        for (Object[] obj:objects){
            UserVO dataVO = objectsIntoVo(obj);//注意obj的顺序
            dataVOs.add(dataVO);
        }
        if (dataVOs.size() > 0){
            return dataVOs.get(0);
        }
        return null;
    }

    @Override
    public UserPO getUserPo(String uname, String password) {
        String hql = "from UserPO p where p.uname=? and p.password=?";
        List<UserPO> userPOs = userDao.findList(hql, uname, password);
        if (userPOs != null && userPOs.size()>0 ){
            return userPOs.get(0);
        }
        return null;
    }

    @Override
    public UserPO getUserPo(String uname) {
        String hql = "from UserPO p where p.uname=?";
        List<UserPO> userPOs = userDao.findList(hql, uname);
        if (userPOs != null && userPOs.size()>0 ){
            return userPOs.get(0);
        }
        return null;
    }

    @Override
    public String getUserPassword(long id) {
        String sql = "select password from user where id=?";
        List<Object> objects = userDao.findListBySql(sql, id);
        if (objects!=null && objects.size()>0){
            return objects.get(0).toString();
        }
        return null;
    }

    @Override
    public List<UserVO> listAllUser() {
        String sql = "select u.id,u.uname,u.name,u.sex,u.department,u.position,u.contact_way," +
                "dd.member as departmentCn,dd2.member as positionCn from user u " +
                "left join data_dictionary dd on u.department=dd.id" +
                " left join data_dictionary dd2 on u.position=dd2.id order by u.id desc";
        List<Object[]> objects = userDao.findListBySql(sql);
        List<UserVO> dataVOs = new ArrayList<>();
        for (Object[] obj:objects){
            UserVO dataVO = objectsIntoVo(obj);//注意obj的顺序
            dataVOs.add(dataVO);
        }
        if (dataVOs.size() > 0){
            return dataVOs;
        }
        return null;
    }

    @Override
    public Page listArticleById(String pageNoStr, String pageSizeStr, long id) {
        StringBuilder sql = new StringBuilder("select a.id,a.title,a.create_date,a.url,a.author,u.name,p.project_name from article a" +
                " left join user u on u.id=a.author" +
                " left join project_article pa on pa.article_id=a.id" +
                " left join project p on p.id=pa.project_id where u.id=" + id);

        sql.append(" order by a.id desc");
        //计算总数前缀
        String countSqlPre = "select count(*) ";
        //获取分页
        Page webPage = userDao.findPage(sql.toString(), countSqlPre, pageNoStr, pageSizeStr);
        //转换数据对象类型
        List<Object[]> objects = (List<Object[]>) webPage.getRows();
        List<ArticleVO> dataDictionaryVOs = new ArrayList<>();
        for (Object[] obj:objects){
            ArticleVO dataVO = objectsIntoArticleVo(obj);
            dataDictionaryVOs.add(dataVO);
        }
        webPage.setRows(dataDictionaryVOs);
        return webPage;
    }

    @Override
    public Page listUserByAnyParam(String pageNoStr, String pageSizeStr, String... params) {
        StringBuilder sql = new StringBuilder("select u.id,u.uname,u.name,u.sex,u.department,u.position,u.contact_way," +
                "dd.member as departmentCn,dd2.member as positionCn from user u " +
                "left join data_dictionary dd on u.department=dd.id" +
                " left join data_dictionary dd2 on u.position=dd2.id where 1=1 and u.id!=1"); //第一个用户是超级管理员

        if(null != params){
            if(params.length > 0 && !ObjectUtil.isNullOrEmpty(params[0])){
                sql.append(" and u.name like '%" + params[0] + "%'");
            }
            if(params.length > 1 && !ObjectUtil.isNullOrEmpty(params[1])){
                sql.append(" and u.position=" + params[1]);
            }
            if(params.length > 2 && !ObjectUtil.isNullOrEmpty(params[2])){
                sql.append(" and u.department=" + params[2]);
            }
        }
        sql.append(" order by u.id desc");
        //计算总数前缀
        String countSqlPre = "select count(*) ";
        //获取分页
        Page webPage = userDao.findPage(sql.toString(), countSqlPre, pageNoStr, pageSizeStr);
        //转换数据对象类型
        List<Object[]> objects = (List<Object[]>) webPage.getRows();
        List<UserVO> dataVOs = new ArrayList<>();
        for (Object[] obj:objects){
            UserVO dataVO = objectsIntoVo(obj);//注意obj的顺序
            dataVOs.add(dataVO);
        }
        webPage.setRows(dataVOs);
        return webPage;
    }

    @Override
    public void saveUser(UserPO userPO) throws Exception {
        //获取配置的用户默认密码
        String sql = "select member_value from data_dictionary where family_value=?";
        List<Object> defaultPWD = userDao.findListBySql(sql, DataDictionaryConsts.DATA_FAMILY_VALUE_USER_PASSWORD);

        //md5加密
        String passWord = "";
        if(!ObjectUtil.isNullOrEmpty(defaultPWD)){
            if(!ObjectUtil.isNullOrEmpty(defaultPWD.get(0))){
                passWord = DigestUtils.md5DigestAsHex(defaultPWD.get(0).toString().getBytes());
            }
        }

        //执行新增
        userPO.setPassword(passWord);
        userDao.saveOrUpdateObject(userPO);
    }

    @Override
    public boolean updateUser(UserPO userPO) throws Exception {
        StringBuilder sql = new StringBuilder("update user set ");
        if (!ObjectUtil.isNullOrEmpty(userPO.getPassword())){
            sql.append("password='" + userPO.getPassword() + "',");
        }
        if (!ObjectUtil.isNullOrEmpty(userPO.getName())){
            sql.append("name='" + userPO.getName() + "',");
        }
        if (!ObjectUtil.isNullOrEmpty(userPO.getSex())){
            sql.append("sex=" + userPO.getSex() + ",");
        }
        if (!ObjectUtil.isNullOrEmpty(userPO.getDepartment())){
            sql.append("department=" + userPO.getDepartment() + ",");
        }
        if (!ObjectUtil.isNullOrEmpty(userPO.getPosition())){
            sql.append("position=" + userPO.getPosition() + ",");
        }
        if (!ObjectUtil.isNullOrEmpty(userPO.getContactWay())){
            sql.append("contact_way='" + userPO.getContactWay() + "',");
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(" where id=?");
        return userDao.executeBySql(sql.toString(),userPO.getId());
    }


    @Override
    public void saveRole4User(long id, String[] roleIds) throws Exception {
        //删除用户原有角色
        String deleteSql = "delete from user_role where user_id=?";
        userDao.executeBySql(deleteSql, id);

        if (null != roleIds && roleIds.length > 0){
            //重新赋予权限
            StringBuilder addSql = new StringBuilder("insert user_role(user_id,role_id) values");
            for (int i = 0; i < roleIds.length; i++) {
                addSql.append("(" + id + "," + roleIds[i] + "),");
            }
            addSql.deleteCharAt(addSql.lastIndexOf(","));
            addSql.append(";");
            userDao.executeBySql(addSql.toString());
        }
    }

    @Override
    public boolean removeUserById(long id) throws Exception {
        String sql = "delete u.*,ur.* from user u left join user_role ur on ur.user_id=u.id where u.id=?";
        return userDao.executeBySql(sql,id);
    }

    /**
     * vo赋值
     * @param obj 值数组，必须按照方法指定的顺序,obj[0]不能为空表示ID
     * @return
     */
    private UserVO objectsIntoVo(Object... obj){
        UserVO userVO = new UserVO();
        userVO.setId(Long.valueOf(String.valueOf(obj[0])));
        if(obj.length>1 && !ObjectUtil.isNullOrEmpty(obj[1])){
            userVO.setUname(obj[1].toString());
        }
        if(obj.length>2 && !ObjectUtil.isNullOrEmpty(obj[2])){
            userVO.setName(obj[2].toString());
        }
        if(obj.length>3 && !ObjectUtil.isNullOrEmpty(obj[3])){
            userVO.setSex(Byte.parseByte(obj[3].toString()));
        }
        if(obj.length>4 && !ObjectUtil.isNullOrEmpty(obj[4])){
            userVO.setDepartment(Long.parseLong(obj[4].toString()));
        }
        if(obj.length>5 && !ObjectUtil.isNullOrEmpty(obj[5])){
            userVO.setPosition(Long.parseLong(obj[5].toString()));
        }
        if(obj.length>6 && !ObjectUtil.isNullOrEmpty(obj[6])){
            userVO.setContactWay(obj[6].toString());
        }
        if(obj.length>7 && !ObjectUtil.isNullOrEmpty(obj[7])){
            userVO.setDepartmentCn(obj[7].toString());
        }
        if(obj.length>8 && !ObjectUtil.isNullOrEmpty(obj[8])){
            userVO.setPositionCn(obj[8].toString());
        }

        return userVO;
    }

    /**
     * vo赋值
     * @param obj 值数组，必须按照方法指定的顺序,obj[0]不能为空表示ID
     * @return
     */
    private ArticleVO objectsIntoArticleVo(Object... obj){
        ArticleVO articleVO = new ArticleVO();
        articleVO.setId(Long.valueOf(String.valueOf(obj[0])));
        if(obj.length>1 && !ObjectUtil.isNullOrEmpty(obj[1])){
            articleVO.setTitle(obj[1].toString());
        }
        if(obj.length>2 && !ObjectUtil.isNullOrEmpty(obj[2])){
            articleVO.setCreateDate(Timestamp.valueOf(obj[2].toString()));
        }
        if(obj.length>3 && !ObjectUtil.isNullOrEmpty(obj[3])){
            articleVO.setUrl(obj[3].toString());
        }
        if(obj.length>4 && !ObjectUtil.isNullOrEmpty(obj[4])){
            articleVO.setAuthor(Long.parseLong(obj[4].toString()));
        }
        if(obj.length>5 && !ObjectUtil.isNullOrEmpty(obj[5])){
            articleVO.setAuthorCn(obj[5].toString());
        }
        if(obj.length>6 && !ObjectUtil.isNullOrEmpty(obj[6])){
            articleVO.setBeLongsTo(obj[6].toString());
        }

        return articleVO;
    }
}
