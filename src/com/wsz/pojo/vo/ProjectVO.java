package com.wsz.pojo.vo;

import java.util.List;

/**
 * 项目展示对象
 * @author wanshenzhen  2017/3/18.
 */
public class ProjectVO {
    private Long id;
    private String projectName;
    private Byte status;
    private String statusCn;//状态值 中文含义
    private String leader;

    private List<UserVO> projectUsers;//项目组成员

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getStatusCn() {
        return statusCn;
    }

    public void setStatusCn(String statusCn) {
        this.statusCn = statusCn;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public List<UserVO> getProjectUsers() {
        return projectUsers;
    }

    public void setProjectUsers(List<UserVO> projectUsers) {
        this.projectUsers = projectUsers;
    }

    @Override
    public String toString() {
        return "ProjectVO{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", status=" + status +
                ", statusCn='" + statusCn + '\'' +
                ", leader='" + leader + '\'' +
                ", projectUsers=" + projectUsers +
                '}';
    }
}
