package com.zxwl.web.bean;

import com.zxwl.web.bean.po.GenericPo;

/**
 * 用户信息
 * Created by generator Jul 18, 2017 8:34:42 AM
 */
public class UserInfo extends GenericPo<String> {
    //
    private String name;
    //
    private int age;
    //
    private String sex;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    private double height;
    //
    private double weight;
    //
    private String telephone;
    //
    private String imgUrl;
    //
    private String userId;
    //
    private java.util.Date gmtCreate;
    //
    private java.util.Date gmtModify;

    /**
     * 获取
     *
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     *
     * @return int
     */
    public int getAge() {
        return this.age;
    }

    /**
     * 设置
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 获取
     *
     * @return double
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * 设置
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * 获取
     *
     * @return double
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * 设置
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * 获取
     *
     * @return String
     */
    public String getTelephone() {
        return this.telephone;
    }

    /**
     * 设置
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取
     *
     * @return String
     */
    public String getImgUrl() {
        return this.imgUrl;
    }

    /**
     * 设置
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 获取
     *
     * @return String
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * 设置
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取
     *
     * @return java.util.Date
     */
    public java.util.Date getGmtCreate() {
        return this.gmtCreate;
    }

    /**
     * 设置
     */
    public void setGmtCreate(java.util.Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取
     *
     * @return java.util.Date
     */
    public java.util.Date getGmtModify() {
        return this.gmtModify;
    }

    /**
     * 设置
     */
    public void setGmtModify(java.util.Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public interface Property extends GenericPo.Property {
        //
        String name = "name";
        //
        String age = "age";
        //
        String high = "high";
        //
        String weight = "weight";
        //
        String telephone = "telephone";
        //
        String imgUrl = "imgUrl";
        //
        String userId = "userId";
        //
        String gmtCreate = "gmtCreate";
        //
        String gmtModify = "gmtModify";
    }
}