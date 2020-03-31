package com.huang.service;

import com.huang.pojo.Comment;

import java.util.List;

/**
 * @author guangtou
 * @create 2020--02--12--11:41
 */
public interface CommentService {
    /**
     * 通过id获取根节点回复
     * @param blogId
     * @return
     */
    List<Comment> listCommentByBlogId(Long blogId);

    /**
     * 保存回复
     * @param comment
     * @return
     */
    Comment saveComment(Comment comment);
}
