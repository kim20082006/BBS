package com.javaee.bbs1.controller;

import com.javaee.bbs1.entity.Comment;
import com.javaee.bbs1.entity.DiscussPost;
import com.javaee.bbs1.entity.Page;
import com.javaee.bbs1.entity.User;
import com.javaee.bbs1.service.CommentService;
import com.javaee.bbs1.service.DiscussPostService;
import com.javaee.bbs1.service.LikeService;
import com.javaee.bbs1.service.UserService;
import com.javaee.bbs1.util.Bbs1Util;
import com.javaee.bbs1.util.BbsConstant;
import com.javaee.bbs1.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author: tianzihe
 * @date: 2020/11/3 - 19:50
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements BbsConstant {

    @Autowired
    private UserService userService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title,String content){
        User user = hostHolder.getUser();
        if(user == null){
            return Bbs1Util.getJSONString(403,"您还未登录!");

        }
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);
        return Bbs1Util.getJSONString(0,"发送成功!");
    }




    @RequestMapping(path = "/detail/{discussPostId}",method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId, Model model, Page page){
        //帖子
        DiscussPost post = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post",post);
        //作者
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user",user);
        //点赞数量
        long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST,discussPostId);
        model.addAttribute("likeCount",likeCount);
        //点赞状态,如果未登陆则无法点赞
        int likeStatus = hostHolder.getUser() == null ? 0 :
                likeService.findEntityLikeStatus(hostHolder.getUser().getId(),ENTITY_TYPE_POST,discussPostId);
        model.addAttribute("likeStatus",likeStatus);
        //评论分页信息
        page.setLimit(5);
        page.setPath("/discuss/detail/" + discussPostId);
        page.setRows(post.getCommentCount());

        //对于帖子评论

        List<Comment> commentList= commentService.findCommentsByEntity(
                ENTITY_TYPE_POST,post.getId(),page.getOffset(),page.getLimit());
        //commentVolist是对于帖子的评论的列表
        List<Map<String,Object>> commentVolist = new ArrayList<>();
        //通过map对于commentList遍历，map是向commentVo中添加评论和对应作者
        if (commentList != null){
            for (Comment comment : commentList){
                Map<String,Object> commentVo = new HashMap<>();
                //评论
                commentVo.put("comment",comment);
                //作者
                commentVo.put("user",userService.findUserById(comment.getUserId()));
                //点赞数量
                likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST,comment.getId());
                commentVo.put("likeCount",likeCount);
                //点赞状态,如果未登陆则无法点赞
                likeStatus = hostHolder.getUser() == null ? 0 :
                        likeService.findEntityLikeStatus(hostHolder.getUser().getId(),ENTITY_TYPE_POST,comment.getId());
                commentVo.put("likeStatus",likeStatus);
                //回复的列表书写，即对于帖子评论的评论
                List<Comment> replyList = commentService.findCommentsByEntity(
                        ENTITY_TYPE_COMMIT,comment.getId(),0,Integer.MAX_VALUE);
                //replyVolist是对于帖子评论的评论的列表
                List<Map<String,Object>> replyVoList = new ArrayList<>();
                if(replyList != null){
                    for(Comment reply : replyList){
                        Map<String, Object> replyVo = new HashMap<>();
                        //回复
                        replyVo.put("reply",reply);
                        //作者
                        replyVo.put("user",userService.findUserById(reply.getUserId()));

                        //回复的目标
                        User target = reply.getTargetId() == 0 ? null : userService.findUserById(reply.getTargetId());
                        replyVo.put("target", target);

                        //回复点赞数量
                        likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST,reply.getId());
                        replyVo.put("likeCount",likeCount);
                        //点赞状态,如果未登陆则无法点赞
                        likeStatus = hostHolder.getUser() == null ? 0 :
                                likeService.findEntityLikeStatus(hostHolder.getUser().getId(),ENTITY_TYPE_POST,reply.getId());
                        replyVo.put("likeStatus",likeStatus);

                        replyVoList.add(replyVo);
                    }
                }
                //把reply放到commentVo中,commentVo包括评论，回复列表内容,回复数量
                commentVo.put("replys",replyVoList);
                //回复数量
                int replyCount = commentService.findCommentCount(ENTITY_TYPE_COMMIT,comment.getId());
                commentVo.put("replyCount", replyCount);
                //Vo的内容加到commentVoList里
                commentVolist.add(commentVo);
            }
        }

        //将得到的整个结果放到model中,以便与界面结合使用
        model.addAttribute("comments",commentVolist);

        return "/site/discuss-detail";
    }

}
