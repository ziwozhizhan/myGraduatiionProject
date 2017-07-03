package com.wsz.pojo.vo;

/**
 * 角色 展示对象
 * @author wanshenzhen  2017/3/17.
 */
public class RoleVO {
    private Long id;
    private String roleName;
    private String describe;
    private boolean userRole;//是否是特定某个用户的角色，true-是

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public boolean isUserRole() {
        return userRole;
    }

    public void setUserRole(boolean userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "RoleVO{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", describe='" + describe + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
