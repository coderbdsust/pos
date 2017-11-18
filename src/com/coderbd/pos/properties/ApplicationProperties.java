package com.coderbd.pos.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author biswajit
 */
public class ApplicationProperties {

    private static Properties properties = new Properties();
    private static String profile = "dev";
    public final static String DB_DRIVER_URL="datasource.url";
    public static final String DB_USERNAME="datasource.username";
    public static final String DB_PASSWORD="datasource.password";
    public static final String DB_DRIVER_CLASS_NAME="datasource.driver-class-name";

    static {
        InputStream input = null;
        try {
            String fileName="application.properties";
            input = new FileInputStream(fileName);
            
            if(input==null) {
                System.out.println("File loading error");
            }
            properties.load(input);
            
            String profile = properties.getProperty("profile");
            if (profile != null && profile.equals("pro")) {
                ApplicationProperties.profile = "pro";
            } else if (profile != null && profile.equals("test")) {
                ApplicationProperties.profile = "test";
            }

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getValue(String key) {
        Object obj= properties.getProperty(profile+"."+key);
        return (String) obj;
    }
    
    public static void main(String[] args){
        ApplicationProperties app = new ApplicationProperties();
        System.out.println(app.getValue(ApplicationProperties.DB_DRIVER_CLASS_NAME));
        System.out.println(app.getValue(ApplicationProperties.DB_USERNAME));
        System.out.println(app.getValue(ApplicationProperties.DB_PASSWORD));
        System.out.println(app.getValue(ApplicationProperties.DB_DRIVER_URL));
        
    }
}
