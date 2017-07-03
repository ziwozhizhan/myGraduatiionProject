package com.wsz.controller;

import com.wsz.common.Page;
import com.wsz.common.consts.ProjectConsts;
import com.wsz.common.consts.ProjectTaskConsts;
import com.wsz.common.util.CurrentUserUtil;
import com.wsz.pojo.po.ProjectPO;
import com.wsz.pojo.po.ProjectTaskPO;
import com.wsz.pojo.vo.ProjectLogVO;
import com.wsz.pojo.vo.ProjectTaskLogVO;
import com.wsz.pojo.vo.ProjectTaskVO;
import com.wsz.pojo.vo.ProjectVO;
import com.wsz.service.IProjectSercvice;
import com.wsz.service.IProjectTaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 项目管理 控制层
 * @author wanshenzhen  2017/4/21.
 */
@Controller
@RequestMapping("/admin/project")
public class ProjectController {
    @Autowired
    private IProjectSercvice projectSercvice;
    @Autowired
    private IProjectTaskService projectTaskService;

    /**
     * 我的项目：刷新列表，分页
     */
    @PostMapping("/listMyProject.do")
    public String listMyProject(String pageNo, Model model){
        //当前用户id
        long userId = CurrentUserUtil.getUserId();
        Page webPage = projectSercvice.listProjectByAnyParam(pageNo, null, userId + "");
        model.addAttribute("myProjectWebPage", webPage);
        return "theme/project/mp_list_and_pager";
    }

