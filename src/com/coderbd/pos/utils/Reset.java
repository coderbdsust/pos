/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Biswajit Debnath
 */
public class Reset {

    public static void resetTable(JTable table) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        while (tableModel.getRowCount() != 0) {
            tableModel.removeRow(0);
        }
    }
    
    public static void resetComboBox(JComboBox cb) {
        cb.removeAllItems();
    }

    public static void removeRow(JTable table, int rowIndex) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        if (table.getRowCount() < rowIndex) {
            tableModel.removeRow(rowIndex);
        }
    }
}
