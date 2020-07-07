package com.itheima.logdemo.utils;

import com.alibaba.fastjson.JSON;

public class LogBean {

    private String rid, sid, tid, from, message;
    private String ip ,url;
    private Long time;

    public static final ThreadLocal<LogBean> logBeanThreadLocal = new ThreadLocal<>();

    public LogBean(String rid, String sid, String tid, String from, String message, String ip, String url) {
        this.rid = rid;
        this.sid = sid;
        this.tid = tid;
        this.from = from;
        this.message = message;
        this.ip = ip;
        this.url = url;
        logBeanThreadLocal.set(this);
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        this.setTime(System.currentTimeMillis());
        return JSON.toJSONString(this);
    }
}
