package com.chat.client.utils;

public final class GlobalCodeMgr {
    private static final GlobalCodeMgr MGR = new GlobalCodeMgr();
    private StringBuffer code;

    public static GlobalCodeMgr getInstance(){
        return MGR;
    }
    public StringBuffer getCode() {
        return code;
    }
    public void setCode(StringBuffer code) {
        this.code = code;
    }
}

