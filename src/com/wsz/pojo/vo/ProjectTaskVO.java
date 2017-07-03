package com.wsz.pojo.vo;

import java.sql.Timestamp;

/**
 * 项目任务展示对象
 * @author wanshenzhen  2017/3/20.
 */
public class ProjectTaskVO {
    private Long id;
    private String taskName;
    private String creater;
    private String completer;
    private Byte status;
    private String statusCn; //状态值中文意义
    private Timestamp expectDate;
    private Timestamp completeDate;
    private Long projectId;
    private Byte urgency;
    private Long userId; //任务完成人id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCompleter() {
        return completer;
    }

    public void setCompleter(String completer) {
        this.completer = completer;
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

    public Timestamp getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Timestamp expectDate) {
        this.expectDate = expectDate;
    }

    public Timestamp getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Timestamp completeDate) {
        this.completeDate = completeDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Byte getUrgency() {
        return urgency;
    }

    public void setUrgency(Byte urgency) {
        this.urgency = urgency;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ProjectTaskVO{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", creater='" + creater + '\'' +
                ", completer='" + completer + '\'' +
                ", status=" + status +
                ", statusCn='" + statusCn + '\'' +
                ", expectDate=" + expectDate +
                ", completeDate=" + completeDate +
                ", projectId=" + projectId +
                ", urgency=" + urgency +
                ", userId=" + userId +
                '}';
    }
}
