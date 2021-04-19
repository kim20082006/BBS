package com.javaee.bbs1.dao;

import com.javaee.bbs1.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: tianzihe
 * @date: 2020/9/30 - 9:45
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(int userId,int offset, int limit);

    int selectDiscussPostRows(@Param("userId")int userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

    //评论数量
    int updateCommentCount(int id, int commentCount);
}
