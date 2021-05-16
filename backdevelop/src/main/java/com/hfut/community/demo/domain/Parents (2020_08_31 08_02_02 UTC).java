package com.hfut.community.demo.domain;

import lombok.Data;

import javax.persistence.Table;

/**
 */
@Data
@Table(name = "parents")
public class Parents {
    /**
     */
    private String wxid;
    /**
     */
    private int purpose;
    public Parents() { }
    public Parents(String wxid, int purpose) {
        this.wxid = wxid;
        this.purpose = purpose;
    }
}
