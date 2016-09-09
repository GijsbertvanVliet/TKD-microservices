package com.example.helloworld.customer;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;

/**
 * Created by gijsbert on 8/12/16.
 */
public class RetrieveCustomer {

    public static CustomerObject getCustomerByCustomerId(String customerId) {

        List<CustomerObject> customers = getCustomers();
        return selectRealCustomer(customers, CustomerFieldIdentifiers.customerId, customerId);
    }

    public static int getLocationOfCustomerWithId(String customerId) {
        List<CustomerObject> customers = getCustomers();
        int placeOfCustomer = -1;
        for(int placement=0; placement<customers.size(); placement++) {
            if (customers.get(placement).getCustomerId() == customerId) placeOfCustomer = placement;
        }
        return placeOfCustomer;
    }

    public static CustomerObject selectRealCustomer(List<CustomerObject> customers, CustomerFieldIdentifiers fieldIdentifier, String fieldValue) {
        if (customers.isEmpty()) return null;
        else if(customers.size() == 1) {
            if(isCorrectCustomer(customers.get(0), fieldIdentifier, fieldValue)) return customers.get(0);
            else return null;
        }
        else if (isCorrectCustomer(customers.get(0), fieldIdentifier, fieldValue)) return customers.get(0);
        else return selectRealCustomer(customers.subList(1,customers.size()-1), fieldIdentifier, fieldValue);
    }

    public static Boolean isCorrectCustomer(CustomerObject customer, CustomerFieldIdentifiers fieldIdentifiers, String fieldValue) {
        switch(fieldIdentifiers) {
            case customerId:
                return customer.getCustomerId().trim().equals(fieldValue.trim());
            case customerName:
                return customer.getCustomerName().trim().equals(fieldValue.trim());
            default:
                return customer.getCustomerName().trim().equals(fieldValue.trim());
        }
    }

    public static List<CustomerObject> getCustomers() {
        String csvFile = "/home/gijsbert/gitrepo/TKD-Microservices/dropwizard-template/src/main/resources/customers.csv";
        String line = "";
        String cvsSplitBy = ",";
        List<CustomerObject> customers = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            br.readLine();
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] customerFields = line.split(cvsSplitBy);
                CustomerObject customerObject = parseStringToCustomer(customerFields);

                customers.add(customerObject);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return customers;
    }

    private static CustomerObject parseStringToCustomer(String[] customerFields) {
            return new CustomerObject(customerFields[0].trim(), customerFields[1].trim(), customerFields[2].trim());
    }

}
