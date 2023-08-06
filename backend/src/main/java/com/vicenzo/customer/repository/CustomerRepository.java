package com.vicenzo.customer.repository;

import com.vicenzo.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsCustomerByEmail(String email);

    boolean existsCustomerById(Long id);
}
