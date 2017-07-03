package com.wsz.pojo.vo;

import java.sql.Timestamp;

/**
 * 项目日志展示对象
 * @author wanshenzhen  2017/3/18.
 */
public class ProjectLogVO {
    private Long id;
    private Long projectId;
    private Timestamp date;
    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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
        return "ProjectLogVO{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", date=" + date +
                ", info='" + info + '\'' +
                '}';
    }
}
