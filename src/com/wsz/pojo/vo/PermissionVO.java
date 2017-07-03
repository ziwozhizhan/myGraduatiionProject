package com.wsz.pojo.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限关联 展示对象
 * @author wanshenzhen  2017/3/17.
 */
public class PermissionVO {
    private Long id;
    private String nameCn;
    private String nameEn;
    private Long pid;
    private Boolean checked; //是否是当前角色的权限
    private List<PermissionVO> children = new ArrayList<>();

    public PermissionVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<PermissionVO> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionVO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "PermissionVO{" +
                "id=" + id +
                ", nameCn='" + nameCn + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", pid=" + pid +
                ", checked=" + checked +
                ", children=" + children +
                '}';
    }
}
