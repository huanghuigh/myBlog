package com.huang.dao;

import com.huang.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 回复dao层
 * @author guangtou
 * @create 2020--02--12--11:42
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {
    /**
     * 通过博客id查询所有回复
     * @param id
     * @param sort
     * @return
     */
    List<Comment> findByBlogId(Long id, Sort sort);

    /**
     * 查询所有根节点回复
     * @param id
     * @param sort
     * @return
     */
    List<Comment> findByBlogIdAndParentCommentNull(Long id, Sort sort);
}
