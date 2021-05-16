package com.hfut.community.demo.domain;

import lombok.Data;

/**
 */
@Data
public class UpgradePlateAdminHTMLMsg {
    /**
     */
    private String info;

    /**
     * @param info String
     */
    public UpgradePlateAdminHTMLMsg(String info) {
        this.info = info;
    }
}
