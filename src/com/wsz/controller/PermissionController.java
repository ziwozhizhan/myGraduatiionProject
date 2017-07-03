package com.wsz.controller;

import com.wsz.pojo.po.PermissionPO;
import com.wsz.pojo.vo.PermissionVO;
import com.wsz.service.IPermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 权限管理 控制层
 * @author wanshenzhen  2017/4/9.
 */
@Controller
@RequestMapping("/admin/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    /**
     * 新增
     */
    @RequiresPermissions("qxgl_xz")
    @PostMapping("/savePermission.do")
    @ResponseBody
    public String savePermission(PermissionPO permissionPO, BindingResult bindingResult){
        String result = "";
        if (bindingResult.hasErrors()){
            return "param_error";//参数绑定错误
        }
        try {
            result = permissionService.savePermission(permissionPO);
        } catch (Exception e) {
            result = "error";
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 编辑：获取待编辑数据
     */
    @RequiresPermissions("qxgl_bj")
    @PostMapping("/getPermission.do")
    public String getPermission(@RequestParam long permId, Model model){
        PermissionVO permissionVO = permissionService.getPermissionById(permId);
        model.addAttribute("updatePermission",permissionVO);
        return "theme/permission/p_edit_modal_body";
    }

    /**
     * 编辑：保存更新
     */
    @RequiresPermissions("qxgl_bj")
    @PostMapping("/updatePermission.do")
    @ResponseBody
    public String updatePermission(PermissionPO permissionPO, BindingResult bindingResult){
        String result = "error";
        if (bindingResult.hasErrors()){
            return "param_error";//参数绑定错误
        }
        try {
            if (permissionService.updatePermission(permissionPO)){
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
    @RequiresPermissions("qxgl_sc")
    @PostMapping("/removePermission.do")
    @ResponseBody
    public String removePermission(@RequestParam long permId){
        try {
            permissionService.removePermissonById(permId);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 刷新页面数据
     */
    @GetMapping("/listPermission.do")
    public String listPermission(Model model){
        String json = permissionService.listAllPermissonJson();
        model.addAttribute("permissionJson",json);
        return "theme/permission/p_list";
    }

}
