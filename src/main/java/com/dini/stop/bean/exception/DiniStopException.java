package com.dini.stop.bean.exception;

public class DiniStopException extends Exception{

    private ReturnCode returnCode;

    public DiniStopException(String message, Throwable cause, ReturnCode returnCode) {
        super(message,cause);
        this.returnCode = returnCode;
    }

    public DiniStopException(String message, Throwable cause) {
        super(message,cause);
    }
}
