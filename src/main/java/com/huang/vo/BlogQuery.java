package com.huang.vo;

/**
 * @author guangtou
 * @create 2020--02--09--12:14
 */
public class BlogQuery {
    private String Title;
    private Long TypeId;
    private boolean recommend;

    public BlogQuery() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Long getTypeId() {
        return TypeId;
    }

    public void setTypeId(Long typeId) {
        TypeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "BlogQuery{" +
                "Title='" + Title + '\'' +
                ", TypeId=" + TypeId +
                ", recommend=" + recommend +
                '}';
    }
}
