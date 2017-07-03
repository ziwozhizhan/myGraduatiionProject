package com.wsz.pojo.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author wanshenzhen  2017/3/20.
 */
@Entity
@Table(name = "project_task", schema = "", catalog = "gowest")
public class ProjectTaskPO {
    private Long id;
    private String taskName;
    private String creater;
    private String completer;
    private Byte status;
    private Timestamp expectDate;
    private Timestamp completeDate;
    private Long projectId;
    private Byte urgency;
    private Long userId;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "task_name", nullable = true, insertable = true, updatable = true, length = 225)
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Basic
    @Column(name = "creater", nullable = true, insertable = true, updatable = true, length = 6)
    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Basic
    @Column(name = "completer", nullable = true, insertable = true, updatable = true, length = 6)
    public String getCompleter() {
        return completer;
    }

    public void setCompleter(String completer) {
        this.completer = completer;
    }

    @Basic
    @Column(name = "status", nullable = true, insertable = true, updatable = true)
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "expect_date", nullable = true, insertable = true, updatable = true)
    public Timestamp getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Timestamp expectDate) {
        this.expectDate = expectDate;
    }

    @Basic
    @Column(name = "complete_date", nullable = true, insertable = true, updatable = true)
    public Timestamp getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Timestamp completeDate) {
        this.completeDate = completeDate;
    }

    @Basic
    @Column(name = "project_id", nullable = false, insertable = true, updatable = true)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "urgency", nullable = true, insertable = true, updatable = true)
    public Byte getUrgency() {
        return urgency;
    }

    public void setUrgency(Byte urgency) {
        this.urgency = urgency;
    }

    @Basic
    @Column(name = "user_id", nullable = true, insertable = true, updatable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectTaskPO that = (ProjectTaskPO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (taskName != null ? !taskName.equals(that.taskName) : that.taskName != null) return false;
        if (creater != null ? !creater.equals(that.creater) : that.creater != null) return false;
        if (completer != null ? !completer.equals(that.completer) : that.completer != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (expectDate != null ? !expectDate.equals(that.expectDate) : that.expectDate != null) return false;
        if (completeDate != null ? !completeDate.equals(that.completeDate) : that.completeDate != null) return false;
        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;
        if (urgency != null ? !urgency.equals(that.urgency) : that.urgency != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (creater != null ? creater.hashCode() : 0);
        result = 31 * result + (completer != null ? completer.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (expectDate != null ? expectDate.hashCode() : 0);
        result = 31 * result + (completeDate != null ? completeDate.hashCode() : 0);
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (urgency != null ? urgency.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
