package com.vicenzo.customer.repository;

import com.vicenzo.customer.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessRepository implements CustomerDao {
    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer alex = new Customer(1L, "Alex", "alex@gmail.com", 21);
        Customer jamila = new Customer(2L, "Jamila", "jamila@gmail.com", 21);

        customers.add(alex);
        customers.add(jamila);

    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Long customerId) {
        return customers.stream()
                .filter(c -> c.getId().equals(customerId))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsPersonWithId(Long id) {
        return customers.stream()
                .anyMatch(c -> c.getId().equals(id));

    }

    @Override
    public void deleteCustomerCustomerById(Long id) {
        customers.stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst()
                .ifPresent(customers::remove);

    }

    @Override
    public void updateCustomer(Customer customer) {
        customers.add(customer);
    }
}
