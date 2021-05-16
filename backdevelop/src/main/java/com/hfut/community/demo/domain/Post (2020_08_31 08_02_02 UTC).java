package com.hfut.community.demo.domain;

import lombok.Data;

/**
 */
@Data
public class Post {
    /**
     */
    private String pid;
    /**
     */
    private String wxid;
    /**
     */
    private String title;
    /**
     */
    private String content;
    /**
     */
    private String postingTime;
    /**
     */
    private String block;
    /**
     */
    private int praiseNum;
    /**
     */
    private int criticismNum;
    /**
     */
    private int eyesNum;
    /**
     */
    private int commentsNum;
    /**
     */
    private int fold;
    /**
     */
    private int refinement;
    /**
     */
    private int forbidComment;
    /**
     */
    private String checkedId;
    public Post() {
    }
    public Post(String pid, String wxid, String title,
                String content, String postingTime,
                String block, int praiseNum,
                int criticismNum, int eyesNum,
                int commentsNum, int fold,
                int refinement, int forbidComment) {
        this.pid = pid;
        this.wxid = wxid;
        this.title = title;
        this.content = content;
        this.postingTime = postingTime;
        this.block = block;
        this.praiseNum = praiseNum;
        this.criticismNum = criticismNum;
        this.eyesNum = eyesNum;
        this.commentsNum = commentsNum;
        this.fold = fold;
        this.refinement = refinement;
        this.forbidComment = forbidComment;

    }
}
