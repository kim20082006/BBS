package com.javaee.bbs1.dao;

import com.javaee.bbs1.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: tianzihe
 * @date: 2020/12/5 - 17:41
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@Mapper
public interface MessageMapper {

    // 查询当前用户的会话列表，针对每个会话，只返回一条最新的私信,会话为与某个用户之间的私信
    List<Message> selectConversations(int userId, int offset, int limit);

    // 查询当前用户的会话数量
    int selectConversationCount(int userId);

    // 查询某个会话所包含的私信列表,与某个用户的私信列表
    List<Message> selectLetters(String conversationId, int offset, int limit);

    // 查询某个会话所包含的私信数量
    int selectLetterCount(String conversationId);

    // 查询未读私信的数量
    int selectLetterUnreadCount(int userId, String conversationId);

    // 新增消息
    int insertMessage(Message message);

    // 改消息状态,多个id
    int updateStatus(List<Integer> ids, int status);
}
