/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils;

import com.coderbd.pos.entity.pojo.ShopOrder;
import com.coderbd.pos.entity.CustomerOrder;
import com.coderbd.pos.entity.OrderProduct;
import java.util.Formatter;
import java.util.List;

/**
 *
 * @author Biswajit Debnath
 */
public class ReceiptIndent {

    private String output = "";

    private void printShopHeader(String header) {
        header = header.replace(", ", ",");
        header = centerAlign(header, 35);
        output += String.format("%35s\n", header);
    }

    private void printOrderID(String OrderCode) {
        output += String.format("\n%-35s\n", "ORDER ID: " + OrderCode);
    }

    private void printProductTitle() {
        output += String.format("%-3s %4s %4s %10s %10s\n", "QTY", "DIS ", " VAT", "   Rate   ", "   Total  ");
        output += String.format("%-3s %-4s %-4s %10s %10s\n", "---", "----", "----", "----------", "----------");
    }

    private void printItem(String itemName, int qty, double dis, double vat, double sellRate, double price) {
        output += String.format("%-35s\n", itemName);
        output += String.format("%-3d %-4.2f %-4.2f %10.2f %10.2f\n", qty, dis, vat, sellRate, price);

    }

    private void printTotal(double totalBill, double totalPaid, double due) {
        output += String.format("%-17s %17s\n", " ", "-----------------");
        output += String.format("%-17s %17.2f\n", "TOTAL BILL", totalBill);
        output += String.format("%-17s %17.2f\n", "TOTAL PAID", totalPaid);
        output += String.format("%-17s %17s\n", " ", "-----------------");
        output += String.format("%-17s %17.2f\n", "DUE", due);
    }

    private void printShopFooter(String footer) {
        output += String.format("%35s\n", footer);
    }

    public String getIndentedOrder(ShopOrder shopOrder) {
        output = "";
        List<OrderProduct> orderProducts = shopOrder.getOrderProducts();
        CustomerOrder customerOrder = shopOrder.getCustomerOrder();

        printShopHeader(customerOrder.getShop().getShopName());
        printShopHeader(customerOrder.getShop().getShopAddress());
        printShopHeader("Mob:" + customerOrder.getShop().getShopMobile());

        printOrderID(customerOrder.getOrderBarcode());
        printProductTitle();
        for (OrderProduct op : orderProducts) {

            int qty = op.getOrderProductQuantity();
            double dis = op.getOrderProductDiscount();
            double vat = op.getOrderProductVat();
            double sellRate = op.getOrderProductSellRate();
            double qtyPrice = sellRate * qty;

            double price = qtyPrice + qtyPrice * 0.01 * vat - qtyPrice * 0.01 * dis;
            
            System.out.println(price);
            
            printItem(op.getProductName(), qty, dis, vat, sellRate, price);
        }
        printTotal(customerOrder.getTotalAmount(),
                customerOrder.getTotalPaid(),
                customerOrder.getTotalDue());
        printShopFooter("                              \n");
        printShopFooter("-------------THE END------------\n");
        printShopFooter("                              \n");
        return output;
    }

    private String centerAlign(String str, int expectedLength) {
        int length = str.length();
        int space = (expectedLength - length) / 2;

        if (space > 0) {
            while (space-- != 0) {
                str = " " + str + " ";
            }
            if (str.length() != expectedLength) {
                str = " " + str;
            }
        }
        return str;
    }

}

/*
 THE NEW CHITRA BOUTIQUES    
 381,AL-HAMRA SHOPPING CITY,SYLHET
 Mob:01937595521        

 ORDER ID: 2150522134950         
 QTY   DIS    VAT       PRICE    
 --- ------ ------ --------------
 NEEL DRESS               
 2   0.00   0.00          6000.00
 PAKHI DRESS              
 1   0.00   0.00          2500.00
 --------------
 TOTAL BILL               8500.00
 TOTAL PAID                  0.00
 --------------
 DUE                      8500.00
 */
