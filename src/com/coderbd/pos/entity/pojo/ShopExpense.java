/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.entity.pojo;

import com.coderbd.pos.entity.Expenditure;
import com.coderbd.pos.entity.ExpenseCategory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Biswajit Debnath
 */
public class ShopExpense {

    private ExpenseCategory expenseCategory;
    private List<Expenditure> expenditures;

    public ShopExpense(ExpenseCategory expenseCategory, List<Expenditure> expenditures) {
        this.expenseCategory = expenseCategory;
        this.expenditures = expenditures;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public List<Expenditure> getExpenditures() {
        return expenditures;
    }

    public void setExpenditures(List<Expenditure> expenditures) {
        this.expenditures = expenditures;
    }

    @Override
    public String toString() {
        return "ShopExpenses{" + "expenseCategory=" + expenseCategory + ", expenditures=" + expenditures + '}';
    }

}
