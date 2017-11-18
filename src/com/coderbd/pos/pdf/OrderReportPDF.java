/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.pdf;

import com.coderbd.pos.entity.Product;
import com.coderbd.pos.entity.SupplierOrderProduct;
import com.coderbd.pos.entity.pojo.SupplierOrderProductReport;
import com.coderbd.pos.entity.pojo.SupplierOrderReport;
import com.coderbd.pos.utils.DirectoryCreator;
import com.coderbd.pos.utils.IDBuilder;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Biswajit Debnath
 */
public class OrderReportPDF {

    private DirectoryCreator directoryCreator;
    private String directory = "OrderProductSellReports";
    private SupplierOrderReport sor;

    public OrderReportPDF(SupplierOrderReport sor) {

        this.sor = sor;
        directoryCreator = new DirectoryCreator();
        directory = directory + "\\" + sor.getSupplier().getSupplierName();
        directoryCreator.makeDirs(directory);
    }

    public boolean genPdf() throws UnsupportedEncodingException {
        List<SupplierOrderProductReport> soprs = sor.getSupplierOrderProductReports();
        IDBuilder idBuilder = new IDBuilder();
        Font innerFont
                = FontFactory.getFont("Arial", BaseFont.WINANSI, BaseFont.EMBEDDED, 8, Font.NORMAL);
        Font headerFont
                = FontFactory.getFont("Arial", BaseFont.WINANSI, BaseFont.EMBEDDED, 8, Font.BOLD);
        try {
            String timestamp = idBuilder.getUniqueTimeStampID();
            String filename = sor.getSupplier().getSupplierName() + "_O" + sor.getSupplierOrderId() + "_" + timestamp + ".pdf";
            String filePath = directory + "\\" + filename;

            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            PdfPTable pdfPTable = new PdfPTable(5);

            pdfPTable.setWidthPercentage(90);
            float[] widths = {0.40f, 0.13f, 0.15f, 0.15f, 0.17f};
            pdfPTable.addCell(new Paragraph("Product Name", headerFont));
            pdfPTable.addCell(new Paragraph("Buy Rate", headerFont));
            pdfPTable.addCell(new Paragraph("Primary Qty", headerFont));
            pdfPTable.addCell(new Paragraph("Unsold Qty", headerFont));
            pdfPTable.addCell(new Paragraph("Unsold Amount", headerFont));

            pdfPTable.setWidths(widths);
            for (SupplierOrderProductReport sopr : soprs) {

                SupplierOrderProduct sop = sopr.getSupplierOrderProduct();
                String name = sop.getSupplierProductName();
                Double rate = sop.getSupplierRate();
                Integer pQty = sop.getSupplierProductQuantity();
                Integer unQty = sopr.getUnSoldProductQuantity();
                Double unAmount = sopr.getUnSoldProductAmount();

                Paragraph nameParag = new Paragraph(name, innerFont);
                Paragraph buyRateParag = new Paragraph(rate.toString(), innerFont);
                Paragraph primaryQuantityParag = new Paragraph(pQty.toString(), innerFont);
                Paragraph unsoldQuantityParag = new Paragraph(unQty.toString(), innerFont);
                Paragraph unsoldAmountParag = new Paragraph(unAmount.toString(), innerFont);

                PdfPCell nameCell = new PdfPCell(nameParag);
                PdfPCell buyRateCell = new PdfPCell(buyRateParag);
                PdfPCell primaryQuantityCell = new PdfPCell(primaryQuantityParag);
                PdfPCell unsoldQuantityCell = new PdfPCell(unsoldQuantityParag);
                PdfPCell unsoldAmountCell = new PdfPCell(unsoldAmountParag);

                pdfPTable.addCell(nameCell);
                pdfPTable.addCell(buyRateCell);
                pdfPTable.addCell(primaryQuantityCell);
                pdfPTable.addCell(unsoldQuantityCell);
                pdfPTable.addCell(unsoldAmountCell);
            }
            document.add(getHeader());

            document.add(pdfPTable);
            document.add(getFooter());
            document.add(new Paragraph("            Report Generated: " + new Date().toString(), innerFont));
            document.close();
            return true;
        } catch (DocumentException | FileNotFoundException dex) {
            System.out.println(dex.getMessage());
            return false;
        }
    }

