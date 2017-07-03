package com.wsz.service;

import com.wsz.common.Page;
import com.wsz.pojo.po.ArticlePO;
import com.wsz.pojo.po.ProjectPO;
import com.wsz.pojo.vo.ArticleVO;
import com.wsz.pojo.vo.ProjectLogVO;
import com.wsz.pojo.vo.ProjectVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 项目 业务接口
 * @author wanshenzhen  2017/3/18.
 */
public interface IProjectSercvice {
    /**
     * 获取指定ID的项目基本信息
     * @param id 项目ID
     * @return 空时返回null
     */
    ProjectVO getProjectById(long id);

    /**
     * 获取指定ID的项目基本信息
     * @param id 项目ID
     */
    ProjectPO getProjectPoById(long id);

    /**
     * 获取项目成员并标识,即查出所有的用户，然后标识出哪些是当前项目的成员
     * @param id 项目ID
     */
    List<Map<String, Object>> listProjectMemberById(long id);

    /**
     * 获取项目成员id与名称
     * @param id 项目ID
     * @return map:memberId-成员id、memberName-成员中文名
     */
    List<Map<String, Object>> listOnlyProjectMemberById(long id);

    /**
     * 根据项目名称获取项目基本信息
     * @param name 项目名称
     * @return 空时返回null
     */
    ProjectVO getProjectByName(String name);

    /**
     * 获取指定文章信息
     * @param articleId 文章id
     * @return
     */
    ArticleVO getArticleByArticleId(long articleId);

    /**
     * 获取指定用户创建的所有项目的基本表信息列表
     * @return 空时返回null
     */
    List<ProjectVO> listAllProject(long leadId);

    /**
     * 按条件获取项目基本表信息，分页
     * @param pageNo 当前页
     * @param pageSize 每页显示数
     * @param params 参数数组,必须按照顺序
     */
    Page listProjectByAnyParam(String pageNo, String pageSize, String... params);

    /**
     * 获取某个用户参与的所有项目列表，分页
     * @param userId 用户id
     * @param pageNo 当前页
     * @param pageSize 每页显示数
     * @param params 参数数组,必须按照顺序
     */
    Page listProjectByUserId(long userId, String pageNo, String pageSize, String... params);

    /**
     * 获取指定ID的项目的日志信息列表
     * @param id 项目ID
     * @return 空时返回null
     */
    List<ProjectLogVO> listProjectLogById(long id);

    /**
     * 添加一条项目信息，并记录日志
     * @param projectPO 项目表实体对象
     * @param memberIds 项目成员IDs
     * @return ok-成功，nameIsNull-项目名称为空,nameNotOnly-项目名称不唯一
     */
    String  saveProject(ProjectPO projectPO, String[] memberIds) throws Exception;

    /**
     * 保存项目笔记
     * @return string ok-成功，not-失败
     */
    String saveProjectNote(HttpServletRequest request, ArticlePO articlePO, String projectId, String content) throws Exception;

    /**
     * 修改项目基本表信息，并记录修改日志
     * @param projectPO 项目对象
     * @param updateMember 是否更改了项目成员 true-是
     * @param memberIds 项目成员
     * @param logInfo 修改备注信息，不能为空
     * @return ok-修改成功,logInfoIsNull-日志记录信息为空
     */
    String updateProject(ProjectPO projectPO, String logInfo, boolean updateMember, String[] memberIds) throws Exception;

    /**
     * 改变项目状态值，并根据需要判断是否记录日志（备注原因）
     * @param prjectId 项目id
     * @param status 更改后的状态值
     * @param info 备注信息
     * @param saveLog 是否记录日志
     * @return
     */
    String updateProjectStatus(long prjectId, String status, String info, boolean saveLog) throws Exception;

    /**
     * 更新文章标题和内容
     * @param title 标题
     * @param articleId 文章id
     * @param content 内容
     * @return String ok-成功，not-失败
     */
    String updateProjectNote(HttpServletRequest request, String title, String articleId, String content ) throws Exception;

    /**
     * 删除项目、项目成员及项目日志（项目下的任务不删除）
     * @param projectId 项目id
     */
    String removeProject(long projectId) throws Exception;

    /**
     * 删除文章及其所属关系
     * @param articleId 文章id
     * @return
     */
    String removeNote(long articleId) throws Exception;
}
