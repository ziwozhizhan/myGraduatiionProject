package com.wsz.pojo.po;

import javax.persistence.*;

/**
 * @author wanshenzhen  2017/3/17.
 */
@Entity
@Table(name = "role")
public class RolePO {
    private Long id;
    private String roleName;
    private String describe;

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
    @Column(name = "role_name", nullable = true, insertable = true, updatable = true, length = 20)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "role_describe", nullable = true, insertable = true, updatable = true, length = 60)
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolePO rolePO = (RolePO) o;

        if (id != null ? !id.equals(rolePO.id) : rolePO.id != null) return false;
        if (roleName != null ? !roleName.equals(rolePO.roleName) : rolePO.roleName != null) return false;
        if (describe != null ? !describe.equals(rolePO.describe) : rolePO.describe != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (describe != null ? describe.hashCode() : 0);
        return result;
    }
}
