package com.hfut.community.demo.domain;

import lombok.Data;


/**
 */
@Data
public class Choice {
    /**
     *点赞、踩id
     */
    private int chid;
    /**
     *点赞、踩者微信号id
     */
    private String wxid;
    /**
     *点赞、踩时间
     */
    private String choiceTime;
    /**
     *点赞为1点踩为0
     */
    private int type;
    /**
     *被点赞或点踩的帖子id
     */
    private int pid;
    /**
     *是否被查看，默认为0未查看
     */
    private int isView;
    /**
     * @param type int
     * @param choiceTime String
     * @param chid int
     * @param pid int
     * @param wxid String
     * @param isView int
     */
    public Choice(
            int chid, String wxid, String choiceTime,
            int type, int pid, int isView) {
        this.chid = chid;
        this.wxid = wxid;
        this.choiceTime = choiceTime;
        this.type = type;
        this.pid = pid;
        this.isView = isView;
    }
    /**
     */
    public Choice() { };
}
