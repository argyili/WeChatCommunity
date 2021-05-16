package com.hfut.community.demo.domain;

import lombok.Data;

/**
 */
@Data
public class Comment {
    /**
     */
    private int cid;
    /**
     */
    private String wxid;
    /**
     */
    private int pid;
    /**
     */
    private String commentTime;
    /**
     */
    private String commentContent;
    /**
     */
    private int isView;
    public Comment() {
    }
    /**
     * @param wxid String
     * @param pid int
     * @param commentContent String
     * @param commentTime String
     */
    public Comment(
            int cid, String wxid, int pid, String commentTime,
            String commentContent, int isView) {
        this.cid = cid;
        this.wxid = wxid;
        this.pid = pid;
        this.commentTime = commentTime;
        this.commentContent = commentContent;
        this.isView = isView;
    }
}
