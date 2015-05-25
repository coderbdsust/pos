/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.print;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 *
 * @author Biswajit Debnath
 */
public class PrinterLookUp {

    private PrintService[] services;

    public PrinterLookUp() {
        initPrinterServices();
    }

    public PrintService[] initPrinterServices() {
        DocFlavor myFormat = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        services = PrintServiceLookup.lookupPrintServices(myFormat, aset);
        return services;
    }

    public String[] getPrinterNames() {
        String[] printerName = new String[services.length];
        int index = 0;
        for (PrintService service : services) {
            printerName[index++] = service.getName();
        }
        return printerName;
    }

    public PrintService getPrintService(int index) {
        return services[index];
    }

    public PrintService getPrintService(String printerName) {

        if (printerName == null) {
            return null;
        }

        for (PrintService service : services) {
            if (service.getName().equals(printerName)) {
                return service;
            }
        }

        return null;
    }

}
