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
public class ShopExpenditure {
    
    private int shopExpId;
    private int shopId;
    private String expName;
    private double expAmount;
    private String expDesc;
    
    public ShopExpenditure(){
        
    }

    public ShopExpenditure(int shopExpId, int shopId, String expName, double expAmount, String expDesc) {
        this.shopExpId = shopExpId;
        this.shopId = shopId;
        this.expName = expName;
        this.expAmount = expAmount;
        this.expDesc = expDesc;
    }
    
    

    public int getShopExpId() {
        return shopExpId;
    }

    public void setShopExpId(int shopExpId) {
        this.shopExpId = shopExpId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public double getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(double expAmount) {
        this.expAmount = expAmount;
    }

    public String getExpDesc() {
        return expDesc;
    }

    public void setExpDesc(String expDesc) {
        this.expDesc = expDesc;
    }

    @Override
    public String toString() {
        return "ShopExpenditure{" + "shopExpId=" + shopExpId + ", shopId=" + shopId + ", expName=" + expName + ", expAmount=" + expAmount + ", expDesc=" + expDesc + '}';
    }
    
    
    
    
    
}
