package com.javaee.bbs1.service;

import com.javaee.bbs1.dao.CommentMapper;
import com.javaee.bbs1.entity.Comment;
import com.javaee.bbs1.util.BbsConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;


@Service
public class CommentService implements BbsConstant {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private DiscussPostService discussPostService;

    public List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int limit){
        return commentMapper.selectCommentsByEntity(entityType,entityId,offset,limit);
    }

    public int findCommentCount(int entityType, int entityId){
        return commentMapper.selectCountByEntity(entityType, entityId);
    }

    //事务使用，READ_COMMITTED为忍受数据受影响的级别，如脏读，幻读等,Propagation为传播机制
    //REQUIRED支持当前事务（外部事务），如果不存在创建新事物。如果数REQUIREDS_NEW停掉当前事务，建新事物
    //NESTED:如果当前存在事务，则嵌套在该事务中执行，否则同REQUIRED
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addComment(Comment comment){
        //判空
        if (comment == null){
            throw new IllegalArgumentException("参数不能为空！");
        }

        //添加评论
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        int rows = commentMapper.insertComment(comment);

        //加帖子后，更新帖子评论数量
        if(comment.getEntityType() == ENTITY_TYPE_POST){
            int count = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(),count);
        }
        return rows;
    }
}
