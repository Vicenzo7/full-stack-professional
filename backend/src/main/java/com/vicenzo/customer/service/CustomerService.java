package com.vicenzo.customer.service;

import com.vicenzo.customer.Customer;
import com.vicenzo.customer.repository.CustomerDao;
import com.vicenzo.exception.ResourceNotFound;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {

    @Qualifier("jpa")
    private final CustomerDao customerDao;

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer customerId) {
        return customerDao.selectCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFound("customer with id [%s] not found".formatted(customerId)));
    }
}

