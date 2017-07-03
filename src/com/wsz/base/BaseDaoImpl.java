package com.wsz.base;

import com.wsz.common.Page;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wanshenzhen on 2016/10/22.
 * 数据操作基类
 */
public class BaseDaoImpl implements IBaseDAO{
    private static Logger log = Logger.getLogger(BaseDaoImpl.class);
    @Autowired
    private HibernateTemplate hibernateTemplate;

    //====== 查询方法 =======
    public List findList(String hql,Object... values){
       return this.hibernateTemplate.find(hql,values);
    }

    public List findListByParamNames(String hql,String[]names,Object[] values){
        return this.hibernateTemplate.findByNamedParam(hql,names,values);
    }

    public List findListBySql(String sql,Object... values){
        List returnlist = this.hibernateTemplate.execute(new HibernateCallback<List>() {
            @Override
            public List doInHibernate(Session session){
                try {
                    SQLQuery query = session.createSQLQuery(sql);
                    for (int i = 0; i < values.length; i++) {
                        query.setParameter(i,values[i]);
                    }
                    return query.list();
                } catch (HibernateException e) {
                    log.error("findListBySql出错："+e.getMessage());
                    return null;
                } finally {
                    session.close();
                }
            }
        });
        return returnlist;
    }

    public Page findPage(String sql,String countSqlPre,String pageNoStr,String pageSizeStr){
        Page webPage = new Page();
        long pageNo = webPage.getCurrentPageNo();
        int pageSize = webPage.getPageSize();
        if(pageNoStr!=null && pageNoStr.length()>0){
            pageNo = Long.parseLong(pageNoStr);
        }
        if(pageSizeStr!=null && pageNoStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }

        int offset = (int) ((pageNo-1)*pageSize);
        List data = findForPage(sql, offset, pageSize);

        String countSql = countSqlPre+" "+removeSelect(sql);
        long count = Long.parseLong(findListBySql(countSql).get(0).toString());

        webPage = new Page(0, count, pageSize, data ,pageNo);
        return webPage;
    }

    public Object getObjectById(Object obj,int id){
        return this.hibernateTemplate.get(obj.getClass(),id);
    }

    public Object getObjectById(Object obj,long id){
        return this.hibernateTemplate.get(obj.getClass(),id);
    }

    public Object getObjectById(Object obj,String id){
        return this.hibernateTemplate.get(obj.getClass(),id);
    }

    public Object getObjectByAnytypeId(Class<?> obClass,Object id) {
        Object object = this.hibernateTemplate.get(obClass, (Serializable) id);
        return object;
    }

    //====== 保存方法 =======
    public void saveOrUpdateObject(Object obj)throws Exception{
        this.hibernateTemplate.saveOrUpdate(obj);
    }

    //====== 删除方法 =======
    public void deleteObject(Object obj) throws Exception{
        this.hibernateTemplate.delete(obj);
    }

    public void deleteAllObjet(List<Object> objs) throws Exception{
        this.hibernateTemplate.deleteAll(objs);
    }

    public Boolean deleteAllByHql(final String hql,Object... ids){
        Boolean result = this.hibernateTemplate.execute(new HibernateCallback<Boolean>() {
            @Override
            public Boolean doInHibernate(Session session){
                try {
                    Query query = session.createQuery(hql);
                    if(ids!=null && ids.length>0){
                        query.setParameterList("ids",ids);
                    }
                    query.executeUpdate();
                    return true;
                } catch (Exception e) {
                    log.error("deleteAllByHql()报错:" + e.getMessage());
                    return false;
                } finally {
                    session.close();
                }
            }
        });
        return result;
    }

    //====== 执行任意sql语句 =======
    public Boolean executeBySql(final String sql,Object... objs) throws Exception{
        Boolean result = this.hibernateTemplate.execute(new HibernateCallback<Boolean>() {
            @Override
            public Boolean doInHibernate(Session session){
                try {
                    SQLQuery query = session.createSQLQuery(sql);
                    if(objs!=null && objs.length>0){
                        for (int i = 0; i < objs.length; i++) {
                            query.setParameter(i,objs[i]);//预编译从 0 开始
                        }
                    }
                    query.executeUpdate();
                    return true;
                } catch (HibernateException e) {
                    log.error("executeBySql()报错:" + e.getMessage());
                    return false;
                } finally {
                    session.close();
                }
            }
        });
        return result;
    }

    //======== 本类支持方法，不允许其他类调用 ===============
    /**
     * 去除sql select子句,用于Page findPage(..)
     * @param sql
     * @return
     */
    private String removeSelect(String sql){
        Pattern p = Pattern.compile("\\(.*\\)",Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            int c = m.end()-m.start();
            m.appendReplacement(sb, getStr(c,"~"));
        }
        m.appendTail(sb);

        String replacedSql = sb.toString();

        int index = replacedSql.indexOf("from");

        //如果不存在
        if(index == -1) {
            index  = replacedSql.indexOf("FROM");
        }
        return sql.substring(index);
    }

    /**
     * 用于Page findPage(..)
     */
    private String getStr(int num, String str) {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 用于Page findPage(..)
     */
    private List findForPage(final String sql, final int offset, final int length) {
        List returnlist = this.hibernateTemplate.execute(new HibernateCallback<List>() {
            @Override
            public List doInHibernate(Session session){
                try {
                    SQLQuery query = session.createSQLQuery(sql);
                    query.setMaxResults(length);
                    query.setFirstResult(offset);
                    return query.list();
                } catch (HibernateException e) {
                    log.error("findListBySql出错："+e.getMessage());
                    return null;
                } finally {
                    session.close();
                }
            }
        });
        return returnlist;
    }
}
