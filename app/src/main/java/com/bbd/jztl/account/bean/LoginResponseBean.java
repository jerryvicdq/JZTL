package com.bbd.jztl.account.bean;

/**
 * 类描述：
 * 创建人：圈圈D
 * 创建时间：2018-10-10 12:53
 */
public class LoginResponseBean {

    private String access_token;

    private String token_type;

    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
