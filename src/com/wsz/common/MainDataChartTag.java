package com.wsz.common;

import com.wsz.common.util.CurrentUserUtil;
import com.wsz.dao.IProjectDao;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页数据柱状图表
 * @author wanshenzhen  2017/5/16.
 */
public class MainDataChartTag implements TemplateMethodModelEx {
    @Autowired
    private IProjectDao projectDao;

    @Override
    public Object exec(List list) throws TemplateModelException {
        Map<String, Object> map = new HashMap<>();
        Object allProject = 0;
        Object takePoject = 0;
        Object notProject = 0;
        Object notTask = 0;

        //获取 系统总项目数
        String allProjectSql = "select count(*) from project";
        List<Object> allProjects = projectDao.findListBySql(allProjectSql);
        if (allProjects != null && allProjects.size() > 0)
            allProject = allProjects.get(0);

        //获取 我参与项目数
        long userId = CurrentUserUtil.getUserId();
        String takePojectSql = "select count(*) from project where leader_id=?";
        List<Object> takePojects = projectDao.findListBySql(takePojectSql, userId);
        if (takePojects != null && takePojects.size() > 0)
            takePoject = takePojects.get(0);

        //获取 我未完成项目数
        String notProjectSql = "select count(*) from project where leader_id=? and status in (0,1,2)";
        List<Object> notProjects = projectDao.findListBySql(notProjectSql, userId);
        if (notProjects != null && notProjects.size() > 0)
            notProject = notProjects.get(0);

        //获取 我未完成任务数
        String notTaskSql = "select count(*) from project_task where user_id=? and status in (0,1,2)";
        List<Object> notTasks = projectDao.findListBySql(notTaskSql, userId);
        if (notTasks != null && notTasks.size() > 0)
            notTask = notTasks.get(0);

        map.put("allProject", allProject);
        map.put("takeProject", takePoject);
        map.put("notProject", notProject);
        map.put("notTask", notTask);

        return map;
    }
}
