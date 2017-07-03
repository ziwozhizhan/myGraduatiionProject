package com.wsz.service;

import com.wsz.common.Page;
import com.wsz.pojo.po.DataDictionaryPO;
import com.wsz.pojo.vo.DataDictionaryVO;

import java.util.List;
import java.util.Map;

/**
 * 数据字典 业务接口
 * @author wanshenzhen  2017/3/14.
 */
public interface IDataDictionaryService {
    /**
     * 查询：获取指定ID信息 （本项目中实际是为编辑时使用，编辑对象应与新增一直，改为DataDictionaryPO）
     * @param id
     * @return 为空时返回null
     */
    DataDictionaryVO getDataById(long id);

    /**
     * 查询：获得所有字典数据 （未用到）
     * @return 为空时返回null
     */
    List<DataDictionaryVO> listAllData();

    /**
     * 根据字典类别值返回字典数据
     * @param familyValue 字典类别值
     * @return map:id、member
     */
    List<Map<String, String>> listDataByFamilyValue(String familyValue);

    /**
     * 查询：条件查询获取分页
     * @param pageNoStr 当前页
     * @param pageSizeStr 每页显示数
     * @param params 参数数组,必须按照顺序 family、member、family_value
     * @return 为空时返回null
     */
    Page listDataByAnyParam(String pageNoStr, String pageSizeStr, String... params);

    /**
     * 新增字典数据
     * @param dataDictionaryPO
     */
    String saveData(DataDictionaryPO dataDictionaryPO) throws Exception;

    /**
     * 修改字典数据
     * @param dataDictionaryPO
     */
    String updateData(DataDictionaryPO dataDictionaryPO) throws Exception;

    /**
     * 删除：依据ID删除字典数据
     * @param id
     */
    void removeDataById(long id) throws Exception;
}
