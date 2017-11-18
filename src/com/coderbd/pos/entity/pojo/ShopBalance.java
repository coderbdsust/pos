/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.entity.pojo;

import com.coderbd.pos.entity.Shop;
import com.coderbd.pos.entity.pojo.ShopDailyProfit;
import java.util.List;

/**
 *
 * @author Biswajit Debnath
 */
public class ShopBalance {

    private Shop shop;
    private List<ShopDailyProfit> shopDailyProfits;

    public ShopBalance(Shop shop, List<ShopDailyProfit> shopDailyProfits) {
        this.shop = shop;
        this.shopDailyProfits = shopDailyProfits;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<ShopDailyProfit> getShopDailyProfits() {
        return shopDailyProfits;
    }

    public void setShopDailyProfits(List<ShopDailyProfit> shopDailyProfits) {
        this.shopDailyProfits = shopDailyProfits;
    }

    @Override
    public String toString() {
        return "ShopBalance{" + "shop=" + shop + ", shopDailyProfits=" + shopDailyProfits + '}';
    }

}
