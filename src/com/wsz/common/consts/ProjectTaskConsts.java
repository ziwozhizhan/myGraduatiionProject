package com.wsz.common.consts;

/**
 * 项目任务 常量类
 * @author wanshenzhen  2017/4/23.
 */
public class ProjectTaskConsts {
    private ProjectTaskConsts(){}

    //任务状态值
    public static final int TASK_STATUS_NOT_START = 0;
    public static final int TASK_STATUS_ON_GOINGG = 1;
    public static final int TASK_STATUS_SUSPEND = 2;
    public static final int TASK_STATUS_COMPLETE = 3;

    /**
     * 任务状态值的真实含义
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
                str = "完成";break;
            default:
                break;
        }
        return str;
    }
}
