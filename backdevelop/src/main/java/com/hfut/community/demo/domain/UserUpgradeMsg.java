package com.hfut.community.demo.domain;
import lombok.Data;
/**
 */
@Data
public class UserUpgradeMsg {
    /**
     */
    private String userPhone;
    /**
     */
    private String choice = "否";

    public UserUpgradeMsg() {
    }
}
