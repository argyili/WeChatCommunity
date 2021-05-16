package com.hfut.community.demo.domain;

import lombok.Data;

/**
 * Teacher视图
 */
@Data
public class TeacherView {
    /**
     */
    private String wxid;
    /**
     */
    private String nickname;
    /**
     */
    private int role;
    /**
     */
    private String region;
    /**
     */
    private String phoneNum;
    /**
     */
    private String forbidden;
    /**
     */
    private int identification;
    /**
     */
    private String img;
}
