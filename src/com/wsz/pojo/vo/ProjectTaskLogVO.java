package com.wsz.pojo.vo;

import java.sql.Timestamp;

/**
 * 项目任务日志展示对象
 * @author wanshenzhen  2017/3/20.
 */
public class ProjectTaskLogVO {
    private Long id;
    private Long projectTaskId;
    private Timestamp date;
    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectTaskId() {
        return projectTaskId;
    }

    public void setProjectTaskId(Long projectTaskId) {
        this.projectTaskId = projectTaskId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ProjectTaskLogVO{" +
                "id=" + id +
                ", projectTaskId=" + projectTaskId +
                ", date=" + date +
                ", info='" + info + '\'' +
                '}';
    }
}
