package com.wsz.pojo.vo;

import java.util.List;

/**
 * 用户 展示对象
 * @author wanshenzhen  2017/3/17.
 */
public class UserVO {
    private Long id;
    private String uname;
    private String password;
    private String name;
    private Byte sex;//0-女，1-男
    private Long department;
    private Long position;
    private String contactWay; //联系方式
    private String departmentCn;//部门中文
    private String positionCn;//职位中文

    private List<RoleVO> roles;//角色

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(Long department) {
        this.department = department;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public List<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVO> roles) {
        this.roles = roles;
    }

    public String getDepartmentCn() {
        return departmentCn;
    }

    public void setDepartmentCn(String departmentCn) {
        this.departmentCn = departmentCn;
    }

    public String getPositionCn() {
        return positionCn;
    }

    public void setPositionCn(String positionCn) {
        this.positionCn = positionCn;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", uname='" + uname + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", department=" + department +
                ", position=" + position +
                ", contactWay='" + contactWay + '\'' +
                ", departmentCn='" + departmentCn + '\'' +
                ", positionCn='" + positionCn + '\'' +
                ", roles=" + roles +
                '}';
    }
}
