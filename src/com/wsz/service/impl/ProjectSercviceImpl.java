package com.wsz.service.impl;

import com.wsz.common.Page;
import com.wsz.common.consts.ProjectConsts;
import com.wsz.common.util.CurrentUserUtil;
import com.wsz.common.util.ObjectUtil;
import com.wsz.dao.IProjectDao;
import com.wsz.pojo.po.ArticlePO;
import com.wsz.pojo.po.ProjectLogPO;
import com.wsz.pojo.po.ProjectPO;
import com.wsz.pojo.vo.ArticleVO;
import com.wsz.pojo.vo.ProjectLogVO;
import com.wsz.pojo.vo.ProjectVO;
import com.wsz.pojo.vo.UserVO;
import com.wsz.service.IProjectSercvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wanshenzhen  2017/3/18.
 */
@Service("projectService")
public class ProjectSercviceImpl implements IProjectSercvice {
    private final static Logger logger = LoggerFactory.getLogger(ProjectSercviceImpl.class);

    @Autowired
    private IProjectDao projectDao;

    @Override
    public ProjectVO getProjectById(long id) {
        String sql = "select p.id,p.project_name,p.status,p.leader from project p where p.id=?";
        List<Object[]> objects = projectDao.findListBySql(sql,id);
        ProjectVO projectVO = null;
        List<UserVO> projectUsers = new ArrayList<>();
        for (Object[] obj:objects){
            if(null == projectVO){
                projectVO = objectsIntoVo(obj);
            }
        }

        return projectVO;
    }

    @Override
    public ProjectPO getProjectPoById(long id) {
        return (ProjectPO) projectDao.getObjectByAnytypeId(ProjectPO.class, id);
    }

