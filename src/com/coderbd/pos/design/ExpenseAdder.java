/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.design;

import com.coderbd.pos.entity.Expenditure;
import com.coderbd.pos.entity.ExpenseCategory;
import com.coderbd.pos.entity.Product;
import com.coderbd.pos.utils.ShopExpense;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Biswajit Debnath
 */
public class ExpenseAdder {
    
    private ResetTable resetter;
    
    public ExpenseAdder() {
        resetter = new ResetTable();
    }
    
    public void addCategoriesInComboBox(List<ShopExpense> shopExpenses, JComboBox combox) {
        combox.removeAllItems();
        for (ShopExpense shopExp : shopExpenses) {
            ExpenseCategory expCategory = shopExp.getExpenseCategory();
            combox.addItem(expCategory.getExpName());
        }
    }
    
    public void addExpendituresInTable(List<Expenditure> expenditures, JTable table) {
        resetter.resetTable(table);
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        for (Expenditure e : expenditures) {
            Object object[] = {e.getExpTime(), e.getExpAmount(), e.getExpDesc()};
            tableModel.addRow(object);
        }
    }
    
}
