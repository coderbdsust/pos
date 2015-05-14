/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.design;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Biswajit Debnath
 */
public class ResetTable {
    
    public void resetTable(JTable table) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        while (tableModel.getRowCount() != 0) {
            tableModel.removeRow(0);
        }
    }
}
