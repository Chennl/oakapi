package com.oak.weixin.entity;

/**
 * Created by Chennl on 7/9/2017.
 */
public class AccessTokenResponse {
    private int errcode;
    private String errmsg;
    private String acess_token;
    private int expires_in;

    public AccessTokenResponse() {
        acess_token=null;
    }

    public int getErrcode() {

        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getAcesstoken() {
        return acess_token;
    }

    public void setAcesstoken(String acess_token) {
        this.acess_token = acess_token;
    }

    public int getExpiresin() {
        return expires_in;
    }

    public void setExpiresin(int expires_in) {
        this.expires_in = expires_in;
    }


}
