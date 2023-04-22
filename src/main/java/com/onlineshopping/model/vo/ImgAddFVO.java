package com.onlineshopping.model.vo;

import org.springframework.web.multipart.MultipartFile;

public class ImgAddFVO {
    private Integer productId;
    private MultipartFile image;

    @Override
    public String toString() {
        return "ImgAddFVO{" +
                "productId=" + productId +
                ", image=" + image +
                '}';
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public ImgAddFVO() {
    }

    public ImgAddFVO(Integer productId, MultipartFile image) {
        this.productId = productId;
        this.image = image;
    }
}
