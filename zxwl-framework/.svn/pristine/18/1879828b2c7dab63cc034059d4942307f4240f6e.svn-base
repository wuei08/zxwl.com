package com.zxwl.web.bean.po;

import java.util.Date;

/**
 * Project: zxwl-framework
 * Author: Sendya <18x@loacg.com>
 * Date: 2017/7/17 14:57
 */
public class BasePo<PK> extends GenericPo<PK> {

    //创建时间
    private Date gmtCreate;
    //修改时间
    private Date gmtModify;
    //上次修改用户
    private String lastChangeUser;

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getLastChangeUser() {
        return lastChangeUser;
    }

    public void setLastChangeUser(String lastChangeUser) {
        this.lastChangeUser = lastChangeUser;
    }

    public interface Property extends GenericPo.Property {
        //创建时间
        String gmtCreate="gmtCreate";
        //修改时间
        String gmtModify="gmtModify";
        //上次修改用户
        String lastChangeUser="lastChangeUser";
    }
}