    @Override
    public List<Map<String, Object>> listProjectMemberById(long id) {
        List<Map<String, Object>> resultMap = new ArrayList<>();

        //获取所有用户
        String sql = "select id,name from user";
        List<Object[]> users = projectDao.findListBySql(sql);

        //获取当前项目的用户
        String pSql = "select user_id from project_user where project_id=?";
        List<Object> pUsers = projectDao.findListBySql(pSql, id);

        for (Object[] obj:users){
            Map<String, Object> map = new HashMap<>();
            map.put("id", obj[0]);
            map.put("name", obj[1]);
            map.put("projectUser", false);

            projectUsers:for (Object pObj:pUsers){
                if (pObj.equals(obj[0])){
                    map.put("projectUser", true);
                    break projectUsers;
                }
            }

            resultMap.add(map);
        }
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> listOnlyProjectMemberById(long id) {
        List<Map<String, Object>> resultMap = new ArrayList<>();

        String sql = "select u.id,u.name from project_user pu left join user u on u.id=pu.user_id where project_id=?";
        List<Object[]> objects = projectDao.findListBySql(sql, id);
        for (Object[] obj:objects){
            Map<String, Object> map = new HashMap<>();
            map.put("memberId", obj[0]);
            map.put("memberName", obj[1]);
            resultMap.add(map);
        }
        return resultMap;
    }

    @Override
    public ProjectVO getProjectByName(String name) {
        if(StringUtils.isEmpty(name)){
            return null;
        }
        String sql = "select p.id,p.project_name,p.status,p.leader from project p where p.project_name=?";
        List<Object[]> objects = projectDao.findListBySql(sql,name);
        ProjectVO projectVO = null;
        for (Object[] obj:objects){
            projectVO = objectsIntoVo(obj);
        }
        return projectVO;
    }

    @Override
    public ArticleVO getArticleByArticleId(long articleId) {
        String sql = "select a.id,a.title,a.create_date,a.url,a.author,u.name from article a" +
                " left join user u on u.id=a.author where a.id=?";
        //转换数据对象类型
        List<Object[]> objects = projectDao.findListBySql(sql, articleId);
        List<ArticleVO> articleVOs = new ArrayList<>();
        for (Object[] obj:objects){
            ArticleVO dataVO = objectsIntoArticleVo(obj);
            articleVOs.add(dataVO);
        }

        if (articleVOs != null && articleVOs.size() > 0)
            return articleVOs.get(0);
        return null;
    }

    @Override
    public List<ProjectVO> listAllProject(long leadId) {
        String sql = "select id,project_name,status,leader from project where leader_id=? order by id desc";
        List<Object[]> objects = projectDao.findListBySql(sql, leadId);
        List<ProjectVO> dataVOs = new ArrayList<>();
        for (Object[] obj:objects) {
            ProjectVO dataVO = objectsIntoVo(obj);//注意obj的顺序
            dataVOs.add(dataVO);
        }
        if (dataVOs.size() > 0){
            return dataVOs;
        }
        return null;
    }

    @Override
    public Page listProjectByAnyParam(String pageNo, String pageSize, String... params) {
        StringBuilder sql = new StringBuilder("select id,project_name,status,leader from project where 1=1");
        if (params != null){
            if(params.length>0 && !ObjectUtil.isNullOrEmpty(params[0])){
                sql.append(" and leader_id="+params[0]);
            }
            if(params.length>1 && !ObjectUtil.isNullOrEmpty(params[1])){
                sql.append(" and project_name like '%"+params[1]+"%'");
            }
            if(params.length>2 && !ObjectUtil.isNullOrEmpty(params[2])){
                sql.append(" and status="+params[2]);
            }
            if(params.length>3 && !ObjectUtil.isNullOrEmpty(params[3])){
                sql.append(" and leader like '%"+params[3]+"%'");
            }
        }
        sql.append(" order by id desc");
        //计算总数前缀
        String countSqlPre = "select count(*) ";
        //获取分页
        Page webPage = projectDao.findPage(sql.toString(), countSqlPre, pageNo, pageSize);
        //转换数据对象类型
        List<Object[]> objects = (List<Object[]>) webPage.getRows();
        List<ProjectVO> dataDictionaryVOs = new ArrayList<>();
        for (Object[] obj:objects){
            ProjectVO dataVO = objectsIntoVo(obj);
            dataDictionaryVOs.add(dataVO);
        }
        webPage.setRows(dataDictionaryVOs);
        return webPage;
    }

    @Override
    public Page listProjectByUserId(long userId, String pageNo, String pageSize, String... params) {
        StringBuilder sql = new StringBuilder("select p.id,p.project_name,p.status,p.leader from project_user pu" +
                " left join project p on pu.project_id=p.id where pu.user_id=" + userId);
        if (params != null){
            if(params.length>0 && !ObjectUtil.isNullOrEmpty(params[0])){
                sql.append(" and p.leader_id="+params[0]);
            }
            if(params.length>1 && !ObjectUtil.isNullOrEmpty(params[1])){
                sql.append(" and p.project_name like '%"+params[1]+"%'");
            }
            if(params.length>2 && !ObjectUtil.isNullOrEmpty(params[2])){
                sql.append(" and p.status="+params[2]);
            }
            if(params.length>3 && !ObjectUtil.isNullOrEmpty(params[3])){
                sql.append(" and p.leader like '%"+params[3]+"%'");
            }
        }
        sql.append(" order by p.id desc");
        //计算总数前缀
        String countSqlPre = "select count(*) ";
        //获取分页
        Page webPage = projectDao.findPage(sql.toString(), countSqlPre, pageNo, pageSize);
        //转换数据对象类型
        List<Object[]> objects = (List<Object[]>) webPage.getRows();
        List<ProjectVO> dataDictionaryVOs = new ArrayList<>();
        for (Object[] obj:objects){
            ProjectVO dataVO = objectsIntoVo(obj);
            dataDictionaryVOs.add(dataVO);
        }
        webPage.setRows(dataDictionaryVOs);
        return webPage;
    }

    @Override
    public List<ProjectLogVO> listProjectLogById(long id) {
        String sql = "select id,project_id,date,info from project_log where project_id=?";
        List<Object[]> objects = projectDao.findListBySql(sql,id);
        List<ProjectLogVO> dataVOs = new ArrayList<>();
        for (Object[] obj:objects) {
            ProjectLogVO dataVO = objectsIntoProjectLogVo(obj);//注意obj的顺序
            dataVOs.add(dataVO);
        }
        if (dataVOs.size() > 0){
            return dataVOs;
        }
        return null;
    }

    @Override
    public String saveProject(ProjectPO projectPO, String[] memberIds) throws Exception {
        //验证项目名称唯一
        String name = projectPO.getProjectName();
        if (StringUtils.isEmpty(name)){
            return "nameIsNull";
        }
        if (null != getProjectByName(name)){
            return "nameNotOnly";
        }

        //新增项目信息
        projectDao.saveOrUpdateObject(projectPO);

        //获得项目ID
        long projectId = projectPO.getId();

        //保存项目成员
        saveProjectMembers(memberIds,projectId);

        //新增项目日志记录
        ProjectLogPO projectLogPO = new ProjectLogPO();
        projectLogPO.setProjectId(projectId);
        projectLogPO.setDate(new Timestamp(System.currentTimeMillis()));

        //项目创建
        int status = Integer.parseInt(projectPO.getStatus().toString());
        if (status == ProjectConsts.PROJECT_STATUS_NOT_START){
            projectLogPO.setInfo("项目【" + name + "】由 " + CurrentUserUtil.getUserName() + " 人创建");
        }
        if (status == ProjectConsts.PROJECT_STATUS_ON_GOINGG){
            projectLogPO.setInfo("项目【" + name + "】由 " + CurrentUserUtil.getUserName() + " 人创建并启动");
        }
        //执行增加日志
        projectDao.saveOrUpdateObject(projectLogPO);
        return "ok";
    }

    @Override
    public String saveProjectNote(HttpServletRequest request, ArticlePO articlePO, String projectId, String content) throws Exception {
        //保存文章内容
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String folder = format.format(date); //文章存放目录
        String fileName = UUID.randomUUID().toString();//文件名
        String fileSeparator = File.separator; //本地系统文件分隔符
        String fileAllName = "ueditor" + fileSeparator + "article" + fileSeparator + folder + fileSeparator + fileName + ".html";

        File file = new File(request.getSession().getServletContext().getRealPath("/") + fileSeparator + fileAllName);
        if(!file.getParentFile().exists()){
            if (!file.getParentFile().mkdirs())
                return "not"; //文章创建失败
        }
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
        out.write(content);
        out.flush();
        out.close();

        //保存文章信息入库
        articlePO.setUrl(format(fileAllName));
        projectDao.saveOrUpdateObject(articlePO);
        long artileId = articlePO.getId();

        //保存文章and项目对应关系
        if (StringUtils.isEmpty(projectId))
            return "ok"; //项目id为空时，文章与项目没有对应关系
        String sql = "insert project_article(project_id,article_id) values(?,?)";
        boolean saveProAndArt = projectDao.executeBySql(sql, projectId, artileId);
        if (!saveProAndArt)
            return "not";

        return "ok";
    }

    @Override
    public String updateProject(ProjectPO projectPO, String logInfo, boolean updateMember, String[] memberIds) throws Exception {
        if(StringUtils.isEmpty(logInfo)){
            return "logInfoIsNull";
        }

        //获得项目ID
        long projectId = projectPO.getId();

        //项目成员修改
        if (updateMember){
            //删除原有的项目成员
            removeProjectMembers(projectId);

            //记录新的项目成员
            saveProjectMembers(memberIds,projectId);
        }

        //执行更改项目信息
        projectDao.saveOrUpdateObject(projectPO);

        //记录项目更改日志
        ProjectLogPO projectLogPO = new ProjectLogPO();
        projectLogPO.setDate(new Timestamp(System.currentTimeMillis()));
        projectLogPO.setProjectId(projectId);
        projectLogPO.setInfo(logInfo);
        projectDao.saveOrUpdateObject(projectLogPO);

        return "ok";
    }

    @Override
    public String updateProjectStatus(long projectId, String status, String info, boolean saveLog) throws Exception {
        String sql = "update project set status=? where id=?";
        boolean upResult = projectDao.executeBySql(sql,status,projectId);
        if (!upResult){
            return "error";
        }

        //记录日志
        if (saveLog){
            ProjectLogPO projectLogPO = new ProjectLogPO();
            projectLogPO.setDate(new Timestamp(System.currentTimeMillis()));
            projectLogPO.setProjectId(projectId);
            projectLogPO.setInfo(info);
            projectDao.saveOrUpdateObject(projectLogPO);
        }
        return "ok";
    }

    @Override
    public String updateProjectNote(HttpServletRequest request, String title, String articleId, String content) throws Exception {
        //获取文章路径
        ArticlePO articlePO = (ArticlePO) projectDao.getObjectByAnytypeId(ArticlePO.class, Long.parseLong(articleId));
        String url = articlePO.getUrl();

        //保存文章内容
        String fileSeparator = File.separator; //本地系统文件分隔符
        File file = new File(request.getSession().getServletContext().getRealPath("/") + fileSeparator + url);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
        out.write(content);
        out.flush();
        out.close();

        //保存文章信息入库
        String sql = "update article set title=?,update_date=? where id=?";
        boolean update = projectDao.executeBySql(sql, title, new Timestamp(System.currentTimeMillis()), articleId);
        if (!update)
            return "not";

        return "ok";
    }

    @Override
    public String removeProject(long projectId) throws Exception {
        //删除项目
        ProjectPO projectPO = new ProjectPO();
        projectPO.setId(projectId);
        projectDao.deleteObject(projectPO);

        //删除项目成员
        boolean removeMember = removeProjectMembers(projectId);
        if (!removeMember){
            return "error";
        }

        //删除项目日志
        boolean removeLog = removeProjectLog(projectId);
        if (!removeLog){
            return "error";
        }

        return "ok";
    }

    @Override
    public String removeNote(long articleId) throws Exception {
        //删除文章信息表
        ArticlePO articlePO = new ArticlePO();
        articlePO.setId(articleId);
        projectDao.deleteObject(articlePO);

        //删除文章对应关系表
        String sql = "delete from project_article where article_id=?";
        boolean removePA = projectDao.executeBySql(sql, articleId);
        if (!removePA)
            return "not";

        return "ok";
    }

    /**
     * 保存项目组成员
     * @param memberIds 项目成员数组IDs
     * @param id 项目ID
     * @return true 成功
     */
    private boolean saveProjectMembers(String[] memberIds,long id) throws Exception {
//        String sql = "insert into project_user(user_id,project_id) values(3,3),(4,3)";
        StringBuilder sql = new StringBuilder("insert into project_user(user_id,project_id) values");
        for (int i = 0; i < memberIds.length; i++) {
            sql.append("(" + memberIds[i] + "," + id + "),");
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(";");
        return projectDao.executeBySql(sql.toString());
    }

    /**
     * 删除项目组成员
     * @param id 项目ID
     */
    private boolean removeProjectMembers(long id) throws Exception {
        String sql = "delete from project_user where project_id=?";
        return projectDao.executeBySql(sql,id);
    }

    /**
     * 删除项目日志
     * @param id 项目id
     * @throws Exception
     */
    private boolean removeProjectLog(long id) throws Exception {
        String sql = "delete from project_log where project_id=?";
        return projectDao.executeBySql(sql, id);
    }

    /**
     * vo赋值
     * @param obj 值数组，必须按照方法指定的顺序,obj[0]不能为空表示ID
     * @return
     */
    private ProjectVO objectsIntoVo(Object... obj){
        ProjectVO dataVO = new ProjectVO();
        dataVO.setId(Long.valueOf(String.valueOf(obj[0])));
        if(obj.length>1 && !ObjectUtil.isNullOrEmpty(obj[1])){
            dataVO.setProjectName(obj[1].toString());
        }
        if(obj.length>2 && !ObjectUtil.isNullOrEmpty(obj[2])){
            dataVO.setStatus(Byte.parseByte(obj[2].toString()));
            dataVO.setStatusCn(ProjectConsts.meaningStatus(Integer.parseInt(obj[2].toString())));
        }
        if(obj.length>3 && !ObjectUtil.isNullOrEmpty(obj[3])){
            dataVO.setLeader(obj[3].toString());
        }

        return dataVO;
    }

    /**
     * ProjectLogVO赋值
     * @param obj 值数组，必须按照方法指定的顺序,obj[0]不能为空表示ID
     * @return
     */
    private ProjectLogVO objectsIntoProjectLogVo(Object... obj){
        ProjectLogVO dataVO = new ProjectLogVO();
        dataVO.setId(Long.valueOf(String.valueOf(obj[0])));
        if(obj.length>1 && !ObjectUtil.isNullOrEmpty(obj[1])){
            dataVO.setProjectId(Long.parseLong(obj[1].toString()));
        }
        if(obj.length>2 && !ObjectUtil.isNullOrEmpty(obj[2])){
            dataVO.setDate(Timestamp.valueOf(obj[2].toString()));
        }
        if(obj.length>3 && !ObjectUtil.isNullOrEmpty(obj[3])){
            dataVO.setInfo(obj[3].toString());
        }
        return dataVO;
    }

    /**
     * vo赋值
     * @param obj 值数组，必须按照方法指定的顺序,obj[0]不能为空表示ID
     * @return
     */
    private ArticleVO objectsIntoArticleVo(Object... obj){
        ArticleVO articleVO = new ArticleVO();
        articleVO.setId(Long.valueOf(String.valueOf(obj[0])));
        if(obj.length>1 && !ObjectUtil.isNullOrEmpty(obj[1])){
            articleVO.setTitle(obj[1].toString());
        }
        if(obj.length>2 && !ObjectUtil.isNullOrEmpty(obj[2])){
            articleVO.setCreateDate(Timestamp.valueOf(obj[2].toString()));
        }
        if(obj.length>3 && !ObjectUtil.isNullOrEmpty(obj[3])){
            articleVO.setUrl(obj[3].toString());
        }
        if(obj.length>4 && !ObjectUtil.isNullOrEmpty(obj[4])){
            articleVO.setAuthor(Long.parseLong(obj[4].toString()));
        }
        if(obj.length>5 && !ObjectUtil.isNullOrEmpty(obj[5])){
            articleVO.setAuthorCn(obj[5].toString());
        }
        if(obj.length>6 && !ObjectUtil.isNullOrEmpty(obj[6])){
            articleVO.setBeLongsTo(obj[6].toString());
        }

        return articleVO;
    }

    /**
     * 格式化路径, 把windows路径替换成标准路径
     * @param input 待格式化的路径
     * @return 格式化后的路径
     */
    private String format ( String input ) {

        return input.replace( "\\", "/" );

    }
}
