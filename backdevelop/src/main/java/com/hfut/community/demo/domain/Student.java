package com.hfut.community.demo.domain;

import lombok.Data;
import javax.persistence.Table;

/**
 */
@Data
@Table(name = "student")
public class Student {
    /**
     */
    private String wxid;
    /**
     */
    private String school;
    /**
     */
    private int topPrize;
    /**
     */
    private int identification;
    /**
     */
    private String img;
    public Student() { }
    public Student(
            String wxid, String school, int topPrize,
            int identification, String img) {
        this.wxid = wxid;
        this.school = school;
        this.topPrize = topPrize;
        this.identification = identification;
        this.img = img;
    }
}
