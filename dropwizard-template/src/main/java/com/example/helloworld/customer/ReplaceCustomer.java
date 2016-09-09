package com.example.helloworld.customer;

import java.io.*;

/**
 * Created by gijsbert on 8/12/16.
 */
public class ReplaceCustomer {

    public static void replaceCustomer(int placeOfOldCustomer, CustomerObject customer) {

        int currentLine = 0;
        try {
            String line = "";
            File tmpFile = File.createTempFile("/home/gijsbert/gitrepo/TKD-Microservices/dropwizard-template/src/main/resources/tmp.csv","");
            String csvFile = "/home/gijsbert/gitrepo/TKD-Microservices/dropwizard-template/src/main/resources/customers.csv";
            BufferedReader reader = getReader(csvFile);
            BufferedWriter writer = getWriter(tmpFile);
            while ((line = reader.readLine()) != null) {
                if (currentLine != placeOfOldCustomer) writer.write(line);
                currentLine++;
            }
            String customerData = customer.getCustomerId()+","+customer.getCustomerName()+","+customer.getEmailAddress()+"\n";
            writer.write(customerData);
            File oldFile = new File(csvFile);
            if (oldFile.delete())
                tmpFile.renameTo(oldFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static BufferedReader getReader(String csvFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            return br;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedWriter getWriter(File tmp) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(tmp));
            return bw;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
