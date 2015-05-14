/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.BarcodeCodabar;
import com.itextpdf.text.pdf.BarcodeDatamatrix;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.BarcodeEANSUPP;
import com.itextpdf.text.pdf.BarcodeInter25;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.BarcodePostnet;
import com.itextpdf.text.pdf.BarcodeQRCode;
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

    private int barcodeQuantity;
    private String barcodeData;
    private double productRate = 150;

    public BarcodePdf(int barcodeQuantity, String barcodeData) {
        this.barcodeQuantity = barcodeQuantity;
        this.barcodeData = barcodeData;
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

    public void generateBarcodePdf() throws UnsupportedEncodingException {
        try {
            String filename = barcodeData + ".pdf";
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
            
            for (int i = 0; i < barcodeQuantity; i++) {
                Image image = code25.createImageWithBarcode(cb, null, null);

                PdfPCell pdfPCell1 = new PdfPCell(image, true);
                pdfPCell1.setBorder(Rectangle.NO_BORDER);

                PdfPCell pdfPCell2 = new PdfPCell();
                pdfPCell2.setBorder(Rectangle.NO_BORDER);

                PdfPCell pdfPCell3 = new PdfPCell(image, true);
                pdfPCell3.setBorder(Rectangle.NO_BORDER);

                pdfPTable.addCell(pdfPCell1);
                pdfPTable.addCell(pdfPCell2);
                pdfPTable.addCell(pdfPCell3);

                pdfPTable.addCell(pdfPCell2);
                pdfPTable.addCell(pdfPCell2);
                pdfPTable.addCell(pdfPCell2);

            }
            document.add(pdfPTable);
            document.close();
        } catch (DocumentException | FileNotFoundException dex) {
            System.out.println(dex.getMessage());
        }

    }
}
