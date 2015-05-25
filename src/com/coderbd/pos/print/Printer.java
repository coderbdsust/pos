/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.print;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

/**
 *
 * @author Biswajit Debnath
 */
public class Printer {

    public void doPrint(PrintService printService, String fileName) {

        try {
            FileInputStream fis = new FileInputStream(fileName);

            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            pras.add(new Copies(1));

            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            if (printService != null) {
                DocPrintJob job = printService.createPrintJob();

                DocAttributeSet das = new HashDocAttributeSet();
                Doc doc = new SimpleDoc(fis, flavor, das);

                PrintJobWatcher printJobWatcher = new PrintJobWatcher(job);
                job.print(doc, pras);
                printJobWatcher.waitForDone();

                InputStream inputStream = new ByteArrayInputStream("\f".getBytes());
                Doc docff = new SimpleDoc(inputStream, flavor, null);
                DocPrintJob endJob = printService.createPrintJob();
                printJobWatcher = new PrintJobWatcher(endJob);
                endJob.print(docff, null);
            }

        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());

        } catch (PrintException iox) {
            System.out.println(iox.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     *
     * @param printService
     * @param inputData
     *
     */
    public void doPrintRawData(PrintService printService, String inputData) {

        try {
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            pras.add(new Copies(1));

            if (printService != null) {

                InputStream inputStream = new ByteArrayInputStream(inputData.getBytes());
                DocAttributeSet das = new HashDocAttributeSet();
                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                Doc doc = new SimpleDoc(inputStream, flavor, das);
                DocPrintJob job = printService.createPrintJob();

                /**
                 * This portion printing receipt data
                 */
                PrintJobWatcher printJobWatcher;
                printJobWatcher = new PrintJobWatcher(job);
                job.print(doc, pras);
                printJobWatcher.waitForDone();

                /**
                 * This portion for cutting the paper from pos printer
                 */
                DocFlavor cutFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                String ESCi = ((char) 0x1b) + "" + ((char) 0x69) + "";

                ByteArrayInputStream cutStream = new ByteArrayInputStream(ESCi.getBytes());
                Doc cutDoc = new SimpleDoc(cutStream, cutFlavor, null);
                DocPrintJob endJob = printService.createPrintJob();

                printJobWatcher = new PrintJobWatcher(endJob);
                endJob.print(cutDoc, pras);
                printJobWatcher.waitForDone();

            }
        } catch (PrintException pe) {
            System.out.println(pe.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

class PrintJobWatcher {

    boolean done = false;

    PrintJobWatcher(DocPrintJob job) {
        job.addPrintJobListener(new PrintJobAdapter() {

            @Override
            public void printJobCanceled(PrintJobEvent pje) {
                allDone();
            }

            @Override
            public void printJobCompleted(PrintJobEvent pje) {
                allDone();
            }

            @Override
            public void printJobFailed(PrintJobEvent pje) {
                allDone();
            }

            @Override
            public void printJobNoMoreEvents(PrintJobEvent pje) {
                allDone();
            }

            void allDone() {
                synchronized (PrintJobWatcher.this) {
                    done = true;
                    System.out.println("Printing done ...");
                    PrintJobWatcher.this.notify();
                }
            }
        });
    }

    public synchronized void waitForDone() {
        try {
            while (!done) {
                wait();
            }
        } catch (InterruptedException e) {
        }
    }
}
