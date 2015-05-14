/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Biswajit Debnath
 */
public class Converter {

    public Timestamp convertDateToTimestamp(Date date) {
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

}
