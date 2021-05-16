package com.hfut.community.demo.domain;

import lombok.Data;

@Data
public class Phone {
    /**
     */
    private String phoneCode;
    /**
     */
    private String verificationCode;

    public Phone() {
    }
}
