package com.huang.pojo;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客
 * @author guangtou
 * @create 2020--02--06--17:25
 */
@Entity
@Table(name="t_blog")
public class Blog {
//    id    标识id，自增策略
    @Id
    @GeneratedValue
    private Long id;
//    标题
    private String title;
//      内容
    private String content;
//      首图
    private String firstPicture;
//        标记
    private String flag;
//        浏览次数
    private Integer views;
//          赞赏开启
    private boolean appreciation;
//          版权/转载开启
    private boolean shareStatement;
//          评论开启
    private boolean commentabled;
//          是否发布
    private boolean published;
//          推荐开启
    private boolean recommend;
//          创建时间，指定数据库为timestamp格式
@Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
//          更新时间
@Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @ManyToOne
    private Type type;
    @Transient
    private String tagIds;
    //    博客描述
    private String description;
    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    //    级联新增，当blog有新的tag时，tag表新增
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Blog() {
    }



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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    private String tagsToIds(List<Tag> tags){
        System.out.println("tags::"+tags);
        if(!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for(Tag tag : tags){
                if(flag) {
                    ids.append(",");
                } else{
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return null;
        }
    }
    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    };

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
