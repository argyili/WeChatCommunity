package com.hfut.community.demo.domain;

import lombok.Data;


/**
 */
@Data
public class CommentToMe {
    /**
     */
    private int cid;
    /**
     */
    private int pid;
    /**
     */
    private String title;
    /**
     */
    private String commentContent;
    /**
     */
    private String commentTime;
    /**
     */
    private String wxid;
    /**
     */
    private String nickname;
    /**
     */
    private int isView;
    /**
     */
    public CommentToMe() {
    }

    /**
     * @param title String
     * @param pid int
     * @param commentContent String
     * @param commentTime Timestamp
     * @param wxid String
     * @param cid int
     * @param nickname String
     * @param isView int
     */
    public CommentToMe(
            int cid, int pid, String title,
            String commentContent, String commentTime,
            String wxid, String nickname, int isView) {
        this.cid = cid;
        this.pid = pid;
        this.title = title;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
        this.wxid = wxid;
        this.nickname = nickname;
        this.isView = isView;
    }
}
