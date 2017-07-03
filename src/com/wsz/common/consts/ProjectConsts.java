package com.wsz.common.consts;

/**
 * 项目 常量类
 * @author wanshenzhen  2017/3/18.
 */
public class ProjectConsts {
    private ProjectConsts (){}

    //项目状态
    public static final int PROJECT_STATUS_NOT_START = 0;
    public static final int PROJECT_STATUS_ON_GOINGG = 1;
    public static final int PROJECT_STATUS_SUSPEND = 2;
    public static final int PROJECT_STATUS_INVALID = 3;
    public static final int PROJECT_STATUS_COMPLETE = 4;

    /**
     * 项目状态值的真实含义
     */
    public static String meaningStatus(int value){
        String str = "";
        switch (value){
            case 0:
                str = "未启动";break;
            case 1:
                str = "进行中";break;
            case 2:
                str = "暂停";break;
            case 3:
                str = "作废";break;
            case 4:
                str = "完成";break;
            default:
                break;
        }
        return str;
    }
}
