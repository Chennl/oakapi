package com.oak.weixin.entity;

public class WeixinMessage {
    private String touser;
    private String toparty;
    private String totag;
    private String msgtype;
    private int agentid;
    private int safe;

    Object text;//实际接收Map类型数据
    public WeixinMessage(){
        this.toparty = "@all";
        this.totag = "@all";
        this.safe = 0;
        this.msgtype = "text";
    }
    public Object getText() {
        return text;
    }
    public void setText(Object text) {
        this.text = text;
    }
    public String getMsgtype() {
        return msgtype;
    }
    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
    public int getAgentid() {
        return agentid;
    }
    public void setAgentid(int agentid) {
        this.agentid = agentid;
    }
    public String getTouser() {
        return touser;
    }
    public void setTouser(String touser) {
        this.touser = touser;
    }
    public  String getToparty() {
        return toparty;
    }
    public void   setToparty(String toparty) {
        this.toparty = toparty;
    }public String getTotag() {
        return totag;
    }public void   setTotag(String totag) {
        this.totag = totag;
    }public int    getSafe() {
        return safe;
    }public void   setSafe(int safe) {
        this.safe = safe;
    }

}