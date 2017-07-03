package com.wsz.service.impl;

import com.wsz.service.IProjectTaskService;
import com.wsz.test.base.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 单元测试 项目任务业务接口实现类方法
 * @author wanshenzhen  2017/3/20.
 */
public class ProjectTaskServiceImplTest extends BaseTest {
    private String msg = "测试项目任务service方法：";

    @Autowired
    private IProjectTaskService projectTaskService;
}
