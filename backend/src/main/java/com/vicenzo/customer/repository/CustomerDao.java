package com.vicenzo.customer.repository;

import com.vicenzo.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers();

    Optional<Customer> selectCustomerById(Integer customerId);
}
