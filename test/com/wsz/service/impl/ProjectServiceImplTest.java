package com.wsz.service.impl;

import com.wsz.pojo.po.ProjectPO;
import com.wsz.pojo.vo.ProjectLogVO;
import com.wsz.pojo.vo.ProjectVO;
import com.wsz.service.IProjectSercvice;
import com.wsz.test.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;

/**
 * 单元测试 项目业务接口实现类方法
 * @author wanshenzhen  2017/3/18.
 */
public class ProjectServiceImplTest extends BaseTest {
    private String msg = "测试项目service方法：";
    @Autowired
    private IProjectSercvice projectSercvice;

    @Test
    public void getProjectById(){
        long id = 1L;
        ProjectVO p = projectSercvice.getProjectById(id);
        System.out.println(null==p?"无数据":p.toString());
        System.out.println(msg + "getProjectById 获取指定ID的项目基本信息和项目成员成功===========");
    }

    @Test
    public void getProjectByName(){
        String name = "ww";
        ProjectVO projectVO = projectSercvice.getProjectByName(name);
        System.out.println(projectVO);
        System.out.println(msg + "getProjectByName 根据项目名称获取项目基本信息成功==========");
    }

    @Test
    public void listAllProject(){
//        List<ProjectVO> p = projectSercvice.listAllProject();
//        System.out.println(p);
        System.out.println(msg + "listAllProject 获取所有项目的基本表信息列表成功============");
    }

    @Test
    public void listProjectLogById(){
        long id = 7L;
        List<ProjectLogVO> p = projectSercvice.listProjectLogById(id);
        System.out.println(p);
        System.out.println(msg + "listProjectLogById 获取指定ID的项目的日志信息列表成功============");
    }

    @Test
    public void saveProject() throws Exception {
        ProjectPO p = new ProjectPO();
        p.setProjectName("ww");
        p.setStatus((byte) 0);
        String[] m = new String[]{"1","2"};
        String result = projectSercvice.saveProject(p,m);
        System.out.println("结果：" + result);
        System.out.println(msg + "saveProject 添加一条项目信息，并记录日志成功==========");
    }

    @Test
    public void updateProject() throws Exception {
        ProjectPO p = new ProjectPO();
        p.setId(1L);
        p.setProjectName("ww");
        String[] m = new String[]{"1","2"};
        String result = projectSercvice.updateProject(p,"info",true,m);
        System.out.println("结果：" + result);
        System.out.println(msg + "updateProject 修改项目基本表信息，并记录修改日志成功==========");
    }
}
