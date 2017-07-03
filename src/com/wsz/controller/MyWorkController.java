package com.wsz.controller;

import com.wsz.common.Page;
import com.wsz.common.util.CurrentUserUtil;
import com.wsz.pojo.po.ArticlePO;
import com.wsz.pojo.vo.ArticleVO;
import com.wsz.pojo.vo.ProjectLogVO;
import com.wsz.pojo.vo.ProjectTaskLogVO;
import com.wsz.service.IProjectSercvice;
import com.wsz.service.IProjectTaskService;
import com.wsz.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 主要工作 控制层
 * @author wanshenzhen  2017/4/25.
 */
@Controller
@RequestMapping("/admin/work")
public class MyWorkController {
    @Autowired
    private IProjectTaskService projectTaskService;
    @Autowired
    private IProjectSercvice projectSercvice;
    @Autowired
    private IUserService userService;

    /**
     * 待办任务：条件查询
     */
    @RequiresPermissions("dbrw_cx")
    @PostMapping("/listMyTask.do")
    public String listMyTask(String pageNo, String status, String urgency, Model model){
        //获取当前用户
        long userId = CurrentUserUtil.getUserId();
        if (StringUtils.isEmpty(status)){
            status = "0 or pt.status=1 or pt.status=2";
        }
        //获取项目任务分页
        Page webPage = projectTaskService.listTaskByAnyParam(pageNo, null, null, userId + "", status, urgency);

        model.addAttribute("myTaskWebPage", webPage);
        return "theme/work/mt_list_and_pager";
    }

    /**
     * 待办任务：任务日志
     */
    @PostMapping("/listMyTaskLog")
    public String listMyTaskLog(long taskId, Model model){
        List<ProjectTaskLogVO> logVOs = projectTaskService.listTaskLog(taskId);
        model.addAttribute("listMyTaskLog", logVOs);
        return "theme/work/mt_log_modal_body";
    }

    /**
     * 待办任务：改变任务状态值，并记录日志（备注原因）
     */
    @PostMapping("/updateMyTaskStatus.do")
    @ResponseBody
    public String updateMyTaskStatus(long taskId, String status, String msg){
        String result = "error";
        try {
            result = projectTaskService.updateTaskStatus(taskId, status, msg, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 待办任务 - 任务记录：新增任务日志
     */
    @PostMapping("/saveMyTaskLog.do")
    @ResponseBody
    public String saveMyTaskLog(long taskId,String msg){
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
     * 参与项目：条件查询
     */
    @RequiresPermissions("cyxm_cx")
    @PostMapping("/listPartakeProject.do")
    public String listPartakeProject(String projectName,String status,String name,String pageNo, Model model){
        //获取当前用户
        long userId = CurrentUserUtil.getUserId();
        //获取用户项目
        Page webPage = projectSercvice.listProjectByUserId(userId, pageNo, null, null, projectName, status, name);

        model.addAttribute("partakeProjectWebPage", webPage);
        return "theme/work/pp_list_and_pager";
    }

    /**
     * 参与项目：查看项目日志
     */
    @PostMapping("/listPartakeProjectLog.do")
    public String listPartakeProjectLog(long projectId, Model model){
        List<ProjectLogVO> projectLogVOs = projectSercvice.listProjectLogById(projectId);
        model.addAttribute("listPartakeProjectLog", projectLogVOs);
        return "theme/work/pp_log_modal_body";
    }

    /**
     * 工作随笔：保存
     */
    @PostMapping("/saveNote.do")
    @ResponseBody
    public String saveNote(String title, String projectId, String content, HttpServletRequest request){
        ArticlePO articlePO = new ArticlePO();
        articlePO.setTitle(title);
        articlePO.setCreateDate(new Timestamp(System.currentTimeMillis()));
        articlePO.setAuthor(CurrentUserUtil.getUserId());
        try {
            return projectSercvice.saveProjectNote(request, articlePO, projectId, content);
        } catch (Exception e) {
            e.printStackTrace();
            return "not";
        }
    }

    /**
     * 工作随笔：获取待编辑内容
     */
    @PostMapping("/getNote.do")
    @ResponseBody
    public ArticleVO getNote(long articleId){
        ArticleVO articleVO = projectSercvice.getArticleByArticleId(articleId);
        return articleVO;
    }

    /**
     * 工作随笔：保存编辑
     */
    @PostMapping("/updateNote.do")
    @ResponseBody
    public String updateNote(String title, String articleId, String content, HttpServletRequest request){
        try {
            return projectSercvice.updateProjectNote(request, title, articleId, content);
        } catch (Exception e) {
            e.printStackTrace();
            return "not";
        }
    }

    /**
     * 工作随笔：查询（或获取分页）
     */
    @PostMapping("/search.do")
    public String updateNote(String pageNo, Model model){
        //获取当前用户
        long userId = CurrentUserUtil.getUserId();
        //获取文章分页
        Page webPage = userService.listArticleById(pageNo, null, userId);

        model.addAttribute("noteWebPage", webPage);
        return "theme/work/n_list_and_pager";
    }

    /**
     * 工作随笔：删除笔记
     */
    @PostMapping("/removeNote.do")
    @ResponseBody
    public String removeNote(long articleId){
        try {
            return projectSercvice.removeNote(articleId);
        } catch (Exception e) {
            e.printStackTrace();
            return "not";
        }
    }
}
