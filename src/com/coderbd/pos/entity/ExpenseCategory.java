/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Biswajit Debnath
 */
public class ExpenseCategory {

    private int expCategoryId;
    private int shopId;
    private String expName;

    public ExpenseCategory() {

    }

    public ExpenseCategory(int shopId, String expName) {
        this.shopId = shopId;
        this.expName = expName;

    }

    public ExpenseCategory(int expCategoryId, int shopId, String expName) {
        this.expCategoryId = expCategoryId;
        this.shopId = shopId;
        this.expName = expName;

    }

    public int getExpCategoryId() {
        return expCategoryId;
    }

    public void setExpCategoryId(int expCategoryId) {
        this.expCategoryId = expCategoryId;
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

    @Override
    public String toString() {
        return "ExpenseCategory{" + "expCategoryId=" + expCategoryId + ", shopId=" + shopId + ", expName=" + expName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.expCategoryId;
        hash = 71 * hash + this.shopId;
        hash = 71 * hash + Objects.hashCode(this.expName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExpenseCategory other = (ExpenseCategory) obj;
        if (this.expCategoryId != other.expCategoryId) {
            return false;
        }
        if (this.shopId != other.shopId) {
            return false;
        }
        if (!Objects.equals(this.expName, other.expName)) {
            return false;
        }
        return true;
    }

}
