package com.javaee.bbs1.controller;

import com.javaee.bbs1.entity.User;
import com.javaee.bbs1.service.LikeService;
import com.javaee.bbs1.util.Bbs1Util;
import com.javaee.bbs1.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: tianzihe
 * @date: 2020/12/12 - 15:34
 * @mail: maxtian123@hotmail.com
 * @info:
 */

@Controller
public class LikeController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId) {
        User user = hostHolder.getUser();
        //点赞
        likeService.like(user.getId(),entityType,entityId);
        //数量
        long likeCount = likeService.findEntityLikeCount(entityType,entityId);
        //状态
        int likeStatus = likeService.findEntityLikeStatus(user.getId(),entityType,entityId);
        //返回的结果
        Map<String,Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);

        return Bbs1Util.getJSONString(0,null,map);
    }
}
