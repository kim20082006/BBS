package com.javaee.bbs1.service;

import com.javaee.bbs1.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author: tianzihe
 * @date: 2020/12/11 - 21:06
 * @mail: maxtian123@hotmail.com
 * @info:
 */

@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    // 点赞
    public void like(int userId,int entityType,int entityId){
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);

        boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
        //判断entityLikeKey是否与userId联系起来了，在不在集合里，联系起来说明已经点赞需要取消，没联系起来需要点赞
        if (isMember){
            redisTemplate.opsForSet().remove(entityLikeKey,userId);
        }else{
            redisTemplate.opsForSet().add(entityLikeKey,userId);
        }
    }

    // 查询某实体点赞的数量
    public long findEntityLikeCount(int entityType, int entityId){
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    // 查询某人对实体的点赞状态
    // 返回整数有扩展性
    public int findEntityLikeStatus(int userId, int entityType, int entityId){
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey,userId) ? 1 : 0 ;

    }

}
