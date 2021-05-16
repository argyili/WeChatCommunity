package com.hfut.community.demo.domain;

import lombok.Data;

import javax.persistence.Table;

/**
 */
@Data
@Table(name = "teacher")
public class Teacher {
    /**
     */
    private String wxid;
    /**
     */
    private int identification;
    /**
     */
    private String img;
    public Teacher() { }
    public Teacher(String wxid, int identification, String img) {
        this.wxid = wxid;
        this.identification = identification;
        this.img = img;
    }
}
