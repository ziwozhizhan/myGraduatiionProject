package com.wsz.pojo.po;

import javax.persistence.*;

/**
 * @author wanshenzhen  2017/3/14.
 */
@Entity
@Table(name = "data_dictionary")
public class DataDictionaryPO {
    private Long id;
    private String family;
    private String member;
    private String familyValue;
    private String memberValue;
    private Integer sort;

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
    @Column(name = "family", nullable = true, insertable = true, updatable = true, length = 10)
    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    @Basic
    @Column(name = "member", nullable = true, insertable = true, updatable = true, length = 20)
    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    @Basic
    @Column(name = "family_value", nullable = true, insertable = true, updatable = true, length = 20)
    public String getFamilyValue() {
        return familyValue;
    }

    public void setFamilyValue(String familyValue) {
        this.familyValue = familyValue;
    }

    @Basic
    @Column(name = "member_value", nullable = true, insertable = true, updatable = true, length = 20)
    public String getMemberValue() {
        return memberValue;
    }

    public void setMemberValue(String memberValue) {
        this.memberValue = memberValue;
    }

    @Basic
    @Column(name = "sort", nullable = true, insertable = true, updatable = true)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataDictionaryPO that = (DataDictionaryPO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (family != null ? !family.equals(that.family) : that.family != null) return false;
        if (member != null ? !member.equals(that.member) : that.member != null) return false;
        if (familyValue != null ? !familyValue.equals(that.familyValue) : that.familyValue != null) return false;
        if (memberValue != null ? !memberValue.equals(that.memberValue) : that.memberValue != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (family != null ? family.hashCode() : 0);
        result = 31 * result + (member != null ? member.hashCode() : 0);
        result = 31 * result + (familyValue != null ? familyValue.hashCode() : 0);
        result = 31 * result + (memberValue != null ? memberValue.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }
}
