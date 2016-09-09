package com.example.helloworld.customer;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by gijsbert on 8/12/16.
 */
public class RetrieveCustomerTest {

    @Test
    public void testGetCustomer(){
        CustomerObject testCustomer= new CustomerObject("123", "henk harry", "henk@harry.nl");
        CustomerObject realCustomer = RetrieveCustomer.getCustomerByCustomerId("123");
        assertThat(testCustomer).isEqualTo(realCustomer);
    }
}
