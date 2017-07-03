package com.wsz.service;

import com.wsz.common.Page;
import com.wsz.pojo.po.ProjectPO;
import com.wsz.pojo.po.ProjectTaskPO;
import com.wsz.pojo.vo.ProjectTaskLogVO;
import com.wsz.pojo.vo.ProjectTaskVO;

import java.util.List;

/**
 * 项目任务 业务接口
 * @author wanshenzhen  2017/3/20.
 */
public interface IProjectTaskService {

    /**
     * 获取任务信息
     * @param taskId 任务id
     */
    ProjectTaskVO getTaskById(long taskId);

    /**
     * 根据条件获取指定项目id的任务列表分页
     * @param pageNoStr 当前页
     * @param pageSizeStr 每页大小
     * @param params 参数数组，顺序 projectId、userId完成人
     * @return
     */
    Page listTaskByAnyParam(String pageNoStr, String pageSizeStr, String... params);

    /**
     * 获取指定任务id的日志列表
     * @param taskId 任务id
     * @return
     */
    List<ProjectTaskLogVO> listTaskLog(long taskId);

    /**
     * 新增项目任务
     * @param projectTaskPO 项目任务对象
     */
    void saveTask(ProjectTaskPO projectTaskPO) throws Exception;

    /**
     * 新增项目任务日志
     * @param taskId 任务id
     * @param info 备注信息
     * @throws Exception
     */
    void saveTaskLog(long taskId, String info) throws Exception;

    /**
     * 编辑项目任务
     * @param projectTaskPO 项目任务对象
     */
    String updateTask(ProjectTaskPO projectTaskPO) throws Exception;

    /**
     * 改变任务状态值，并根据需要判断是否记录日志（备注原因）
     * @param taskId 任务id
     * @param status 更改后的状态值
     * @param info 备注信息
     * @param saveLog 是否记录日志
     * @return
     */
    String updateTaskStatus(long taskId, String status, String info, boolean saveLog) throws Exception;

    /**
     * 删除任务及日志
     * @param taskId 任务id
     * @return true-成功
     */
    boolean removeTask(long taskId) throws Exception;
}
