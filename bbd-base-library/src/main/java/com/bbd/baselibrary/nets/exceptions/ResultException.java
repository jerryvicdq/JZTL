package com.bbd.baselibrary.nets.exceptions;

/**
 * created by arvin on 16/10/24 17:29
 * email：1035407623@qq.com
 */
public class ResultException extends RuntimeException {
    private int errCode = 0;

    private String message;

    private String data;

    public ResultException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
        this.message = msg;
    }



    public ResultException(int errCode, String msg,String data) {
        super(msg);
        this.errCode = errCode;
        this.message = msg;
        this.data = data;
    }


    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}
