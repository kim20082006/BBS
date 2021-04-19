package com.javaee.bbs1.dao;

import com.javaee.bbs1.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: tianzihe
 * @date: 2020/12/4 - 13:23
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@Mapper
public interface CommentMapper {

    /**获得回复内容并加入分页功能
    */
    List<Comment> selectCommentsByEntity(int entityType,int entityId, int offset, int limit);

    int selectCountByEntity( int entityType, int entityId);

    int insertComment(Comment comment);


}
