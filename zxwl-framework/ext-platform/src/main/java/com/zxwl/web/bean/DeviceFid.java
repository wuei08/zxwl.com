package com.zxwl.web.bean;
import com.zxwl.web.bean.po.GenericPo;
/**
* 射频管理
* Created by generator Jul 12, 2017 8:58:59 AM
*/
public class DeviceFid extends GenericPo<String>{
  		//设备序号
        private String devNum;
  		//设备编码
        private String devCode;
  		//
        private java.util.Date gmtCreate;
  		//
        private java.util.Date gmtModify;
  		//
        private String lastChangeUser;

        /**
        * 获取 设备序号
        * @return String 设备序号
        */
        public String getDevNum(){
			return this.devNum;
        }

        /**
        * 设置 设备序号
        */
        public void setDevNum(String devNum){
        	this.devNum=devNum;
        }
        /**
        * 获取 设备编码
        * @return String 设备编码
        */
        public String getDevCode(){
			return this.devCode;
        }

        /**
        * 设置 设备编码
        */
        public void setDevCode(String devCode){
        	this.devCode=devCode;
        }
        /**
        * 获取 
        * @return java.util.Date 
        */
        public java.util.Date getGmtCreate(){
			return this.gmtCreate;
        }

        /**
        * 设置 
        */
        public void setGmtCreate(java.util.Date gmtCreate){
        	this.gmtCreate=gmtCreate;
        }
        /**
        * 获取 
        * @return java.util.Date 
        */
        public java.util.Date getGmtModify(){
			return this.gmtModify;
        }

        /**
        * 设置 
        */
        public void setGmtModify(java.util.Date gmtModify){
        	this.gmtModify=gmtModify;
        }
        /**
        * 获取 
        * @return String 
        */
        public String getLastChangeUser(){
			return this.lastChangeUser;
        }

        /**
        * 设置 
        */
        public void setLastChangeUser(String lastChangeUser){
        	this.lastChangeUser=lastChangeUser;
        }
      
      public interface Property extends GenericPo.Property{
                //设备序号
                 String devNum="devNum";
                //设备编码
                 String devCode="devCode";
                //
                 String gmtCreate="gmtCreate";
                //
                 String gmtModify="gmtModify";
                //
                 String lastChangeUser="lastChangeUser";
    	}
}