    public PdfPTable getFooter() throws DocumentException {
        Font innerFont
                = FontFactory.getFont("Arial", BaseFont.WINANSI, BaseFont.EMBEDDED, 8, Font.NORMAL);
        Font headerFont
                = FontFactory.getFont("Arial", BaseFont.WINANSI, BaseFont.EMBEDDED, 8, Font.BOLD);

        PdfPTable pdfPTableFooter = new PdfPTable(2);
        pdfPTableFooter.setWidthPercentage(90);
        float[] widthsFooter = {0.80f, 0.20f};
        pdfPTableFooter.setWidths(widthsFooter);

        Paragraph totalSoldPara = new Paragraph("Total Sold: " + sor.getOrderTotalSoldAmount().toString(), headerFont);
        PdfPCell totalSoldCell = new PdfPCell(totalSoldPara);
        totalSoldCell.setBorder(Rectangle.NO_BORDER);

        Paragraph totalUnSoldPara = new Paragraph("Total UnSold: " + sor.getOrderTotalUnsoldAmount().toString(), headerFont);
        PdfPCell totalUnSoldCell = new PdfPCell(totalUnSoldPara);
        totalUnSoldCell.setBorder(Rectangle.NO_BORDER);

        Paragraph blankPara = new Paragraph("");
        PdfPCell blankCell = new PdfPCell(blankPara);
        blankCell.setBorder(Rectangle.NO_BORDER);

        Double bill = sor.getTotalBill();
        Double paid = sor.getTotalPaid();
        Double due = bill - paid;

        Paragraph billPara = new Paragraph("BILL: " + bill.toString(), headerFont);
        PdfPCell billCell = new PdfPCell(billPara);
        billCell.setBorder(Rectangle.NO_BORDER);

        Paragraph paidPara = new Paragraph("PAID: " + paid.toString(), headerFont);
        PdfPCell paidCell = new PdfPCell(paidPara);
        paidCell.setBorder(Rectangle.NO_BORDER);

        Paragraph duePara = new Paragraph("DUE: " + due.toString(), headerFont);
        PdfPCell dueCell = new PdfPCell(duePara);
        dueCell.setBorder(Rectangle.NO_BORDER);

        pdfPTableFooter.addCell(totalSoldCell);
        pdfPTableFooter.addCell(billCell);
        pdfPTableFooter.addCell(totalUnSoldCell);
        pdfPTableFooter.addCell(paidCell);
        pdfPTableFooter.addCell(blankCell);
        pdfPTableFooter.addCell(dueCell);

        return pdfPTableFooter;
    }

    public PdfPTable getHeader() throws DocumentException {
        Font innerFont
                = FontFactory.getFont("Arial", BaseFont.WINANSI, BaseFont.EMBEDDED, 8, Font.NORMAL);
        Font headerFont
                = FontFactory.getFont("Arial", BaseFont.WINANSI, BaseFont.EMBEDDED, 12, Font.BOLD);

        PdfPTable tableHeader = new PdfPTable(1);
        tableHeader.setWidthPercentage(90);

        Paragraph supplierNamePara = new Paragraph(sor.getSupplier().getSupplierName(), headerFont);
        Paragraph addressPara = new Paragraph(sor.getSupplier().getSupplierAddress(), innerFont);
        Paragraph mobilePara = new Paragraph("Mobile: " + sor.getSupplier().getSupplierMobile(), innerFont);
        Paragraph orderPara = new Paragraph("Order: " + sor.getSupplierOrderId() + ", Time: " + sor.getOrderTime(), innerFont);

        PdfPCell supplierNameCell = new PdfPCell();
        supplierNameCell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        supplierNameCell.setBorder(Rectangle.NO_BORDER);
        supplierNameCell.addElement(supplierNamePara);

        PdfPCell addressCell = new PdfPCell();
        addressCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        addressCell.setBorder(Rectangle.NO_BORDER);
        addressCell.addElement(addressPara);

        PdfPCell mobileCell = new PdfPCell(mobilePara);
        mobileCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        mobileCell.setBorder(Rectangle.NO_BORDER);
        mobileCell.addElement(mobilePara);

        PdfPCell orderCell = new PdfPCell(orderPara);
        orderCell.setBorder(Rectangle.NO_BORDER);

        PdfPCell bCell = new PdfPCell(new Paragraph(" "));
        bCell.setBorder(Rectangle.NO_BORDER);

        tableHeader.addCell(supplierNameCell);
        tableHeader.addCell(addressCell);
        tableHeader.addCell(mobileCell);
        tableHeader.addCell(orderCell);
        tableHeader.addCell(bCell);

        return tableHeader;
    }

}
