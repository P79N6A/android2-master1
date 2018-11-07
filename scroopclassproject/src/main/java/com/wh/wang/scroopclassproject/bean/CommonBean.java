package com.wh.wang.scroopclassproject.bean;

/**
 * Created by wang on 2017/9/3.
 */


public class CommonBean {
    /**
     * id : 160
     * product_id : 115
     * img : 9/E/d.l.png
     * type : 1
     * isclick : 1
     */

    private String id;
    private String product_id;
    private String img;
    private String type;
    private String isclick;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsclick() {
        return isclick;
    }

    public void setIsclick(String isclick) {
        this.isclick = isclick;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "id='" + id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", img='" + img + '\'' +
                ", type='" + type + '\'' +
                ", isclick='" + isclick + '\'' +
                '}';
    }

}
