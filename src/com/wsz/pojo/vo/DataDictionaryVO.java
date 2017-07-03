package com.wsz.pojo.vo;

/**
 * 数据字典 展示对象
 * @author wanshenzhen  2017/3/15.
 */
public class DataDictionaryVO {
    private Long id;
    private String family;
    private String member;
    private String familyValue;
    private String memberValue;
    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getFamilyValue() {
        return familyValue;
    }

    public void setFamilyValue(String familyValue) {
        this.familyValue = familyValue;
    }

    public String getMemberValue() {
        return memberValue;
    }

    public void setMemberValue(String memberValue) {
        this.memberValue = memberValue;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
