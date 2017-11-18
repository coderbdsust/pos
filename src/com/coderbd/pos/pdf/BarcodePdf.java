/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.pdf;

import com.coderbd.pos.entity.Product;
import com.coderbd.pos.entity.Shop;
import com.coderbd.pos.utils.DirectoryCreator;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Biswajit Debnath
 */
public class BarcodePdf {

    private DirectoryCreator directoryCreator;
    private String directory = "GenratedBarcode";
    private Shop shop = null;
    private int barcodeQuantity = 11;
    private String barcodeData;
    private double productRate = 150;

    public BarcodePdf(int barcodeQuantity, String barcodeData) {
        this.barcodeQuantity = barcodeQuantity;
        this.barcodeData = barcodeData;
        directoryCreator = new DirectoryCreator();
        directoryCreator.makeDir(directory);
    }

    public BarcodePdf(String barcodeData, Shop shop) {
        this.shop = shop;
        this.barcodeData = barcodeData;
        directoryCreator = new DirectoryCreator();
        directory = shop.getShopName() + "\\" + directory;
        directoryCreator.makeDirs(directory);
    }

    public int getBarcodeQuantity() {
        return barcodeQuantity;
    }

    public void setBarcodeQuantity(int barcodeQuantity) {
        this.barcodeQuantity = barcodeQuantity;
    }

    public String getBarcodeData() {
        return barcodeData;
    }

    public void setBarcodeData(String barcodeData) {
        this.barcodeData = barcodeData;
    }

    public void generateBarcodePdf(int amount) throws UnsupportedEncodingException {
        try {
            String filename = directory + "\\" + barcodeData + ".pdf";
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            Barcode128 code25 = new Barcode128();
            code25.setGenerateChecksum(true);
            code25.setCode(barcodeData);
            code25.setSize(10f);
            code25.setX(1.50f);

            PdfPTable pdfPTable = new PdfPTable(3);
            float[] widths = {0.45f, .10f, .45f};
            pdfPTable.setWidths(widths);
            String codeName = getCodeName(shop.getShopName(), shop.getShopId(), amount);
            System.out.println("BarCodeName:" + codeName);

            Image image = code25.createImageWithBarcode(cb, null, null);

            Paragraph paragraph = new Paragraph(codeName);
            paragraph.setSpacingBefore(10.0f);
            PdfPCell title = new PdfPCell(paragraph);
            title.setBorder(Rectangle.NO_BORDER);

            PdfPCell barcodeCell = new PdfPCell(image, true);
            barcodeCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell blank = new PdfPCell();
            blank.setBorder(Rectangle.NO_BORDER);

            Paragraph blankParagraph = new Paragraph("-                                                    -");
            PdfPCell pdfPCellBlank = new PdfPCell(blankParagraph);
            pdfPCellBlank.setBorder(Rectangle.NO_BORDER);

            for (int i = 0; i < barcodeQuantity; i++) {
                /**
                 * This is for barcode pdf title
                 */
                pdfPTable.addCell(title);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(title);

                /**
                 * This is for barcode image
                 */
                pdfPTable.addCell(barcodeCell);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(barcodeCell);

                /**
                 * Blank space after barcode image
                 */
                pdfPTable.addCell(pdfPCellBlank);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(pdfPCellBlank);
            }
            document.add(pdfPTable);
            document.close();
        } catch (DocumentException | FileNotFoundException dex) {
            System.out.println(dex.getMessage());
        }
    }
    public void generateBarcodePdf(boolean fixedRate , Product p) throws UnsupportedEncodingException {
        try {
            String filename = directory + "\\" + barcodeData + ".pdf";
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            Barcode128 code25 = new Barcode128();
            code25.setGenerateChecksum(true);
            code25.setCode(barcodeData);
            code25.setSize(10f);
            code25.setX(1.50f);

            PdfPTable pdfPTable = new PdfPTable(3);
            float[] widths = {0.45f, .10f, .45f};
            pdfPTable.setWidths(widths);
            String codeName = getCodeName(shop.getShopName(), shop.getShopId(), (int) p.getProductBuyRate());
            System.out.println("BarCodeName:" + codeName);

            Image image = code25.createImageWithBarcode(cb, null, null);

            Paragraph paragraph = new Paragraph(codeName);
            paragraph.setSpacingBefore(10.0f);
            PdfPCell title = new PdfPCell(paragraph);
            title.setBorder(Rectangle.NO_BORDER);

            PdfPCell barcodeCell = new PdfPCell(image, true);
            barcodeCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell blank = new PdfPCell();
            blank.setBorder(Rectangle.NO_BORDER);

            Paragraph blankParagraph = new Paragraph("-                                                    -");
            PdfPCell pdfPCellBlank = new PdfPCell(blankParagraph);
            pdfPCellBlank.setBorder(Rectangle.NO_BORDER);

            for (int i = 0; i < barcodeQuantity; i++) {
                /**
                 * This is for barcode pdf title
                 */
                pdfPTable.addCell(title);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(title);

                /**
                 * This is for barcode image
                 */
                pdfPTable.addCell(barcodeCell);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(barcodeCell);

                /**
                 * Blank space after barcode image
                 */
                pdfPTable.addCell(pdfPCellBlank);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(pdfPCellBlank);
            }
            document.add(pdfPTable);
            document.close();
        } catch (DocumentException | FileNotFoundException dex) {
            System.out.println(dex.getMessage());
        }
    }

    private String getCodeName(String shopName, int shopId, int amount) {
        String[] words = shopName.split(" ");

        String output = "";

        for (String word : words) {
            output += word.charAt(0);
        }

        String str = String.format("%d", shopId);
        int strLength = 5 - str.length();
        while (strLength > 0) {
            str = "9" + str;
            strLength--;
        }
        output += str;
        output += String.format("0%06d88", amount);

        return output;

    }
}
