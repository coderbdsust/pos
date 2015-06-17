/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils.adder;

import com.coderbd.pos.entity.pojo.ShopOrder;
import com.coderbd.pos.constraints.Enum;
import com.coderbd.pos.constraints.Message;
import com.coderbd.pos.entity.OrderProduct;
import com.coderbd.pos.entity.Product;
import com.coderbd.pos.utils.Reset;
import com.coderbd.pos.utils.Search;
import java.awt.Color;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Biswajit Debnath
 */
public class OrderProductAdder {

    private Reset resetter;
    private Search search;

    public OrderProductAdder() {
        resetter = new Reset();
        search = new Search();
    }

    public void addProductInOrderView(List<Product> products, ShopOrder shopOrder,
            String productBarcode, double discount, double vat,
            boolean usePreviousSellRate, JTable orderTable, JLabel productInfoLabel,
            JLabel orderTotalBill, JTextField cashIn, JLabel cashBack) {

        int productIndex = search.searchProduct(products, productBarcode);

        if (productIndex != Enum.invalidIndex) {

            Product product = products.get(productIndex);

            if (product.getProductStock() <= 0) {

                productInfoLabel.setForeground(Color.RED);
                productInfoLabel.setText(Message.ERROR_PRODUCT_OUT_OF_STOCK);

            } else {

                List<OrderProduct> orderProducts = shopOrder.getOrderProducts();

                int orderProductIndex = search.searchOrderProduct(orderProducts, productBarcode);
                boolean dataUpdated = false;

                if (orderProductIndex == Enum.invalidIndex) {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setProductId(product.getProductId());
                    orderProduct.setProductName(product.getProductName());
                    orderProduct.setProductBarcode(productBarcode);

                    orderProduct.setProductBuyRate(product.getProductBuyRate());
                    if (usePreviousSellRate == true) {
                        orderProduct.setOrderProductSellRate(product.getProductSellRate());
                    } else {
                        orderProduct.setOrderProductSellRate(0);
                    }
                    orderProduct.setOrderProductQuantity(1);
                    orderProduct.setOrderProductVat(vat);
                    orderProduct.setOrderProductDiscount(discount);
                    orderProduct.setProductStock(product.getProductStock());
                    orderProducts.add(orderProduct);
                    dataUpdated = true;
                } else {
                    OrderProduct orderProduct = orderProducts.get(orderProductIndex);
                    if (orderProduct.getProductStock() > orderProduct.getOrderProductQuantity()) {
                        orderProduct.setOrderProductQuantity(
                                orderProduct.getOrderProductQuantity() + 1
                        );
                        dataUpdated = true;
                    }
                }

                if (dataUpdated) {
                    Double productTotalAmount = updateOrderView(orderTable, orderProducts);
                    orderTotalBill.setText(productTotalAmount.toString());
                    updateCash(orderTotalBill, cashIn, cashBack, shopOrder);

                }
                updateCash(orderTotalBill, cashIn, cashBack, shopOrder);

            }
            productInfoLabel.setText("");

        } else {
            productInfoLabel.setForeground(Color.RED);
            productInfoLabel.setText(Message.ERROR_PRODUCT_NOT_FOUND);
        }
    }

    public double updateOrderView(JTable table, List<OrderProduct> orderProducts) {

        resetter.resetTable(table);

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        int index = 0;
        double productTotalAmount = 0.0;

        for (OrderProduct op : orderProducts) {
            double productAmount = 0;
            double originalPrice = op.getOrderProductQuantity() * op.getOrderProductSellRate();
            productAmount = originalPrice - originalPrice * op.getOrderProductDiscount() * .01 + originalPrice * op.getOrderProductVat() * 0.01;

            Object object[] = {++index, op.getProductBarcode(),
                op.getProductName(),
                op.getOrderProductQuantity(),
                op.getOrderProductSellRate(),
                op.getOrderProductDiscount(),
                op.getOrderProductVat(),
                productAmount};
            productTotalAmount += productAmount;
            tableModel.addRow(object);
        }
        return productTotalAmount;

    }

    public void updateCash(JLabel totalBillViewLabel, JTextField cashInField, JLabel cashBackViewLabel, ShopOrder shopOrder) {
        Double totalAmount = 0.0;
        Double cashIn = 0.0;
        Double cashOut = 0.0;

        try {
            totalAmount = Double.parseDouble(totalBillViewLabel.getText());
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
        }

        try {
            cashIn = Double.parseDouble(cashInField.getText());
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
            cashBackViewLabel.setText("");
        }

        cashOut = totalAmount - cashIn;

        if (cashOut >= 0) {
            cashBackViewLabel.setForeground(Color.RED);
        } else {
            cashBackViewLabel.setForeground(Color.GREEN);
        }

        cashBackViewLabel.setText(cashOut.toString());
        shopOrder.getCustomerOrder().setTotalAmount(totalAmount);
        shopOrder.getCustomerOrder().setTotalPaid(cashIn);
        shopOrder.getCustomerOrder().setTotalDue(cashOut);
    }

}
