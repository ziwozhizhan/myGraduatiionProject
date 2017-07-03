package com.wsz.pojo.vo;

import java.sql.Timestamp;

/**
 * 文章信息 展示对象
 * @author wanshenzhen  2017/5/13.
 */
public class ArticleVO {
    private Long id;
    private String title;
    private Long author;
    private String authorCn; //用户中文名
    private Timestamp createDate;
    private Timestamp updateDate;
    private String url;

    private String beLongsTo; //文章所属分类，一般为项目名

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public String getAuthorCn() {
        return authorCn;
    }

    public void setAuthorCn(String authorCn) {
        this.authorCn = authorCn;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBeLongsTo() {
        return beLongsTo;
    }

    public void setBeLongsTo(String beLongsTo) {
        this.beLongsTo = beLongsTo;
    }

    @Override
    public String toString() {
        return "ArticleVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", authorCn='" + authorCn + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", url='" + url + '\'' +
                ", beLongsTo='" + beLongsTo + '\'' +
                '}';
    }
}
