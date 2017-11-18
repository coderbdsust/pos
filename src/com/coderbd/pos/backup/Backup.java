/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.backup;

import com.coderbd.pos.constraints.Const;
import com.coderbd.pos.utils.IDBuilder;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 *
 * @author Biswajit Debnath
 */
public class Backup {

    private int STREAM_BUFFER = 512000;
    private IDBuilder builder;
    private String exec = "";
    private String saveFile = "";

    public Backup() {
        builder = new IDBuilder();
        exec = System.getenv("DB_SERVER_PATH") + "\\mysqldump";
        System.out.println(exec);
    }

    public String backUpData() {

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Choose File Save Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        String fileName = "";
        String data = "";

        try {
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                String uniqueFile = builder.getUniqueTimeStampID();
                saveFile = chooser.getSelectedFile().toString();
                System.out.println("save:" + saveFile);
                fileName = chooser.getSelectedFile() + "\\" + uniqueFile + ".sql";
                File file = new File(fileName);

                try (FileOutputStream fileOutStream = new FileOutputStream(file)) {
                    data = getDatabaseBackupData(exec);
                    fileOutStream.write(data.getBytes());
                }
                return file.getName();
            } else {
                System.out.println("No Path Selected!");
                return null;
            }
        } catch (HeadlessException | IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private String getDatabaseBackupData(String execline) throws IOException {
        StringBuilder dumpdata = new StringBuilder();
        try {

            String command[] = new String[]{execline,
                "--host=" + Const.DB_HOST,
                "--port=" + Const.DB_PORT,
                "--user=" + Const.DB_USERNAME,
                "--password=" + Const.DB_PASSWORD,
                "--compact",
                "--complete-insert",
                "--extended-insert",
                "--skip-comments",
                "--skip-triggers",
                Const.DB_SCHEMA};
            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();
            InputStream in = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            int count;
            char[] cbuf = new char[STREAM_BUFFER];

            while ((count = br.read(cbuf, 0, STREAM_BUFFER)) != -1) {
                dumpdata.append(cbuf, 0, count);
            }
            br.close();
            in.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        return dumpdata.toString();
    }

}
