package com.hfut.community.demo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 */
@Data
@Entity
@Table(name = "admin")
public class Admin {
    /**
     */
    @Id
    private String phoneNum;
    /**
     */
    private String power;
    /**
     * @param phoneNum String
     * @param power String
     */
    public Admin(String phoneNum, String power) {
        this.phoneNum = phoneNum;
        this.power = power;
    }
    /**
     */
    public Admin() {
    }
}
