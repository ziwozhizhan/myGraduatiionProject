package com.wsz.controller;

import com.wsz.common.Page;
import com.wsz.common.consts.DataDictionaryConsts;
import com.wsz.common.consts.ProjectConsts;
import com.wsz.common.util.CurrentUserUtil;
import com.wsz.pojo.po.ProjectPO;
import com.wsz.pojo.vo.ProjectVO;
import com.wsz.pojo.vo.RoleVO;
import com.wsz.pojo.vo.UserVO;
import com.wsz.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 页面跳转；
 * 初始化页面数据
 * @author wanshenzhen  2017/4/6.
 */
@Controller
@RequestMapping("/admin/main/")
public class MainController {
    @Autowired
    private ILoginAndExitService loginAndExitService;
    @Autowired
    private IDataDictionaryService dataDictionaryService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IProjectSercvice projectSercvice;
    @Autowired
    private IProjectTaskService projectTaskService;

    @RequestMapping("/test.do")
    public void test(){
    }

    /**
     * 跳转到 主页面
     */
    @GetMapping("/goMain.do")
    public String goMain(){
        return "theme/main";
    }

    /**
     * 退出系统
     */
    @GetMapping("/exit.do")
    public String goExit(){
        loginAndExitService.exit();
        return "theme/login";
    }

    /**
     * 跳转到 数据字典 页面，并加载初始数据
     */
    @RequiresPermissions("sjzd")
    @GetMapping("/goData.do")
    public String goData(Model model){
        Page webPage = dataDictionaryService.listDataByAnyParam("1", null);
        model.addAttribute("dataWebPage",webPage);
        return "theme/dictionary/data_dictionary";
    }

    /**
     * 跳转到 权限管理 页面，并加载初始数据
     */
    @RequiresPermissions("qxgl")
    @GetMapping("/goPermission.do")
    public String goPermission(Model model){
        String json = permissionService.listAllPermissonJson();
        model.addAttribute("permissionJson",json);
        return "theme/permission/permission";
    }

    /**
     * 跳转到 角色管理 页面，并加载初始数据
     */
    @RequiresPermissions("jsgl")
    @GetMapping("/goRole.do")
    public String goRole(Model model){
        List<RoleVO> roleVOs = roleService.listAllRole();
        model.addAttribute("roleList",roleVOs);
        return "theme/role/role";
    }

    /**
     * 跳转到 用户管理 页面，并加载初始数据
     */
    @RequiresPermissions("yhgl")
    @GetMapping("/goUser.do")
    public String goUser(Model model){
        //职位列表
        List<Map<String, String>> position = dataDictionaryService.listDataByFamilyValue(DataDictionaryConsts.DATA_FAMILY_VALUE_POSITION);
        //部门列表
        List<Map<String, String>> department = dataDictionaryService.listDataByFamilyValue(DataDictionaryConsts.DATA_FAMILY_VALUE_DEPARTMENT);
        //用户分页 userWebPage
        Page webPage = userService.listUserByAnyParam("1", null);

        model.addAttribute("userPosition", position);
        model.addAttribute("userDepartment", department);
        model.addAttribute("userWebPage", webPage);
        return "theme/user/user";
    }

    /**
     * 跳转到 我的项目 页面，并加载初始数据
     */
    @RequiresPermissions("wdxm")
    @GetMapping("/goMyProject.do")
    public String goMyProject(Model model){
        //当前用户id
        long userId = CurrentUserUtil.getUserId();
        //获取所有用户列表
        List<UserVO> userVOs = userService.listAllUser();
        //获取用户项目
        Page webPage = projectSercvice.listProjectByAnyParam("1", null, userId + "");

        model.addAttribute("allUsers", userVOs);
        model.addAttribute("myProjectWebPage", webPage);
        return "theme/project/my_project";
    }

