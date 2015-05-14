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
public class ShopOwner {

    private int shopOwnerId;
    private User user;
    private Shop shop;

    public ShopOwner(int shopOwnerId, User user, Shop shop) {
        this.shopOwnerId = shopOwnerId;
        this.user = user;
        this.shop = shop;
    }

    public int getShopOwnerId() {
        return shopOwnerId;
    }

    public void setShopOwnerId(int shopOwnerId) {
        this.shopOwnerId = shopOwnerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "ShopOwner{" + "shopOwnerId=" + shopOwnerId + ", user=" + user + ", shop=" + shop + '}';
    }

}
