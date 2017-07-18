package com.zxwl.web.bean;
import com.zxwl.web.bean.po.GenericPo;
/**
* 店铺装修表
* Created by generator Jul 18, 2017 1:46:59 AM
*/
public class ShopDecoration extends GenericPo<String>{
  		//店铺id
        private String shopId;
  		//店铺图文信息内容
        private String content;
  		//
        private String img1;
  		//
        private String img2;
  		//
        private String img3;

        /**
        * 获取 店铺id
        * @return String 店铺id
        */
        public String getShopId(){
			return this.shopId;
        }

        /**
        * 设置 店铺id
        */
        public void setShopId(String shopId){
        	this.shopId=shopId;
        }
        /**
        * 获取 店铺图文信息内容
        * @return String 店铺图文信息内容
        */
        public String getContent(){
			return this.content;
        }

        /**
        * 设置 店铺图文信息内容
        */
        public void setContent(String content){
        	this.content=content;
        }
        /**
        * 获取 
        * @return String 
        */
        public String getImg1(){
			return this.img1;
        }

        /**
        * 设置 
        */
        public void setImg1(String img1){
        	this.img1=img1;
        }
        /**
        * 获取 
        * @return String 
        */
        public String getImg2(){
			return this.img2;
        }

        /**
        * 设置 
        */
        public void setImg2(String img2){
        	this.img2=img2;
        }
        /**
        * 获取 
        * @return String 
        */
        public String getImg3(){
			return this.img3;
        }

        /**
        * 设置 
        */
        public void setImg3(String img3){
        	this.img3=img3;
        }
      
      public interface Property extends GenericPo.Property{
                //店铺id
                 String shopId="shopId";
                //店铺图文信息内容
                 String content="content";
                //
                 String img1="img1";
                //
                 String img2="img2";
                //
                 String img3="img3";
    	}
}