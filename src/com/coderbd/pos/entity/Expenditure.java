/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.entity;

import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author Biswajit Debnath
 */
public class Expenditure {

    private int expId;
    private int expCategoryId;
    private double expAmount;
    private Timestamp expTime;
    private String expDesc;

    public Expenditure() {

    }

    public Expenditure(int expCategoryId, double expAmount, Timestamp expTime, String expDesc) {
        this.expCategoryId = expCategoryId;
        this.expAmount = expAmount;
        this.expTime = expTime;
        this.expDesc = expDesc;
    }

    public Expenditure(int expId, int expCategoryId, double expAmount, Timestamp expTime, String expDesc) {
        this.expId = expId;
        this.expCategoryId = expCategoryId;
        this.expAmount = expAmount;
        this.expTime = expTime;
        this.expDesc = expDesc;
    }

    public int getExpId() {
        return expId;
    }

    public void setExpId(int expId) {
        this.expId = expId;
    }

    public int getExpCategoryId() {
        return expCategoryId;
    }

    public void setExpCategoryId(int expCategoryId) {
        this.expCategoryId = expCategoryId;
    }

    public double getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(double expAmount) {
        this.expAmount = expAmount;
    }

    public Timestamp getExpTime() {
        return expTime;
    }

    public void setExpTime(Timestamp expTime) {
        this.expTime = expTime;
    }

    public String getExpDesc() {
        return expDesc;
    }

    public void setExpDesc(String expDesc) {
        this.expDesc = expDesc;
    }

    @Override
    public String toString() {
        return "Expenditure{" + "expId=" + expId + ", expCategoryId=" + expCategoryId + ", expAmount=" + expAmount + ", expTime=" + expTime + ", expDesc=" + expDesc + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Expenditure other = (Expenditure) obj;
        if (this.expId != other.expId) {
            return false;
        }
        if (this.expCategoryId != other.expCategoryId) {
            return false;
        }
        if (Double.doubleToLongBits(this.expAmount) != Double.doubleToLongBits(other.expAmount)) {
            return false;
        }
        if (!Objects.equals(this.expTime, other.expTime)) {
            return false;
        }
        if (!Objects.equals(this.expDesc, other.expDesc)) {
            return false;
        }
        return true;
    }
    
    

}
