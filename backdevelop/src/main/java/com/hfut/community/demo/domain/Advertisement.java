package com.hfut.community.demo.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 */
@Data
@Entity
@Table(name = "advertisement")
public class Advertisement {
    /**
     */
    @Id
    private String aid;
    /**
     */
    private String img;
    /**
     */
    private String advContent;
    /**
     */
    public Advertisement() {
    }
    /**
     * @param aid String
     * @param img String
     * @param advContent v
     * @return Advertisement
     */
    public Advertisement(String aid, String img, String advContent) {
        this.aid = aid;
        this.img = img;
        this.advContent = advContent;
    }
}
