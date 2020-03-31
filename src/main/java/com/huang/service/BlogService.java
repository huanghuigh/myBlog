package com.huang.service;

import com.huang.pojo.Blog;
import com.huang.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author guangtou
 * @create 2020--02--09--9:24
 */
public interface BlogService {
    /**
     * 通过id查询博客
     * @param id
     * @return
     */
    public Blog getBlog(Long id);
    /**
     * 通过id查询博客，并将markdown格式内容转为html格式
     * @param id
     * @return
     */
    public Blog getAndConvert(Long id);

    /**
     * 通过id删除博客
     * @param id
     */
    public void deleteBlog(Long id);

    /**
     * 保存/修改博客
     * @param blog
     * @return
     */
    public Blog saveBlog(Blog blog);

    /**
     * 指定id更新博客
     * @param id
     * @param blog
     * @return
     */
    public Blog updateBlog(Long id,Blog blog);

    /**
     * 按照条件查询博客
     * @param pageable
     * @param blog 标题、分类、推荐
     * @return
     */
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    /**
     * 按照条件查询所有博客
     * @param pageable
     * @return
     */
    public Page<Blog> BlogAll(Pageable pageable);

    /**
     * 按照条件查询所有博客
     * @param pageable
     * @return
     */
    public Page<Blog> listBlog(Pageable pageable);

    /**
     * 查询标题或内容是否包含指定字符串
     * @param query
     * @param pageable
     * @return
     */
    public Page<Blog> listBlog(String query,Pageable pageable);

    /**
     * 查询所有推荐的博客
     * @param size
     * @return
     */
    List<Blog> listRecommendBlogTop(Integer size);

    /**
     * 查询包含标签的博客
     * @param tagId
     * @param pageable
     * @return
     */
    Page<Blog> listBlog(Long tagId,Pageable pageable);

    /**
     * 获取以年份分隔的所有Blog
     * @return
     */
    Map<String,List<Blog>> archiveBlog();

    /**
     * 获取博客总数量
     * @return
     */
    Long countBlog();
}
