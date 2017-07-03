package com.wsz.service;

import com.wsz.common.Page;
import com.wsz.pojo.po.UserPO;
import com.wsz.pojo.vo.ArticleVO;
import com.wsz.pojo.vo.UserVO;

import java.util.List;

/**
 * 用户管理 业务接口
 * @author wanshenzhen  2017/3/17.
 */
public interface IUserService  {
    /**
     * 获取指定ID的用户基本信息（用户表）
     * @param id 用户ID
     * @return
     */
    UserVO getUserBaseMsgById(long id);

    /**
     * 用于用户登录验证
     * @param uname 用户名
     * @param password 密码
     * @return
     */
    UserPO getUserPo(String uname, String password);

    /**
     * 用于用户权限验证
     * @param uname 用户名
     * @return
     */
    UserPO getUserPo(String uname);

    /**
     * 获取用户密码
     * @param id
     * @return
     */
    String getUserPassword(long id);

    /**
     * 《待完善，看业务是否需要？》
     * 获取指定ID的用户基本信息+角色
     * @param id 用户ID
     * @return
     */
//    UserVO getUserById(long id);

    /**
     * 获取所有用户基本信息列表
     * @return
     */
    List<UserVO> listAllUser();

    /**
     * 获取用户的笔记文章信息
     * @param pageNoStr 当前页
     * @param pageSizeStr 每页显示数
     * @param id 用户id
     * @return
     */
    Page listArticleById(String pageNoStr, String pageSizeStr, long id);

    /**
     * 条件查询用户，分页
     * @param params 条件参数数组，
     *               必须顺序为：（用户表）name、position、department
     * @return
     */
    Page listUserByAnyParam(String pageNoStr, String pageSizeStr, String... params);

    /**
     * 增加用户信息
     * @param userPO 用户对象
     */
    void saveUser(UserPO userPO) throws Exception;

    /**
     * 修改用户信息
     * @param userPO 用户对象
     * @return true-成功
     */
    boolean updateUser(UserPO userPO) throws Exception;

    /**
     * 为用户赋予角色，添加前删除原有角色
     * @param id 用户ID
     * @param roleIds 角色ID数组
     */
    void saveRole4User(long id,String[] roleIds) throws Exception;

    /**
     * 移除指定用户ID的基本信息和角色
     * @param id 用户ID
     */
    boolean removeUserById(long id) throws Exception;
}
