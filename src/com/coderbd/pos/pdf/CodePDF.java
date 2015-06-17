/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.pdf;

import com.coderbd.pos.entity.Product;
import com.coderbd.pos.utils.DirectoryCreator;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import java.util.Date;

/**
 *
 * @author Biswajit Debnath
 */
public class CodePDF {

    private DirectoryCreator directoryCreator;
    private String directory = "GenratedBarcode";
    private Product product;
    private int barcodeQuantity;
    boolean isFixedRated;

    public CodePDF(boolean isFixedRated, Product product) {

        this.isFixedRated = isFixedRated;
        this.product = product;
        directoryCreator = new DirectoryCreator();
        directory = product.getShop().getShopName() + "\\" + directory;
        directoryCreator.makeDirs(directory);
    }

    public boolean genCodeVer2Pdf() throws UnsupportedEncodingException {

        if (isFixedRated == true) {
            barcodeQuantity = 12;
        } else {
            barcodeQuantity = 15;
        }

        try {
            String filename = "";

            if (isFixedRated) {
                filename = directory + "\\" + product.getProductBarcode() + "_FIXED_TK" + ".pdf";
            } else {
                filename = directory + "\\" + product.getProductBarcode() + ".pdf";
            }

            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            Barcode128 code25 = new Barcode128();
            code25.setGenerateChecksum(true);
            code25.setCode(product.getProductBarcode());
            code25.setSize(10f);
            code25.setX(1.50f);

            PdfPTable pdfPTable = new PdfPTable(7);
            pdfPTable.setWidthPercentage(98);
            float[] widths = {0.22f, 0.04f, 0.22f, 0.04f, 0.22f, 0.04f, 0.22f};
            pdfPTable.setWidths(widths);
            String codeName = getCodeName(product.getShop().getShopName(),
                    product.getShop().getShopId(), (int) product.getProductBuyRate());

            System.out.println("BarCodeName:" + codeName);

            Image image = code25.createImageWithBarcode(cb, null, null);

            Font titleArialFont
                    = FontFactory.getFont("Arial", BaseFont.WINANSI, BaseFont.EMBEDDED, 8, Font.NORMAL);
            Font priceFont
                    = FontFactory.getFont("Arial", BaseFont.WINANSI, BaseFont.EMBEDDED, 8, Font.BOLD);

            Paragraph paragraph = new Paragraph(codeName, titleArialFont);
            paragraph.setSpacingBefore(10.0f);
            PdfPCell title = new PdfPCell(paragraph);
            title.setBorder(Rectangle.NO_BORDER);

            Paragraph fixedTKParagraph = new Paragraph("FIXED TK: " + product.getProductSellRate(), priceFont);
            PdfPCell fixedRateCell = new PdfPCell(fixedTKParagraph);
            fixedRateCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell barcodeCell = new PdfPCell(image, true);
            barcodeCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell blank = new PdfPCell();
            blank.setBorder(Rectangle.NO_BORDER);

            Paragraph blankParagraph = new Paragraph("-                           -");
            PdfPCell pdfPCellBlank = new PdfPCell(blankParagraph);
            pdfPCellBlank.setBorder(Rectangle.NO_BORDER);

            for (int i = 0; i < barcodeQuantity; i++) {
                /**
                 * This is for barcode pdf title
                 */
                pdfPTable.addCell(title);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(title);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(title);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(title);

                /**
                 * This is for barcode image
                 */
                pdfPTable.addCell(barcodeCell);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(barcodeCell);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(barcodeCell);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(barcodeCell);
                /**
                 *
                 */
                if (isFixedRated == true) {
                    pdfPTable.addCell(fixedRateCell);
                    pdfPTable.addCell(blank);
                    pdfPTable.addCell(fixedRateCell);
                    pdfPTable.addCell(blank);
                    pdfPTable.addCell(fixedRateCell);
                    pdfPTable.addCell(blank);
                    pdfPTable.addCell(fixedRateCell);
                }
                /**
                 * Blank space after barcode image
                 */
                pdfPTable.addCell(pdfPCellBlank);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(pdfPCellBlank);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(pdfPCellBlank);
                pdfPTable.addCell(blank);
                pdfPTable.addCell(pdfPCellBlank);
            }
            document.add(pdfPTable);
            document.add(new Paragraph("Shop Name: " + product.getShop().getShopName() + ", Name:" + product.getProductName() 
                    + "\n,Qty: " + product.getProductStock() + ", " + new Date().toString(), titleArialFont));
            document.close();
            return true;
        } catch (DocumentException | FileNotFoundException dex) {
            System.out.println(dex.getMessage());
            return false;
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
