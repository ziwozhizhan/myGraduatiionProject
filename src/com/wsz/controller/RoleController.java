package com.wsz.controller;

import com.wsz.pojo.po.RolePO;
import com.wsz.pojo.vo.RoleVO;
import com.wsz.service.IPermissionService;
import com.wsz.service.IRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理 控制层
 * @author wanshenzhen  2017/4/14.
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;

    /**
     * 新增
     */
    @RequiresPermissions("jsgl_xz")
    @PostMapping("/saveRole.do")
    @ResponseBody
    public String saveRole(RolePO rolePO, BindingResult bindingResult){
        String result = "error";
        if (bindingResult.hasErrors()){
            return "param_error";//参数绑定错误
        }
        try {
            roleService.saveOrUpdateRole(rolePO);
            result = "ok";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 编辑: 获取待编辑数据
     */
    @RequiresPermissions("jsgl_bj")
    @PostMapping("/getRole.do")
    public String getRole(@RequestParam long roleId, Model model){
        RoleVO roleVO = roleService.getRoleById(roleId);
        model.addAttribute("updateRole",roleVO);
        return "theme/role/r_edit_modal_body";
    }

    /**
     * 编辑：保存
     */
    @RequiresPermissions("jsgl_bj")
    @PostMapping("/updateRole.do")
    @ResponseBody
    public String updateRole(RolePO rolePO, BindingResult bindingResult){
        String result = "error";
        if (bindingResult.hasErrors()){
            return "param_error";//参数绑定错误
        }
        try {
            roleService.saveOrUpdateRole(rolePO);
            result = "ok";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除
     */
    @RequiresPermissions("jsgl_sc")
    @PostMapping("/removeRole.do")
    @ResponseBody
    public String removeRole(@RequestParam long roleId){
        String result = "error";
        try {
            if (roleService.removeRoleById(roleId)){
                result = "ok";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 赋予权限：获取
     */
    @RequiresPermissions("jsgl_fyqx")
    @PostMapping("/listPermissions.do")
    public String listPermissions(@RequestParam long roleId, Model model){
        String json = permissionService.listAllPermissonJson4Role(roleId);
        model.addAttribute("permList",json);
        model.addAttribute("roleId",roleId);
        return "theme/role/r_permission_tree";
    }

    /**
     * 赋予权限：保存
     */
    @RequiresPermissions("jsgl_fyqx")
    @PostMapping("/saveRolePerms.do")
    @ResponseBody
    public String saveRolePerms(@RequestParam long roleId, String permissions){
        String[] permIds = new String[]{};
        String result = "error";
        if (!StringUtils.isEmpty(permissions)){
            permIds = permissions.split(",");
        }
        try {
            roleService.savePermission4Role(roleId, permIds);
            result = "ok";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 刷新
     */
    @GetMapping("/listRoles.do")
    public String listRoles(Model model){
        List<RoleVO> roleVOs = roleService.listAllRole();
        model.addAttribute("roleList",roleVOs);
        return "theme/role/r_list";
    }
}
