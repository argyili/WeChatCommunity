package com.hfut.community.demo.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 */
@Data
@Entity
@Table(name = "user")
public class User {
    /**
     */
    @Id
    private String wxid = null;
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
}
