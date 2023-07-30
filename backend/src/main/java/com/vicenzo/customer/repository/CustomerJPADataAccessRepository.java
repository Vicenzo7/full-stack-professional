package com.vicenzo.customer.repository;

import com.vicenzo.customer.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerJPADataAccessRepository implements CustomerDao {

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer customerId) {
        return customerRepository.findById(customerId);
    }
}
