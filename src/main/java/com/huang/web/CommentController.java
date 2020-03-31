package com.huang.web;

import com.huang.pojo.Comment;
import com.huang.pojo.User;
import com.huang.service.BlogService;
import com.huang.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * 留言Controller
 * @author guangtou
 * @create 2020--02--12--11:29
 */
@Controller
public class CommentController {
    @Value("${comment.avatar}")
    private String avatar;

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    /**
     * ajax留言区域
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping("/comments/{blogId}")
    public  String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    /**
     * 留言提交
     * @param comment
     * @return
     */
    @PostMapping("/comments")
    public String post(Comment comment,HttpSession session){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
