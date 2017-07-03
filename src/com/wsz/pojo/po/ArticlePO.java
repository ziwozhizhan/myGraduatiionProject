package com.wsz.pojo.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author wanshenzhen  2017/5/13.
 */
@Entity
@Table(name = "article", schema = "", catalog = "gowest")
public class ArticlePO {
    private Long id;
    private String title;
    private Long author;
    private Timestamp createDate;
    private Timestamp updateDate;
    private String url;

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
    @Column(name = "title", nullable = true, insertable = true, updatable = true, length = 45)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "author", nullable = true, insertable = true, updatable = true)
    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    @Basic
    @Column(name = "create_date", nullable = true, insertable = true, updatable = true)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "update_date", nullable = true, insertable = true, updatable = true)
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Basic
    @Column(name = "url", nullable = true, insertable = true, updatable = true, length = 150)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticlePO articlePO = (ArticlePO) o;

        if (id != null ? !id.equals(articlePO.id) : articlePO.id != null) return false;
        if (title != null ? !title.equals(articlePO.title) : articlePO.title != null) return false;
        if (author != null ? !author.equals(articlePO.author) : articlePO.author != null) return false;
        if (createDate != null ? !createDate.equals(articlePO.createDate) : articlePO.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(articlePO.updateDate) : articlePO.updateDate != null) return false;
        if (url != null ? !url.equals(articlePO.url) : articlePO.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
