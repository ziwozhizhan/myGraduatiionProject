package com.wsz.service.impl;

import com.wsz.common.Page;
import com.wsz.common.util.ObjectUtil;
import com.wsz.dao.IDataDictionaryDao;
import com.wsz.pojo.po.DataDictionaryPO;
import com.wsz.pojo.vo.DataDictionaryVO;
import com.wsz.service.IDataDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanshenzhen  2017/3/14.
 */
@Service("dataDictionaryService")
public class DataDictionaryServiceImpl implements IDataDictionaryService{
    private final static Logger logger = LoggerFactory.getLogger(DataDictionaryServiceImpl.class);
    @Autowired
    private IDataDictionaryDao dataDictionaryDao;

    @Override
    public DataDictionaryVO getDataById(long id) {
        String sql = "select id,family,member,family_value,member_value,sort from data_dictionary" +
                " where id=?";
        List<Object[]> objects = dataDictionaryDao.findListBySql(sql,id);
        List<DataDictionaryVO> dataDictionaryVOs = new ArrayList<>();
        for (Object[] obj:objects){
            DataDictionaryVO dataVO = objectsIntoVo(obj);
            dataDictionaryVOs.add(dataVO);
        }
        if (dataDictionaryVOs.size() > 0){
            return dataDictionaryVOs.get(0);
        }
        return null;
    }

    @Override
    public List<DataDictionaryVO> listAllData() {
        String sql = "select id,family,member,family_value,member_value,sort from data_dictionary " +
                "order by sort desc";
        List<Object[]> objects = dataDictionaryDao.findListBySql(sql);
        List<DataDictionaryVO> dataDictionaryVOs = new ArrayList<>();
        for (Object[] obj:objects){
            DataDictionaryVO dataVO = objectsIntoVo(obj);
            dataDictionaryVOs.add(dataVO);
        }
        if (dataDictionaryVOs.size() > 0){
            return dataDictionaryVOs;
        }
        return null;
    }

    @Override
    public List<Map<String, String>> listDataByFamilyValue(String familyValue) {
        List<Map<String, String>> resultList = new ArrayList<>();

        String sql = "select id,member from data_dictionary where family_value=?";
        List<Object[]> objects = dataDictionaryDao.findListBySql(sql, familyValue);
        for (Object[] obj:objects){
            Map<String, String> map = new HashMap<>();
            map.put("id",obj[0] + "");
            map.put("member", obj[1] + "");
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public Page listDataByAnyParam(String pageNoStr, String pageSizeStr, String... params) {
        StringBuilder sql = new StringBuilder("select id,family,member,family_value,member_value,sort from " +
                "data_dictionary where 1=1");
        if (params != null){
            if(params.length>0 && !ObjectUtil.isNullOrEmpty(params[0])){
                sql.append(" and family like '%"+params[0]+"%'");
            }
            if(params.length>1 && !ObjectUtil.isNullOrEmpty(params[1])){
                sql.append(" and member like '%"+params[1]+"%'");
            }
            if(params.length>2 && !ObjectUtil.isNullOrEmpty(params[2])){
                sql.append(" and family_value='"+params[2]+"'");
            }
        }
        sql.append(" order by sort desc");
        //计算总数前缀
        String countSqlPre = "select count(*) ";
        //获取分页
        Page webPage = dataDictionaryDao.findPage(sql.toString(), countSqlPre, pageNoStr, pageSizeStr);
        //转换数据对象类型
        List<Object[]> objects = (List<Object[]>) webPage.getRows();
        List<DataDictionaryVO> dataDictionaryVOs = new ArrayList<>();
        for (Object[] obj:objects){
            DataDictionaryVO dataVO = objectsIntoVo(obj);
            dataDictionaryVOs.add(dataVO);
        }
        webPage.setRows(dataDictionaryVOs);
        return webPage;
    }

    @Override
    public String saveData(DataDictionaryPO dataDictionaryPO) throws Exception {
        /*
        验证：
        字典类值与同一字典类值下名称值组合不能重复
        */
        String sql = "select family_value,member_value from data_dictionary where 1=1 " +
                " and family_value='"+
                dataDictionaryPO.getFamilyValue()+"' and member_value='"+
                dataDictionaryPO.getMemberValue()+"'";
        List<Object[]> objs = dataDictionaryDao.findListBySql(sql);
        if(ObjectUtil.isNullOrEmpty(objs)){
            //信息保存入库
            dataDictionaryDao.saveOrUpdateObject(dataDictionaryPO);
            return "ok";
        }
        return "not";//验证不通过
    }

    @Override
    public String updateData(DataDictionaryPO dataDictionaryPO) throws Exception {
        dataDictionaryDao.saveOrUpdateObject(dataDictionaryPO);
        return "ok";
    }

    @Override
    public void removeDataById(long id) throws Exception{
        DataDictionaryPO dataDictionaryPO = new DataDictionaryPO();
        dataDictionaryPO.setId(id);
        dataDictionaryDao.deleteObject(dataDictionaryPO);
    }

    /**
     * 为数据字典展示对象赋值
     * @param obj 值数组，必须按照方法指定的顺序,obj[0]不能为空表示ID
     * @return
     */
    private DataDictionaryVO objectsIntoVo(Object... obj){
        DataDictionaryVO dataVO = new DataDictionaryVO();
        dataVO.setId(Long.valueOf(String.valueOf(obj[0])));
        if(obj.length>1 && !ObjectUtil.isNullOrEmpty(obj[1])){
            dataVO.setFamily(obj[1].toString());
        }
        if(obj.length>2 && !ObjectUtil.isNullOrEmpty(obj[2])){
            dataVO.setMember(obj[2].toString());
        }
        if(obj.length>3 && !ObjectUtil.isNullOrEmpty(obj[3])){
            dataVO.setFamilyValue(obj[3].toString());
        }
        if(obj.length>4 && !ObjectUtil.isNullOrEmpty(obj[4])){
            dataVO.setMemberValue(obj[4].toString());
        }
        if(obj.length>5 && !ObjectUtil.isNullOrEmpty(obj[5])){
            dataVO.setSort(Integer.valueOf(obj[5].toString()));
        }
        return dataVO;
    }
}
