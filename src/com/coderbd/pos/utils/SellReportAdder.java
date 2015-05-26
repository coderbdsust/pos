/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils;

import com.coderbd.pos.entity.Shop;
import com.coderbd.pos.entity.pojo.ShopBalance;
import com.coderbd.pos.entity.pojo.ShopDailyProfit;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Biswajit Debnath
 */
public class SellReportAdder {

    private ResetTable resetter;

    public SellReportAdder() {
        resetter = new ResetTable();
    }

    public void refreshSellReportShops(JComboBox cb, List<Shop> shops) {
        cb.removeAllItems();
        cb.addItem("ALL SHOPS");
        for (Shop shop : shops) {
            cb.addItem(shop.getShopName());
        }
    }

    public void updateSellReportTable(ShopBalance shopBalance, JTable table,
            JLabel sell, JLabel paid, JLabel due, JLabel profit, Date fromDate, Date toDate) {
        resetter.resetTable(table);
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

        List<ShopDailyProfit> shopDailyProfits = shopBalance.getShopDailyProfits();
        Shop shop = shopBalance.getShop();
        Double sellValue = 0.0;
        Double paidValue = 0.0;
        Double dueValue = 0.0;
        Double profitValue = 0.0;

        for (ShopDailyProfit sdp : shopDailyProfits) {

            Date date = sdp.getDate();

            if (DateUtil.isDateInRange(fromDate, date, toDate)) {
                Object object[] = {
                    shop.getShopName(),
                    sdp.getDate(),
                    sdp.getDailySell(),
                    sdp.getDailyPaid(),
                    sdp.getDailyDue(),
                    sdp.getDailyProfit()
                };
                sellValue += sdp.getDailySell();
                paidValue += sdp.getDailyPaid();
                dueValue += sdp.getDailyDue();
                profitValue += sdp.getDailyProfit();
                tableModel.addRow(object);
            }
        }

        updateSellInfoLabel(sell, sellValue, paid, paidValue, due, dueValue, profit, profitValue);
    }

    public void updateSellReportTable(List<ShopBalance> shopBalances, JTable table,
            JLabel sell, JLabel paid, JLabel due, JLabel profit, Date fromDate, Date toDate) {
        resetter.resetTable(table);
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

        Double sellValue = 0.0;
        Double paidValue = 0.0;
        Double dueValue = 0.0;
        Double profitValue = 0.0;

        for (ShopBalance shopBalance : shopBalances) {
            List<ShopDailyProfit> shopDailyProfits = shopBalance.getShopDailyProfits();
            Shop shop = shopBalance.getShop();
            for (ShopDailyProfit sdp : shopDailyProfits) {
                Date date = sdp.getDate();
                if (DateUtil.isDateInRange(fromDate, date, toDate)) {
                    Object object[] = {
                        shop.getShopName(),
                        sdp.getDate(),
                        sdp.getDailySell(),
                        sdp.getDailyPaid(),
                        sdp.getDailyDue(),
                        sdp.getDailyProfit()
                    };
                    sellValue += sdp.getDailySell();
                    paidValue += sdp.getDailyPaid();
                    dueValue += sdp.getDailyDue();
                    profitValue += sdp.getDailyProfit();
                    tableModel.addRow(object);
                }
            }
        }
        updateSellInfoLabel(sell, sellValue, paid, paidValue, due, dueValue, profit, profitValue);
    }

    public void updateSellInfoLabel(JLabel sell, Double sellValue,
            JLabel paid, Double paidValue,
            JLabel due, Double dueValue,
            JLabel profit, Double profitValue) {
        sell.setText(sellValue.toString());
        paid.setText(paidValue.toString());
        due.setText(dueValue.toString());
        profit.setText(profitValue.toString());
    }

}
