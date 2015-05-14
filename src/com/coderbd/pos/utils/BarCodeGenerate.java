/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.swing.Icon;
import javax.swing.JPanel;

/**
 *
 * @author Biswajit Debnath
 */
public class BarCodeGenerate {

    public Image getBarcodeImage(String barcode) throws DocumentException {
        Document doc = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(doc, out);
        doc.open();
        PdfContentByte cb = pdfWriter.getDirectContent();
        Barcode128 barcode128 = new Barcode128();
        barcode128.setCode(barcode);
        barcode128.setStartStopText(false);
        Image image = barcode128.createImageWithBarcode(cb, null,
                null);

        return image;
    }

    public JPanel getBarCodePanel(String barcode, JPanel panel) throws DocumentException {

        panel.setBackground(Color.WHITE);

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();

        PdfContentByte cb = writer.getDirectContent();

        float width = PageSize.A4.getWidth();
        float height = PageSize.A4.getHeight() / 2;

        PdfTemplate resultsPanelPdfTemplate = cb.createTemplate(width, height);
        Graphics2D g2d2 = new PdfGraphics2D(resultsPanelPdfTemplate, width, height);
        panel.paint(g2d2);
        g2d2.dispose();
        cb.addTemplate(resultsPanelPdfTemplate, 0, 0);
        document.close();
        return panel;
    }
}
