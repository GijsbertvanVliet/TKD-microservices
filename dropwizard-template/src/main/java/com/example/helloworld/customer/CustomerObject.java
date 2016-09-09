package com.example.helloworld.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by gijsbert on 8/12/16.
 */
public class CustomerObject {

    public CustomerObject() {}

    public CustomerObject(String customerId, String customerName, String emailAddress) {
        this.customerId=customerId;
        this.customerName=customerName;
        this.emailAddress=emailAddress;
    }
    private String customerId;
    private String customerName;
    private String emailAddress;

    @JsonProperty("customerId")
    public String getCustomerId() {
        return customerId;
    }

    @JsonProperty("customerName")
    public String getCustomerName() {
        return customerName;
    }

    @JsonProperty("emailAddress")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {this.emailAddress = emailAddress;}

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof CustomerObject))return false;
        CustomerObject otherParsed = (CustomerObject) other;
        return otherParsed.getCustomerId().equals(customerId)
                &&otherParsed.getCustomerName().equals(customerName)
                &&otherParsed.getEmailAddress().equals(emailAddress);
    }
}
