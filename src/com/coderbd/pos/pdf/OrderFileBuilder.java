/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.pdf;

import com.coderbd.pos.entity.Shop;
import com.coderbd.pos.entity.pojo.ShopOrder;
import com.coderbd.pos.utils.DirectoryCreator;
import com.coderbd.pos.utils.ReceiptIndent;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Biswajit Debnath
 */
public class OrderFileBuilder {

    private ReceiptIndent receipt;
    private DirectoryCreator directoryCreator;
    private String directory = "GenratedBill";

    public OrderFileBuilder() {
        directoryCreator = new DirectoryCreator();
        directoryCreator.makeDir(directory);
        receipt = new ReceiptIndent();
    }

    public OrderFileBuilder(Shop shop) {
        directoryCreator = new DirectoryCreator();
        directory = shop.getShopName() + "\\" + directory;
        directoryCreator.makeDirs(directory);
        receipt = new ReceiptIndent();
    }

    public String makePdf(ShopOrder shopOrder) {
        Document document = new Document(new Rectangle(205, 800));
        String fileName = directory + "\\" + shopOrder.getCustomerOrder().getOrderBarcode() + ".pdf";
        String receiptText = receipt.getIndentedOrder(shopOrder);
        System.out.println(receiptText);

        document.setMargins(3, 2, 2, 2);

        Font courierFont = FontFactory.getFont("courier");
        courierFont.setSize(10f);

        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(fileName));

            document.open();
            document.add(new Paragraph(receiptText, courierFont));
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public String makeTextFile(ShopOrder shopOrder) {

        String fileName = directory + "\\" + shopOrder.getCustomerOrder().getOrderBarcode() + ".txt";
        String receiptText = receipt.getIndentedOrder(shopOrder);

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());

            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(receiptText);
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return fileName;
    }

}