    /**
     * 跳转到 我的项目-项目任务 页面，并加载指定项目id的任务列表数据
     */
    @GetMapping("/goProjectTask.do")
    public String goProjectTask(long projectId, Model model){
        //获取项目id、项目名称name
        ProjectPO projectPO = projectSercvice.getProjectPoById(projectId);
        int status = projectPO.getStatus();
        boolean canCreateProject = true;
        if (status == ProjectConsts.PROJECT_STATUS_INVALID || status == ProjectConsts.PROJECT_STATUS_COMPLETE){
            canCreateProject = false;
        }
        //获取项目成员
        List<Map<String, Object>> members = projectSercvice.listOnlyProjectMemberById(projectId);
        //获取项目任务分页
        Page webPage = projectTaskService.listTaskByAnyParam("1", null, projectId + "");

        model.addAttribute("projectId", projectPO.getId());
        model.addAttribute("canCreateProject", canCreateProject);
        model.addAttribute("projectMembers", members);
        model.addAttribute("projectName", projectPO.getProjectName());
        model.addAttribute("projectTaskWebPage", webPage);
        return "theme/project/project_task";
    }

    /**
     * 跳转到 所有项目 页面，并加载指定项目id的任务列表数据
     */
    @RequiresPermissions("syxm")
    @GetMapping("/goAllProject.do")
    public String goAllProject( Model model){
        //获取用户项目
        Page webPage = projectSercvice.listProjectByAnyParam("1", null);

        model.addAttribute("allProjectWebPage", webPage);
        return "theme/project/all_project";
    }

    /**
     * 跳转到 所有项目-项目任务 页面，并加载指定项目id的任务列表数据
     */
    @GetMapping("/goAllProjectTask.do")
    public String goAllProjectTask(long projectId, Model model){
        //获取项目id、项目名称name
        ProjectPO projectPO = projectSercvice.getProjectPoById(projectId);
        //获取项目任务分页
        Page webPage = projectTaskService.listTaskByAnyParam("1", null, projectId + "");

        model.addAttribute("projectId", projectPO.getId());
        model.addAttribute("projectName", projectPO.getProjectName());
        model.addAttribute("projectTaskWebPage", webPage);
        return "theme/project/ap_task";
    }

    /**
     * 跳转到 个人中心 页面，并加载个人信息
     */
    @RequiresPermissions("grzx")
    @GetMapping("/goPersonalCenter.do")
    public String goPersonalCenter(Model model){
        //获取当前用户id
        long userId = CurrentUserUtil.getUserId();
        //获取用户信息
        UserVO userVO = userService.getUserBaseMsgById(userId);
        //职位列表
        List<Map<String, String>> position = dataDictionaryService.listDataByFamilyValue(DataDictionaryConsts.DATA_FAMILY_VALUE_POSITION);
        //部门列表
        List<Map<String, String>> department = dataDictionaryService.listDataByFamilyValue(DataDictionaryConsts.DATA_FAMILY_VALUE_DEPARTMENT);

        model.addAttribute("personalUserPosition", position);
        model.addAttribute("personalUserDepartment", department);
        model.addAttribute("personalUser", userVO);
        return "theme/personal/personal_center";
    }

    /**
     * 跳转到 待办任务 页面，并加载待办任务列表
     */
    @RequiresPermissions("dbrw")
    @GetMapping("/goMyTask.do")
    public String goMyTask(Model model){
        //获取当前用户
        long userId = CurrentUserUtil.getUserId();
        //限制状态：非完成
        String status = "0 or pt.status=1 or pt.status=2";
        //获取项目任务分页
        Page webPage = projectTaskService.listTaskByAnyParam("1", null, null, userId+"", status);

        model.addAttribute("myTaskWebPage", webPage);
        return "theme/work/my_task";
    }

    /**
     * 跳转到 参与项目 页面，并加载我的任务列表
     */
    @RequiresPermissions("cyxm")
    @GetMapping("/goPartakeProject.do")
    public String goPartakeProject(Model model){
        //获取当前用户
        long userId = CurrentUserUtil.getUserId();
        //获取用户项目
        Page webPage = projectSercvice.listProjectByUserId(userId,"1", null);

        model.addAttribute("partakeProjectWebPage", webPage);
        return "theme/work/partake_project";
    }

    /**
     * 跳转到 工作随笔 页面，并加载初始数据
     */
    @RequiresPermissions("gzsb")
    @GetMapping("/goNote.do")
    public String goNote(Model model){
        //获取当前用户
        long userId = CurrentUserUtil.getUserId();
        //获取用户所有项目列表
        List<ProjectVO> listVo = projectSercvice.listAllProject(userId);
        //获取文章分页
        Page webPage = userService.listArticleById("1", null, userId);

        model.addAttribute("listPojectVo", listVo);
        model.addAttribute("noteWebPage", webPage);
        return "theme/work/note";
    }
}
