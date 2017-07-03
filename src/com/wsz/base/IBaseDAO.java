package com.wsz.base;

import com.wsz.common.Page;

import java.util.List;

/**
 * Created by wanshenzhen on 2016/10/29.
 * 数据操作基类接口
 */
public interface IBaseDAO {
    //========= 查询方法 ============
    /**
     * 预编译取值
     */
    public List findList(String hql,Object... values);

    /**
     * 根据属性名获取对象集合
     * @param names 属性名数组
     * @param values 对应属性值
     */
    public List findListByParamNames(String hql,String[]names,Object[] values);

    /**
     * 原生sql查询,预编译
     */
    public List findListBySql(String sql,Object... values);

    /**
     * 获取分页
     * @param sql 查询语句
     * @param countSqlPre 获取总数前缀 如：select count(*)
     * @param pageNoStr 当前页（使用前先验证是否存在）
     * @param pageSizeStr 每页显示数
     * @return
     */
    public Page findPage(String sql,String countSqlPre,String pageNoStr,String pageSizeStr);

    public Object getObjectById(Object obj,int id);
    public Object getObjectById(Object obj,long id);
    public Object getObjectById(Object obj,String id);
    public Object getObjectByAnytypeId(Class<?> obClass,Object id);

    //========= 保存方法 ============
    public void saveOrUpdateObject(Object obj) throws Exception;

    //========= 删除方法 ============
    public void deleteObject(Object obj) throws Exception;
    public void deleteAllObjet(List<Object> objs) throws Exception;

    /**
     * 使用hql语句删除数据，预编译时hql语句id取名 :ids<br/>
     * 如：delete form User where id in :ids
     * @param hql hql语句
     * @param ids id集合 可为空
     * @return true 成功 false 失败
     */
    public Boolean deleteAllByHql(final String hql,Object... ids);

    //========= 执行任意sql语句 ============
    public Boolean executeBySql(final String sql,Object... objs) throws Exception;
}
