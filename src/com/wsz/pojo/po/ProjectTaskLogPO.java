package com.wsz.pojo.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author wanshenzhen  2017/3/18.
 */
@Entity
@Table(name = "project_task_log")
public class ProjectTaskLogPO {
    private Long id;
    private Long projectTaskId;
    private Timestamp date;
    private String info;

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
    @Column(name = "project_task_id", nullable = false, insertable = true, updatable = true)
    public Long getProjectTaskId() {
        return projectTaskId;
    }

    public void setProjectTaskId(Long projectTaskId) {
        this.projectTaskId = projectTaskId;
    }

    @Basic
    @Column(name = "date", nullable = true, insertable = true, updatable = true)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "info", nullable = true, insertable = true, updatable = true, length = 225)
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectTaskLogPO that = (ProjectTaskLogPO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (projectTaskId != null ? !projectTaskId.equals(that.projectTaskId) : that.projectTaskId != null)
            return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (info != null ? !info.equals(that.info) : that.info != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (projectTaskId != null ? projectTaskId.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }
}
