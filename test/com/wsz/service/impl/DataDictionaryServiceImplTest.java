package com.wsz.service.impl;


import com.wsz.pojo.po.DataDictionaryPO;
import com.wsz.service.IDataDictionaryService;
import com.wsz.test.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据字典 业务实现测试类
 * @author wanshenzhen  2017/3/14.
 */

public class DataDictionaryServiceImplTest extends BaseTest{
    private String dic = "测试字典service方法：";
    @Autowired
    private IDataDictionaryService dataDictionaryService;

    @Test
    public void getDataById(){
        long id = 1L;
        dataDictionaryService.getDataById(id);
        System.out.println(dic + "getDataById 获取单个ID对象成功==============");
    }

    @Test
    public void listAllData(){
        dataDictionaryService.listAllData();
        System.out.println(dic + "listAllData 获得所有字典数据成功===============");
    }

    @Test
    public void listDataByAnyParam(){
        dataDictionaryService.listDataByAnyParam("f","m");
        System.out.println(dic + "listDataByAnyParam 条件查询获取数据成功============");
    }

    @Test
    public void saveOrUpdateData() throws Exception {
        DataDictionaryPO dataDictionaryPO = new DataDictionaryPO();
        dataDictionaryPO.setFamily("测试字典类家族");
        dataDictionaryPO.setFamilyValue("fv");
        dataDictionaryService.saveData(dataDictionaryPO);
        System.out.println(dic + "saveOrUpdateData 添加成功===============");
    }

    @Test
    public void removeDataById() throws Exception{
        long id = 1L;
        dataDictionaryService.removeDataById(id);
    }

}
