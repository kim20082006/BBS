package com.javaee.bbs1.service;

import com.javaee.bbs1.dao.MessageMapper;
import com.javaee.bbs1.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author: tianzihe
 * @date: 2020/12/5 - 18:48
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    public List<Message> findConversations(int userId, int offset, int limit){
        return messageMapper.selectConversations(userId,offset,limit);
    }

    public int findConversationCount(int userId){
        return messageMapper.selectConversationCount(userId);
    }

    public List<Message> findLetters(String conversationId, int offset, int limit){
        return messageMapper.selectLetters(conversationId,offset,limit);
    }

    public int findLetterCount(String conversationId){
        return messageMapper.selectLetterCount(conversationId);
    }

    public int findLetterUnreadCount(int userId,String conversation){
        return messageMapper.selectLetterUnreadCount(userId,conversation);
    }

    //发送列表
    public int addMessage(Message message){
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        return messageMapper.insertMessage(message);
    }

    //改已读状态
    public int readMessage(List<Integer> ids){
        return messageMapper.updateStatus(ids,1);
    }
}
