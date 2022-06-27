package com.dini.stop.bean.exception;

public enum ReturnCode {

    ERROR_USER(101),
    ERROR_VEHICULE(102),
    ERROR_TRAJET(103),

    USER_OK(201),
    VEHICULE_OK(202),
    TRAJET_OK(203),

    USER_EXIST(301),
    OK(200);

    private int code;

    ReturnCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
