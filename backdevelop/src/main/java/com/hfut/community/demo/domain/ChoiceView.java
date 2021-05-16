package com.hfut.community.demo.domain;

import lombok.Data;

/**
 */
@Data
public class ChoiceView {
    /**
     * 点赞/踩的id
     */
    private int chid;
    /**
     * 点赞/踩的时间
     */
    private String choiceTime;
    /**
     * 被点赞/踩的帖子id
     */
    private int pid;
    /**
     * 被点赞/踩的帖子的发帖人的微信号
     */
    private String toWxid;
    /**
     * 谁进行点赞/踩
     */
    private String byWxid;
    /**
     * 被点赞/踩的帖子的标题
     */
    private String title;
    /**
     * 点赞/踩的帖子的类型
     */
    private int type;
    /**
     */
    private int isView;
    /**
     */
    public ChoiceView() {
    }
    /**
     * @param title String
     * @param pid String
     * @param byWxid String
     * @param chid String
     * @param choiceTime TimeStamp
     * @param toWxid String
     * @param type int
     */
    public ChoiceView(
            int chid, String choiceTime, int pid, String toWxid,
            String byWxid, String title, int type, int isView) {
        this.chid = chid;
        this.choiceTime = choiceTime;
        this.pid = pid;
        this.toWxid = toWxid;
        this.byWxid = byWxid;
        this.title = title;
        this.type = type;
        this.isView = isView;
    }
}