    /**
     * 我的项目：创建项目
     */
    @RequiresPermissions("wdxm_cjxm")
    @PostMapping("/saveMyProject.do")
    @ResponseBody
    public String saveMyProject(String projectName, String projectUsers){
        String[] memberIds = new String[]{};
        String result = "error";

        if (!StringUtils.isEmpty(projectUsers)){
            memberIds = projectUsers.split(",");
        }

        try {
            ProjectPO projectPO = new ProjectPO();
            projectPO.setProjectName(projectName);
            projectPO.setStatus(Byte.parseByte(ProjectConsts.PROJECT_STATUS_NOT_START + ""));
            projectPO.setLeader(CurrentUserUtil.getUserName());
            projectPO.setLeaderId(CurrentUserUtil.getUserId());
            result = projectSercvice.saveProject(projectPO, memberIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 我的项目 - 编辑项目：获取待编辑信息
     */
    @RequiresPermissions("wdxm_bjxm")
    @PostMapping("/getMyProject.do")
    public String getMyProject(@RequestParam long projectId, Model model){
        // 获取项目信息 id,projectName
        ProjectVO projectVO = projectSercvice.getProjectById(projectId);
        // 获取项目成员
        List<Map<String, Object>> map =  projectSercvice.listProjectMemberById(projectId);

        model.addAttribute("updateMyProject", projectVO);
        model.addAttribute("updateMpUsers", map);
        return "theme/project/mp_edit_modal_body";
    }

    /**
     * 我的项目 - 编辑项目：保存
     */
    @RequiresPermissions("wdxm_bjxm")
    @PostMapping("/updateMyProject.do")
    @ResponseBody
    public String updateMyProject(long projectId, boolean updateMember, HttpServletRequest request){
        String result = "error";
        String[] memberIds = new String[]{};

        String projectName = request.getParameter("projectName");
        String projectUsers = request.getParameter("projectUsers");
        String msg = request.getParameter("msg");

        if (!StringUtils.isEmpty(projectUsers)){
            memberIds = projectUsers.split(",");
        }

        try {
            ProjectPO projectPO = projectSercvice.getProjectPoById(projectId);
            projectPO.setProjectName(projectName);
            result = projectSercvice.updateProject(projectPO, msg, updateMember, memberIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 我的项目 - 查看项目日志
     */
    @PostMapping("/listProjectLog.do")
    public String listProjectLog(long projectId, Model model){
        List<ProjectLogVO> projectLogVOs = projectSercvice.listProjectLogById(projectId);
        model.addAttribute("listProjectLog", projectLogVOs);
        return "theme/project/project_log_modal_body";
    }

    /**
     * 我的项目 - 改变项目状态值，并记录日志（备注原因）
     */
    @PostMapping("/updateMyProjectStatus.do")
    @ResponseBody
    public String updateMyProjectStatus(long projectId, String status, String msg){
        String result = "error";
        try {
            result = projectSercvice.updateProjectStatus(projectId, status, msg, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 我的项目 - 删除项目
     */
    @PostMapping("/removeMyProject.do")
    @ResponseBody
    public String removeMyProject(long projectId){
        String result = "error";
        try {
            result = projectSercvice.removeProject(projectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 项目任务：刷新列表，分页
     */
    @PostMapping("/listProjectTask.do")
    public String listProjectTask(long projectId, String pageNo, Model model){
        //获取项目任务分页
        Page webPage = projectTaskService.listTaskByAnyParam(pageNo, null, projectId + "");

        model.addAttribute("projectTaskWebPage", webPage);
        return "theme/project/pt_list_and_pager";
    }

    /**
     * 项目任务 - 创建任务：保存
     */
    @PostMapping("/saveProjectTask.do")
    @ResponseBody
    public String saveProjectTask(ProjectTaskPO projectTaskPO, BindingResult bindingResult, String jiezhiDate){
        String result = "error";
        if (bindingResult.hasErrors()){
            return "param_error";//参数绑定错误
        }

        jiezhiDate = jiezhiDate.replace("T", " ");
        jiezhiDate = jiezhiDate + ":00";
        try {
            projectTaskPO.setExpectDate(Timestamp.valueOf(jiezhiDate));
            projectTaskPO.setCreater(CurrentUserUtil.getUserName());
            projectTaskPO.setStatus(Byte.parseByte(ProjectTaskConsts.TASK_STATUS_NOT_START + ""));
            projectTaskService.saveTask(projectTaskPO);
            result = "ok";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 项目任务 - 编辑：获取待编辑数据
     */
    @PostMapping("/getProjectTask.do")
    public String getProjectTask(long projectId, long taskId, Model model){
        //获取项目成员
        List<Map<String, Object>> members = projectSercvice.listOnlyProjectMemberById(projectId);
        //获取任务信息
        ProjectTaskVO projectTaskVO = projectTaskService.getTaskById(taskId);
        String jiezhiDate = projectTaskVO.getExpectDate().toString();
        jiezhiDate = jiezhiDate.substring(0,jiezhiDate.lastIndexOf(":"));
        jiezhiDate = jiezhiDate.replace(" ", "T");

        model.addAttribute("projectId", projectId);
        model.addAttribute("projectMembers", members);
        model.addAttribute("updateTask", projectTaskVO);
        model.addAttribute("jiezhiDate", jiezhiDate);
        return "theme/project/pt_edit_modal_body";
    }

    /**
     * 项目任务 - 编辑：保存
     */
    @PostMapping("/updateProjectTask.do")
    @ResponseBody
    public String updateProjectTask(ProjectTaskPO projectTaskPO, BindingResult bindingResult, String jiezhiDate){
        String result = "error";
        if (bindingResult.hasErrors()){
            return "param_error";//参数绑定错误
        }

        jiezhiDate = jiezhiDate.replace("T", " ");
        jiezhiDate = jiezhiDate + ":00";
        try {
            projectTaskPO.setExpectDate(Timestamp.valueOf(jiezhiDate));
            result = projectTaskService.updateTask(projectTaskPO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 项目任务 - 任务日志
     */
    @PostMapping("/listProjectTaskLog")
    public String listProjectTaskLog(long taskId, Model model){
        List<ProjectTaskLogVO> logVOs = projectTaskService.listTaskLog(taskId);
        model.addAttribute("listProjectTaskLog", logVOs);
        return "theme/project/project_task_log_modal_body";
    }

    /**
     * 项目任务 - 删除
     */
    @PostMapping("/removeProjectTask.do")
    @ResponseBody
    public String removeProjectTask(long taskId){
        String result = "error";
        try {
            boolean removeTask = projectTaskService.removeTask(taskId);
            if (removeTask){
                result = "ok";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 项目任务 - 改变任务状态值，并记录日志（备注原因）
     */
    @PostMapping("/updateProjectTaskStatus.do")
    @ResponseBody
    public String updateProjectTaskStatus(long taskId, String status, String msg){
        String result = "error";
        try {
            result = projectTaskService.updateTaskStatus(taskId, status, msg, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 项目任务 - 任务记录：新增任务日志
     */
    @PostMapping("/saveProjectTaskLog.do")
    @ResponseBody
    public String saveProjectTaskLog(long taskId,String msg){
        String result = "error";
        try {
            projectTaskService.saveTaskLog(taskId, msg);
            result = "ok";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 所有项目 - 查询
     */
    @RequiresPermissions("syxm_cx")
    @PostMapping("/listAllProject.do")
    public String listAllProject(String projectName,String status,String name,String pageNo, Model model){
        //获取用户项目
        Page webPage = projectSercvice.listProjectByAnyParam(pageNo, null, null, projectName, status, name);

        model.addAttribute("allProjectWebPage", webPage);
        return "theme/project/ap_list_and_pager";
    }

    /**
     * 所有项目 - 项目任务：刷新列表，分页
     */
    @PostMapping("/listAllProjectTask.do")
    public String listAllProjectTask(long projectId, String pageNo, Model model){
        //获取项目任务分页
        Page webPage = projectTaskService.listTaskByAnyParam(pageNo, null, projectId + "");

        model.addAttribute("projectTaskWebPage", webPage);
        return "theme/project/ap_task_list_and_pager";
    }
}
