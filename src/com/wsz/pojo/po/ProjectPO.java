package com.wsz.pojo.po;

import javax.persistence.*;

/**
 * @author wanshenzhen  2017/3/18.
 */
@Entity
@Table(name = "project", schema = "")
public class ProjectPO {
    private Long id;
    private String projectName;
    private Byte status;
    private String leader;
    private Long leaderId;

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
    @Column(name = "project_name", nullable = true, insertable = true, updatable = true, length = 25)
    public String getProjectName() {
        return projectName;
    }

    public String setProjectName(String projectName) {
        this.projectName = projectName;
        return projectName;
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
    @Column(name = "leader", nullable = true, insertable = true, updatable = true, length = 6)
    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectPO projectPO = (ProjectPO) o;

        if (id != null ? !id.equals(projectPO.id) : projectPO.id != null) return false;
        if (projectName != null ? !projectName.equals(projectPO.projectName) : projectPO.projectName != null)
            return false;
        if (status != null ? !status.equals(projectPO.status) : projectPO.status != null) return false;
        if (leader != null ? !leader.equals(projectPO.leader) : projectPO.leader != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (leader != null ? leader.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProjectPO{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", status=" + status +
                ", leader='" + leader + '\'' +
                '}';
    }

    @Basic
    @Column(name = "leader_id", nullable = true, insertable = true, updatable = true)
    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }
}
