package com.wsz.controller;

import com.wsz.common.Page;
import com.wsz.pojo.po.DataDictionaryPO;
import com.wsz.pojo.vo.DataDictionaryVO;
import com.wsz.service.IDataDictionaryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 字典管理 控制层
 * @author wanshenzhen  2017/4/7.
 */
@Controller
@RequestMapping("/admin/data")
public class DataDictionaryController {
    @Autowired
    private IDataDictionaryService dataDictionaryService;

    /**
     * 查询，分页
     */
    @RequiresPermissions("sjzd_cx")
    @PostMapping("/search.do")
    public String listSearch(String pageNo, String pageSize, String family, String member, Model model){
        Page webPage = dataDictionaryService.listDataByAnyParam(pageNo, pageSize, family, member);
        model.addAttribute("dataWebPage",webPage);
        return "theme/dictionary/data_and_pager";
    }

    /**
     * 新增
     */
    @RequiresPermissions("sjzd_xz")
    @PostMapping("/saveData.do")
    @ResponseBody
    public String saveData(DataDictionaryPO dataDictionaryPO, BindingResult bindingResult){
        String result = "";
        if (bindingResult.hasErrors()){
            return "param_error";//参数绑定错误
        }
        try {
            result = dataDictionaryService.saveData(dataDictionaryPO);
        } catch (Exception e) {
            result = "error";
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 编辑：获取待编辑数据
     */
    @RequiresPermissions("sjzd_bj")
    @PostMapping("/getData.do")
    public String getData(@RequestParam long dataId, Model model){
        DataDictionaryVO dataDictionaryVO = dataDictionaryService.getDataById(dataId);
        model.addAttribute("updateData", dataDictionaryVO);
        return "theme/dictionary/data_edit_modal_body";
    }

    /**
     * 编辑：保存
     */
    @RequiresPermissions("sjzd_bj")
    @PostMapping("/updateData.do")
    @ResponseBody
    public String updateData(DataDictionaryPO dataDictionaryPO, BindingResult bindingResult){
        String result = "";
        if (bindingResult.hasErrors()){
            return "param_error";//参数绑定错误
        }
        try {
            result = dataDictionaryService.updateData(dataDictionaryPO);
        } catch (Exception e) {
            result = "error";
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除数据
     */
    @RequiresPermissions("sjzd_sc")
    @PostMapping("/removeData.do")
    @ResponseBody
    public String removeData(@RequestParam long dataId){
        try {
            dataDictionaryService.removeDataById(dataId);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
