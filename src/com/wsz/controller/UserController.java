package com.wsz.controller;

import com.wsz.common.Page;
import com.wsz.common.consts.DataDictionaryConsts;
import com.wsz.common.util.JsonUtil;
import com.wsz.pojo.po.UserPO;
import com.wsz.pojo.vo.RoleVO;
import com.wsz.pojo.vo.UserVO;
import com.wsz.service.IDataDictionaryService;
import com.wsz.service.IRoleService;
import com.wsz.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户管理 控制层
 * @author wanshenzhen  2017/4/16.
 */
@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IDataDictionaryService dataDictionaryService;

    /**
     * 查询
     */
    @RequiresPermissions("yhgl_cx")
    @PostMapping("/listUsers.do")
    public String listUsers(String pageNo, String pageSize,String name, String position,
                            String department, Model model){
        Page webPage = userService.listUserByAnyParam(pageNo, pageSize, name, position, department);
        model.addAttribute("userWebPage", webPage);
        return "theme/user/u_list_and_pager";
    }

    /**
     * 新增
     */
    @RequiresPermissions("yhgl_xz")
    @PostMapping("/saveUser.do")
    @ResponseBody
    public String saveUser(UserPO userPO, BindingResult bindingResult){
        String result = "error";
        if (bindingResult.hasErrors()){
            return "param_error";//参数绑定错误
        }
        try {
            userService.saveUser(userPO);
            result = "ok";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 编辑：获取待编辑数据
     */
    @RequiresPermissions("yhgl_bj")
    @PostMapping("/getUser.do")
    public String getUser(@RequestParam long userId, Model model){
        //职位列表
        List<Map<String, String>> position = dataDictionaryService.listDataByFamilyValue(DataDictionaryConsts.DATA_FAMILY_VALUE_POSITION);
        //部门列表
        List<Map<String, String>> department = dataDictionaryService.listDataByFamilyValue(DataDictionaryConsts.DATA_FAMILY_VALUE_DEPARTMENT);
        //用户数据
        UserVO userVO = userService.getUserBaseMsgById(userId);

        model.addAttribute("userPosition", position);
        model.addAttribute("userDepartment", department);
        model.addAttribute("updateUser", userVO);
        return "theme/user/u_edit_modal_body";
    }

    /**
     * 编辑：保存
     */
    @RequiresPermissions("yhgl_bj")
    @PostMapping("/updateUser.do")
    @ResponseBody
    public String updateUser(UserPO userPO, BindingResult bindingResult){
        String result = "error";
        if (bindingResult.hasErrors()){
            return "param_error";//参数绑定错误
        }
        try {
            boolean updateUser = userService.updateUser(userPO);
            if (updateUser){
                result = "ok";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除
     */
    @RequiresPermissions("yhgl_sc")
    @PostMapping("/removeUser.do")
    @ResponseBody
    public String removeUser(@RequestParam long userId){
        try {
            if (userService.removeUserById(userId)){
                return "ok";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * 赋予角色：获取角色
     */
    @RequiresPermissions("yhgl_fyjs")
    @PostMapping("/listUserRoles.do")
    public String listUserRoles(@RequestParam long userId, Model model){
        List<RoleVO> userVOs = roleService.listUserRoles(userId);
        model.addAttribute("updateUserRoles", userVOs);
        model.addAttribute("userId", userId);
        return "theme/user/u_role_modal_body";
    }

    /**
     * 赋予角色：保存
     */
    @RequiresPermissions("yhgl_fyjs")
    @PostMapping("/saveUserRoles.do")
    @ResponseBody
    public String saveUserRoles(long userId, String userRoles){
        String[] roleIds  = new String[]{};
        if (!StringUtils.isEmpty(userRoles)){
            roleIds = userRoles.split(",");
        }

        try {
            userService.saveRole4User(userId, roleIds);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "error";
    }
}
