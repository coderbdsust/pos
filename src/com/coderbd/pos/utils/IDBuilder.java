/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import com.coderbd.pos.constraints.Enum;

/**
 *
 * @author Biswajit Debnath
 */
public class IDBuilder {

    SimpleDateFormat simpleDateFormat;

    public IDBuilder() {
        simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss");
    }

    public IDBuilder(String timestampPattern) {
        simpleDateFormat = new SimpleDateFormat(timestampPattern);
    }

    public String getUniqueTimeStampID() {
        String uniqueID = simpleDateFormat.format(new Date());
        System.out.println("Unique ID: " + uniqueID.length());
        return uniqueID;
    }

    public String geProductUniqueID() {
        String uniqueID = Enum.PRODUCT + simpleDateFormat.format(new Date());
        System.out.println("Product Unique ID: " + uniqueID.length());
        return uniqueID;
    }

    public String geOrderUniqueID() {
        String uniqueID = Enum.ORDER + simpleDateFormat.format(new Date());
        System.out.println("Order Unique ID: " + uniqueID.length());
        return uniqueID;
    }

    public int randomID(int minRange, int maxRange) {
        Random random = new Random();
        int randomValue = random.nextInt(maxRange - minRange) + minRange;
        return randomValue;
    }

}
