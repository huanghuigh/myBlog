package com.huang.service;

import com.huang.dao.CommentRepository;
import com.huang.pojo.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author guangtou
 * @create 2020--02--12--11:47
 */
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
//        以createTime降序
        Sort sort = Sort.by(Sort.Direction.ASC,"createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComment(comments);
    }
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
//        如果是回复，设置回复父id
        if (parentCommentId != -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 遍历回复-父节点，将每个父节点的多级子节点都放入第一级中
     * @param comments
     * @return 返回一级节点集合
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment :comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
//        合并评论的各层字级到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }


    /**
     * 将集合中每个回复的所以回复添加到一级回复中
     * @param comments root根结点，blog不为空的对象集合
     */
    private void combineChildren(List<Comment> comments){
        for (Comment comment : comments){
            List<Comment> replys1 = comment.getReplyComments();
            for (Comment reply1 : replys1){
//                循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
//            修改顶级结点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
//            清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }
//    存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 添加该节点下所有的子节点到tempReplys
     * @param comment
     */
    private void recursively(Comment comment){
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if(comment.getReplyComments().size()>0){
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply :replys){
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }
}
