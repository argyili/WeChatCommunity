package com.hfut.community.demo.domain;

import lombok.Data;


/**
 */
@Data
public class CommentByMyself {
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
    public CommentByMyself() {
    }

    /**
     * @param title String
     * @param pid int
     * @param commentContent String
     * @param commentTime String
     * @param wxid String
     * @param cid int
     * @param nickname String
     */
    public CommentByMyself(
            int cid, int pid, String title, String commentContent,
            String commentTime, String wxid, String nickname) {
        this.cid = cid;
        this.pid = pid;
        this.title = title;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
        this.wxid = wxid;
        this.nickname = nickname;
    }
}
