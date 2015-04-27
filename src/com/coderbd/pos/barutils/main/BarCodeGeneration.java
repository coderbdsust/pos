/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.barutils.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

/**
 *
 * @author Biswajit Debnath
 */
public class BarCodeGeneration {

    Code39Bean bean;
    int dpi;

    public BarCodeGeneration(int dpi) {
        this.dpi = dpi;
        bean = new Code39Bean();
        bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));
        bean.setWideFactor(20);
        bean.doQuietZone(false);
    }

    public void generateBarCodePNG(String barCodeData, String fileName) {

        File outputFile = null;
        OutputStream out = null;

        try {
            outputFile = new File(fileName + ".png");
            out = new FileOutputStream(outputFile);

            //Set up the canvas provider for monochrome PNG output 
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                    out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

            //Generate the barcode
            bean.generateBarcode(canvas, barCodeData);

            //Signal end of generation
            canvas.finish();

        } catch (IOException iox) {
            System.out.println("Canvas not finished");

        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException iox) {
                System.out.println("Outputstream not closed!");
            }
        }

    }

}
