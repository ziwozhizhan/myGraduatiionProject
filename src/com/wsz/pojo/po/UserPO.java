package com.wsz.pojo.po;

import javax.persistence.*;

/**
 * @author wanshenzhen  2017/3/17.
 */
@Entity
@Table(name = "user")
public class UserPO {
    private Long id;
    private String uname;
    private String password;
    private String name;
    private Byte sex;
    private Long department;
    private Long position;
    private String contactWay;

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
    @Column(name = "uname", nullable = true, insertable = true, updatable = true, length = 20)
    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Basic
    @Column(name = "password", nullable = true, insertable = true, updatable = true, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 6)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "sex", nullable = true, insertable = true, updatable = true)
    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "department", nullable = true, insertable = true, updatable = true)
    public Long getDepartment() {
        return department;
    }

    public void setDepartment(Long department) {
        this.department = department;
    }

    @Basic
    @Column(name = "position", nullable = true, insertable = true, updatable = true)
    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    @Basic
    @Column(name = "contact_way", nullable = true, insertable = true, updatable = true, length = 60)
    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPO userPO = (UserPO) o;

        if (id != null ? !id.equals(userPO.id) : userPO.id != null) return false;
        if (uname != null ? !uname.equals(userPO.uname) : userPO.uname != null) return false;
        if (password != null ? !password.equals(userPO.password) : userPO.password != null) return false;
        if (name != null ? !name.equals(userPO.name) : userPO.name != null) return false;
        if (sex != null ? !sex.equals(userPO.sex) : userPO.sex != null) return false;
        if (department != null ? !department.equals(userPO.department) : userPO.department != null) return false;
        if (position != null ? !position.equals(userPO.position) : userPO.position != null) return false;
        if (contactWay != null ? !contactWay.equals(userPO.contactWay) : userPO.contactWay != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uname != null ? uname.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (contactWay != null ? contactWay.hashCode() : 0);
        return result;
    }
}
