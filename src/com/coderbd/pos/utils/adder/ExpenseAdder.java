/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils.adder;

import com.coderbd.pos.entity.Expenditure;
import com.coderbd.pos.entity.ExpenseCategory;
import com.coderbd.pos.entity.Product;
import com.coderbd.pos.entity.pojo.ShopExpense;
import com.coderbd.pos.utils.Reset;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Biswajit Debnath
 */
public class ExpenseAdder {

    private Reset resetter;

    public ExpenseAdder() {
        resetter = new Reset();
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

    public Double getTotalExpense(List<Expenditure> expenditures) {
        double total = 0;
        for (Expenditure e : expenditures) {
            total += e.getExpAmount();
        }
        return total;
    }
}
