/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.barutils.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Biswajit Debnath
 */
public class IDBuilder {

    public String getUniqueTimeStampID() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
        String uniqueID = dateFormat.format(new Date());
        return uniqueID;
    }

    public int randomID(int minRange, int maxRange) {
        Random random = new Random();
        int randomValue = random.nextInt(maxRange - minRange) + minRange;
        return randomValue;
    }

}
