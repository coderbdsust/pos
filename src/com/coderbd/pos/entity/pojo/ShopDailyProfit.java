/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.entity.pojo;

import java.util.Date;

/**
 *
 * @author Biswajit Debnath
 */
public class ShopDailyProfit {

    private int shopId;
    private Date date;
    private double dailySell;
    private double dailyPaid;
    private double dailyDue;
    private double dailyProfit;

    public ShopDailyProfit() {

    }

    public ShopDailyProfit(int shopId, Date date, double dailySell, double dailyPaid, double dailyDue, double dailyProfit) {
        this.shopId = shopId;
        this.date = date;
        this.dailySell = dailySell;
        this.dailyPaid = dailyPaid;
        this.dailyDue = dailyDue;
        this.dailyProfit = dailyProfit;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDailySell() {
        return dailySell;
    }

    public void setDailySell(double dailySell) {
        this.dailySell = dailySell;
    }

    public double getDailyPaid() {
        return dailyPaid;
    }

    public void setDailyPaid(double dailyPaid) {
        this.dailyPaid = dailyPaid;
    }

    public double getDailyDue() {
        return dailyDue;
    }

    public void setDailyDue(double dailyDue) {
        this.dailyDue = dailyDue;
    }

    public double getDailyProfit() {
        return dailyProfit;
    }

    public void setDailyProfit(double dailyProfit) {
        this.dailyProfit = dailyProfit;
    }

    @Override
    public String toString() {
        return "ShopDailyProfit{" + "shopId=" + shopId + ", date=" + date + ", dailySell=" + dailySell + ", dailyPaid=" + dailyPaid + ", dailyDue=" + dailyDue + ", dailyProfit=" + dailyProfit + '}';
    }

}
