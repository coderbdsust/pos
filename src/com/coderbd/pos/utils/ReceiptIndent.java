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
    private int lineLen = 37;

    private void printShopHeader(String header) {
        header = header.replace("  ", " ");
        header = "\n" + mergibleLine(header, lineLen);
        output += header;
    }

    private void printOrderID(String OrderCode) {
        output += String.format("\n%-37s\n", "ORDER ID: " + OrderCode);
    }

    private void printProductTitle() {
        output += String.format("%-3s %5s %5s %10s %10s\n", "QTY", "DIS ", "VAT(%)", "  RATE   ", "   TOTAL  ");
        output += String.format("%-3s %-5s %-5s %10s %10s\n", "---", "----", "----", "----------", "----------");
    }

    private void printItem(String itemName, int qty, double dis, double vat, double sellRate, double price) {
        output += String.format("%-37s\n", itemName);
        output += String.format("%-3d %-5.2f %-5.2f %10.2f %10.2f\n", qty, dis, vat, sellRate, price);

    }

    private void printTotal(double totalBill, double totalPaid, double due) {
        output += String.format("%-17s %19s\n", " ", "-----------------");
        output += String.format("%-17s %19.2f\n", "TOTAL BILL", totalBill);
        output += String.format("%-17s %19.2f\n", "TOTAL PAID", totalPaid);
        output += String.format("%-17s %19s\n", " ", "-----------------");
        output += String.format("%-17s %19.2f\n", "DUE", due);
    }

    private void printShopFooter(String footer) {
        output += String.format("%37s\n", footer);
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
            printItem(op.getProductBarcode(), qty, dis, vat, sellRate, price);
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

    public String mergibleLine(String str, int len) {
        String sret = "";
        String[] words = str.split(",");
        String tempLine = "";
        for (String word : words) {
            if ((tempLine + word).length() <= len) {
                if (tempLine.length() == 0) {
                    tempLine = word;
                } else {
                    tempLine = tempLine + ", " + word;
                }
            } else {
                tempLine = centerAlign(tempLine, len);
                if (sret.length() == 0) {
                    sret = tempLine;
                } else {
                    sret = sret + "\n" + tempLine;
                }
                tempLine = word;
            }
        }
        if (tempLine.length() != 0) {
            tempLine = centerAlign(tempLine, len);
            sret = sret + "\n" + tempLine;
        }
        return sret;
    }

}
//
//package com.coderbd.pos.utils;
//
//import com.coderbd.pos.entity.pojo.ShopOrder;
//import com.coderbd.pos.entity.CustomerOrder;
//import com.coderbd.pos.entity.OrderProduct;
//import java.util.Formatter;
//import java.util.List;
//
///**
// *
// * @author Biswajit Debnath
// */
//public class ReceiptIndent {
//
//    private String output = "";
//
//    private void printShopHeader(String header) {
//        header = header.replace(", ", ",");
//        header = centerAlign(header, 37);
//        output += String.format("%37s\n", header);
//    }
//
//    private void printOrderID(String OrderCode) {
//        output += String.format("\n%-37s\n", "ORDER ID: " + OrderCode);
//    }
//
//    private void printProductTitle() {
//        output += String.format("%-3s %5s %5s %10s %10s\n", "QTY", "DIS ", "VAT(%)", "  Rate   ", "   Total  ");
//        output += String.format("%-3s %-5s %-5s %10s %10s\n", "---", "----", "----", "----------", "----------");
//    }
//
//    private void printItem(String itemName, int qty, double dis, double vat, double sellRate, double price) {
//        output += String.format("%-37s\n", itemName);
//        output += String.format("%-3d %-5.2f %-5.2f %10.2f %10.2f\n", qty, dis, vat, sellRate, price);
//
//    }
//
//    private void printTotal(double totalBill, double totalPaid, double due) {
//        output += String.format("%-17s %19s\n", " ", "-----------------");
//        output += String.format("%-17s %19.2f\n", "TOTAL BILL", totalBill);
//        output += String.format("%-17s %19.2f\n", "TOTAL PAID", totalPaid);
//        output += String.format("%-17s %19s\n", " ", "-----------------");
//        output += String.format("%-17s %19.2f\n", "DUE", due);
//    }
//
//    private void printShopFooter(String footer) {
//        output += String.format("%37s\n", footer);
//    }
//
//    public String getIndentedOrder(ShopOrder shopOrder) {
//        output = "";
//        List<OrderProduct> orderProducts = shopOrder.getOrderProducts();
//        CustomerOrder customerOrder = shopOrder.getCustomerOrder();
//
//        printShopHeader(customerOrder.getShop().getShopName());
//        printShopHeader(customerOrder.getShop().getShopAddress());
//        printShopHeader("Mob:" + customerOrder.getShop().getShopMobile());
//
//        printOrderID(customerOrder.getOrderBarcode());
//        printProductTitle();
//        for (OrderProduct op : orderProducts) {
//
//            int qty = op.getOrderProductQuantity();
//            double dis = op.getOrderProductDiscount();
//            double vat = op.getOrderProductVat();
//            double sellRate = op.getOrderProductSellRate();
//            double qtyPrice = sellRate * qty;
//
//            double price = qtyPrice + qtyPrice * 0.01 * vat - qtyPrice * 0.01 * dis;
//
//            System.out.println(price);
//
//            printItem(op.getProductBarcode() + "-" + op.getProductName(), qty, dis, vat, sellRate, price);
//        }
//        printTotal(customerOrder.getTotalAmount(),
//                customerOrder.getTotalPaid(),
//                customerOrder.getTotalDue());
//        printShopFooter("                              \n");
//        printShopFooter("-------------THE END------------\n");
//        printShopFooter("                              \n");
//        return output;
//    }
//
//    private String centerAlign(String str, int expectedLength) {
//        int length = str.length();
//        int space = (expectedLength - length) / 2;
//
//        if (space > 0) {
//            while (space-- != 0) {
//                str = " " + str + " ";
//            }
//            if (str.length() != expectedLength) {
//                str = " " + str;
//            }
//        }
//        return str;
//    }
//
//}