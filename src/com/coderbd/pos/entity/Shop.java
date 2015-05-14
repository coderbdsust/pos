/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.entity;

/**
 *
 * @author Biswajit Debnath
 */
public class Shop {

    private int shopId;
    private String shopName;
    private String shopTin;
    private String shopAddress;
    private String shopMobile;

    public Shop() {
    }

    public Shop(int shopId, String shopName, String shopTin, String shopAddress, String shopMobile) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopTin = shopTin;
        this.shopAddress = shopAddress;
        this.shopMobile = shopMobile;
    }

    public Shop(String shopName, String shopTin, String shopAddress, String shopMobile) {
        this.shopName = shopName;
        this.shopTin = shopTin;
        this.shopAddress = shopAddress;
        this.shopMobile = shopMobile;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopTin() {
        return shopTin;
    }

    public void setShopTin(String shopTin) {
        this.shopTin = shopTin;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopMobile() {
        return shopMobile;
    }

    public void setShopMobile(String shopMobile) {
        this.shopMobile = shopMobile;
    }

    @Override
    public String toString() {
        return "Shop{" + "shopId=" + shopId + ", shopName=" + shopName + ", shopTin=" + shopTin + ", shopAddress=" + shopAddress + ", shopMobile=" + shopMobile + '}';
    }

}
