package com.wsz.service.impl;

import com.wsz.common.Page;
import com.wsz.common.consts.ProjectTaskConsts;
import com.wsz.common.util.CurrentUserUtil;
import com.wsz.common.util.ObjectUtil;
import com.wsz.dao.IProjectTaskDao;
import com.wsz.pojo.po.ProjectTaskLogPO;
import com.wsz.pojo.po.ProjectTaskPO;
import com.wsz.pojo.vo.ProjectTaskLogVO;
import com.wsz.pojo.vo.ProjectTaskVO;
import com.wsz.pojo.vo.UserVO;
import com.wsz.service.IProjectTaskService;
import com.wsz.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanshenzhen  2017/3/20.
 */
@Service("projectTaskService")
 public class ProjectTaskServiceImpl implements IProjectTaskService{
    private final static Logger logger = LoggerFactory.getLogger(ProjectSercviceImpl.class);

    @Autowired
    private IProjectTaskDao projectTaskDao;
    @Autowired
    private IUserService userService;

    @Override
    public ProjectTaskVO getTaskById(long taskId) {
        String sql = "select pt.id,pt.task_name,pt.status,pt.urgency,pt.user_id,pt.expect_date" +
                " from project_task pt where pt.id=?";
        List<Object[]> objects = projectTaskDao.findListBySql(sql, taskId);
        ProjectTaskVO projectTaskVO = null;
        for (Object[] obj:objects){
            projectTaskVO = objectsIntoTaskVo(obj);
        }
        return projectTaskVO;
    }

    @Override
    public Page listTaskByAnyParam(String pageNoStr, String pageSizeStr, String... params) {
        StringBuilder sql = new StringBuilder("select pt.id,pt.task_name,pt.status,pt.urgency,pt.user_id,pt.expect_date," +
                "pt.complete_date,pt.completer,pt.creater from project_task pt where 1=1");
        if (params != null){
            if(params.length>0 && !ObjectUtil.isNullOrEmpty(params[0])){
                sql.append(" and pt.project_id=" + params[0]);
            }
            if(params.length>1 && !ObjectUtil.isNullOrEmpty(params[1])){
                sql.append(" and pt.user_id=" + params[1]);
            }
            if(params.length>2 && !ObjectUtil.isNullOrEmpty(params[2])){
                sql.append(" and (pt.status=" + params[2] +")");
            }
            if(params.length>3 && !ObjectUtil.isNullOrEmpty(params[3])){
                sql.append(" and pt.urgency=" + params[3]);
            }
        }
        sql.append(" order by pt.id desc");
        //计算总数前缀
        String countSqlPre = "select count(*) ";
        //获取分页
        Page webPage = projectTaskDao.findPage(sql.toString(), countSqlPre, pageNoStr, pageSizeStr);
        //转换数据对象类型
        List<Object[]> objects = (List<Object[]>) webPage.getRows();
        List<ProjectTaskVO> dataDictionaryVOs = new ArrayList<>();
        for (Object[] obj:objects){
            ProjectTaskVO dataVO = objectsIntoTaskVo(obj);
            dataDictionaryVOs.add(dataVO);
        }
        webPage.setRows(dataDictionaryVOs);
        return webPage;
    }

    @Override
    public List<ProjectTaskLogVO> listTaskLog(long taskId) {
        String sql = "select ptl.id,ptl.date,ptl.info from project_task_log ptl where ptl.project_task_id=?";
        List<Object[]> objects = projectTaskDao.findListBySql(sql, taskId);
        List<ProjectTaskLogVO> vos = new ArrayList<>();
        for (Object[] obj:objects){
            ProjectTaskLogVO vo = new ProjectTaskLogVO();
            vo = objectsIntoTaskLogVo(obj);
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public void saveTask(ProjectTaskPO projectTaskPO) throws Exception {
        //获取完成人名称
        long completerId = projectTaskPO.getUserId();
        UserVO userVO = userService.getUserBaseMsgById(completerId);
        String completer = userVO.getName();
        projectTaskPO.setCompleter(completer);
        //任务信息入库
        projectTaskDao.saveOrUpdateObject(projectTaskPO);

        //保存任务日志
        String info = "任务【" + projectTaskPO.getTaskName() + "】由 " + CurrentUserUtil.getUserName() + " 人创建";
        ProjectTaskLogPO logPO = new ProjectTaskLogPO();
        logPO.setDate(new Timestamp(System.currentTimeMillis()));
        logPO.setInfo(info);
        logPO.setProjectTaskId(projectTaskPO.getId());
        projectTaskDao.saveOrUpdateObject(logPO);
    }

    @Override
    public void saveTaskLog(long taskId, String info) throws Exception {
        ProjectTaskLogPO logPO = new ProjectTaskLogPO();
        logPO.setDate(new Timestamp(System.currentTimeMillis()));
        logPO.setInfo(info);
        logPO.setProjectTaskId(taskId);
        projectTaskDao.saveOrUpdateObject(logPO);
    }

    @Override
    public String updateTask(ProjectTaskPO projectTaskPO) throws Exception {
        //获取完成人名称
        long completerId = projectTaskPO.getUserId();
        UserVO userVO = userService.getUserBaseMsgById(completerId);
        String completer = userVO.getName();

        String sql = "update project_task set task_name=?,completer=?,expect_date=?,urgency=?,user_id=? where id=?";
        boolean update = projectTaskDao.executeBySql(sql,projectTaskPO.getTaskName(),completer
                ,projectTaskPO.getExpectDate(),projectTaskPO.getUrgency(),projectTaskPO.getUserId(),projectTaskPO.getId());
        if (!update){
            return "error";
        }

        //保存任务日志
        String info = "任务【" + projectTaskPO.getTaskName() + "】由 " + CurrentUserUtil.getUserName() + " 人做了修改";
        ProjectTaskLogPO logPO = new ProjectTaskLogPO();
        logPO.setDate(new Timestamp(System.currentTimeMillis()));
        logPO.setInfo(info);
        logPO.setProjectTaskId(projectTaskPO.getId());
        projectTaskDao.saveOrUpdateObject(logPO);

        return "ok";
    }

    @Override
    public String updateTaskStatus(long taskId, String status, String info, boolean saveLog) throws Exception {
        String sql = "update project_task set status=? where id=?";
        if ("3".equals(status)){
            sql = "update project_task set status=?,complete_date='" + new Timestamp(System.currentTimeMillis()) + "' where id=?";
        }
        boolean upResult = projectTaskDao.executeBySql(sql,status,taskId);
        if (!upResult){
            return "error";
        }

        //记录日志
        if (saveLog){
            ProjectTaskLogPO projectLogPO = new ProjectTaskLogPO();
            projectLogPO.setDate(new Timestamp(System.currentTimeMillis()));
            projectLogPO.setProjectTaskId(taskId);
            projectLogPO.setInfo(info);
            projectTaskDao.saveOrUpdateObject(projectLogPO);
        }
        return "ok";
    }

    @Override
    public boolean removeTask(long taskId) throws Exception {
        //删除任务
        String sql = "delete from project_task where id=?";
        boolean removeTask = projectTaskDao.executeBySql(sql, taskId);
        if (!removeTask){
            return false;
        }

        //删除任务日志
        boolean removeTaskLog = removeTaskLog(taskId);
        if (!removeTaskLog){
            return false;
        }
        return true;
    }

    /**
     * 删除任务日志
     * @param taskId 任务id
     */
    private boolean removeTaskLog(long taskId) throws Exception {
        String sql = "delete from project_task_log where project_task_id=?";
        return projectTaskDao.executeBySql(sql, taskId);
    }

    /**
     * vo赋值
     * @param obj 值数组，必须按照方法指定的顺序,obj[0]不能为空表示ID
     * @return
     */
    private ProjectTaskVO objectsIntoTaskVo(Object... obj){
        ProjectTaskVO dataVO = new ProjectTaskVO();
        dataVO.setId(Long.valueOf(String.valueOf(obj[0])));
        if(obj.length>1 && !ObjectUtil.isNullOrEmpty(obj[1])){
            dataVO.setTaskName(obj[1].toString());
        }
        if(obj.length>2 && !ObjectUtil.isNullOrEmpty(obj[2])){
            dataVO.setStatus(Byte.parseByte(obj[2].toString()));
            dataVO.setStatusCn(ProjectTaskConsts.meaningStatus(Integer.parseInt(obj[2].toString())));
        }
        if(obj.length>3 && !ObjectUtil.isNullOrEmpty(obj[3])){
            dataVO.setUrgency(Byte.parseByte(obj[3].toString()));
        }
        if(obj.length>4 && !ObjectUtil.isNullOrEmpty(obj[4])){
            dataVO.setUserId(Long.parseLong(obj[4].toString()));
        }
        if(obj.length>5 && !ObjectUtil.isNullOrEmpty(obj[5])){
            dataVO.setExpectDate(Timestamp.valueOf(obj[5].toString()));
        }
        if(obj.length>6 && !ObjectUtil.isNullOrEmpty(obj[6])){
            dataVO.setCompleteDate(Timestamp.valueOf(obj[6].toString()));
        }
        if(obj.length>7 && !ObjectUtil.isNullOrEmpty(obj[7])){
            dataVO.setCompleter(obj[7].toString());
        }
        if(obj.length>8 && !ObjectUtil.isNullOrEmpty(obj[8])){
            dataVO.setCreater(obj[8].toString());
        }
        return dataVO;
    }

    /**
     * vo赋值
     * @param obj 值数组，必须按照方法指定的顺序,obj[0]不能为空表示ID
     * @return
     */
    private ProjectTaskLogVO objectsIntoTaskLogVo(Object... obj){
        ProjectTaskLogVO dataVO = new ProjectTaskLogVO();
        dataVO.setId(Long.valueOf(String.valueOf(obj[0])));
        if(obj.length>1 && !ObjectUtil.isNullOrEmpty(obj[1])){
            dataVO.setDate(Timestamp.valueOf(obj[1].toString()));
        }
        if(obj.length>2 && !ObjectUtil.isNullOrEmpty(obj[2])){
            dataVO.setInfo(obj[2].toString());
        }
        return dataVO;
    }
}
