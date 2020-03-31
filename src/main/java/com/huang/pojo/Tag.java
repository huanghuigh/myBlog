package com.huang.pojo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 博客标签
 * @author guangtou
 * @create 2020--02--06--17:37
 */
@Entity
@Table(name ="t_tag")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message="标签名称不能为空")
    private String name;
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
