package com.vicenzo.customer.repository;

import com.vicenzo.customer.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers();

    Optional<Customer> selectCustomerById(Long customerId);

    void insertCustomer(Customer customer);

    boolean existsCustomerWithEmail(String email);

    boolean existsCustomerWithId(Long id);

    void deleteCustomerCustomerById(Long id);

    void updateCustomer(Customer customer);
}
