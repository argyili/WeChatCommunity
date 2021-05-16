package com.hfut.community.demo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 */
@Data
@Entity
@Table(name = "block")
public class Block {
    /**
     */
    @Id
    private String block;
    /**
     */
    private String phoneNum;
    /**
     * @param block String
     * @param phoneNum String
     */
    public Block(String block, String phoneNum) {
        this.block = block;
        this.phoneNum = phoneNum;
    }

    /**
     */
    public Block() {
    }
}
