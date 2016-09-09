package com.example.helloworld.customer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by gijsbert on 8/12/16.
 */
public class AddNewCustomer {

    public static void addCustomer(CustomerObject customer) {
        Writer output;
        try {
            output = new BufferedWriter(new FileWriter("/home/gijsbert/gitrepo/TKD-Microservices/dropwizard-template/src/main/resources/customers.csv",true));
            String customerData = customer.getCustomerId()+","+customer.getCustomerName()+","+customer.getEmailAddress()+"\n";
            output.append(customerData);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
