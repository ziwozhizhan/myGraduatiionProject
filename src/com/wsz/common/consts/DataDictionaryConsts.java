package com.wsz.common.consts;

/**
 * 数据字典 常量类<br/>
 * 若系统内需要用到数据库中的字段值，需要在此类中配置常量，方便维护修改
 * @author wanshenzhen  2017/4/15.
 */
public class DataDictionaryConsts {
    private DataDictionaryConsts(){}

    //以下常量对应数据库family_value字段
    public static final String DATA_FAMILY_VALUE_USER_PASSWORD = "yhmm"; //系统默认用户密码配置
    public static final String DATA_FAMILY_VALUE_POSITION = "zwlb"; //职位列表
    public static final String DATA_FAMILY_VALUE_DEPARTMENT = "bmlb"; //部门列表

}
