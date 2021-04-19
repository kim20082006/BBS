package com.javaee.bbs1.util;



/**
 * @author: tianzihe
 * @date: 2020/12/11 - 20:52
 * @mail: maxtian123@hotmail.com
 * @info:
 */
public class RedisKeyUtil {

    private static final String SPLIT = ":";

    private static final String PREFIX_ENTITY_LIKE ="like:entity";

    // 某个实体的赞
    // like:entity:entityType:entityId -> set(userId)
    // 谁给实体点赞，则把userId存到集合里
    public static String getEntityLikeKey(int entityType, int entityId){
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }


}
