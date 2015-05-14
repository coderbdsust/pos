/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.service;

import com.coderbd.pos.dao.ExpendituresDao;
import com.coderbd.pos.dao.ExpenseCategoriesDao;
import com.coderbd.pos.entity.Expenditure;
import com.coderbd.pos.entity.ExpenseCategory;
import com.coderbd.pos.entity.Shop;
import com.coderbd.pos.utils.ShopExpense;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Biswajit Debnath
 */
@Service("shopExpenseService")
public class ShopExpenseService {

    @Autowired
    private ExpendituresDao expendituresDao;

    @Autowired
    private ExpenseCategoriesDao expCategoriesDao;

    public List<ShopExpense> getShopExpenses(Shop shop) {
        List<ShopExpense> shopExpenses = new ArrayList<ShopExpense>();
        List<ExpenseCategory> expCategories;
        expCategories = expCategoriesDao.getExpenseCategories(shop.getShopId());
        for (ExpenseCategory expCategory : expCategories) {
            List<Expenditure> expditures = expendituresDao.getExpenditures(expCategory);
            ShopExpense shopExpense = new ShopExpense(expCategory, expditures);
            shopExpenses.add(shopExpense);
        }
        return shopExpenses;
    }

    public boolean createExpenseCategory(ExpenseCategory expCategory) {
        boolean status = expCategoriesDao.createExpenseCategory(expCategory);
        return status;
    }

    public ExpenseCategory getExpenseCategory(int shopId, String expName) {
        return expCategoriesDao.getExpenseCategory(shopId, expName);
    }
    
    public boolean saveExpenditure(Expenditure expenditure){
       return expendituresDao.createExpenditure(expenditure);
    }
    
    public List<Expenditure> getExpenditures(ExpenseCategory expCategory){
        return expendituresDao.getExpenditures(expCategory);
    }
    
    public boolean deleteExpenditure(Expenditure expenditure){
        return expendituresDao.deleteExpenditure(expenditure);
    }
}
