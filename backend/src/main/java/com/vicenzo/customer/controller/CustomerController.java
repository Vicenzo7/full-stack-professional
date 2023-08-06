package com.vicenzo.customer.controller;

import com.vicenzo.customer.model.Customer;
import com.vicenzo.customer.dto.CustomerRegistrationRequest;
import com.vicenzo.customer.dto.CustomerUpdateRequest;
import com.vicenzo.customer.repository.CustomerJDBCDataAccessRepository;
import com.vicenzo.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerJDBCDataAccessRepository customerJDBCDataAccessRepository;


    @GetMapping()
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("{customerId}")
    public Customer getCustomersById(@PathVariable Long customerId) {
        return customerService.getCustomer(customerId);
    }

    @PostMapping()
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        customerService.addCustomer(customerRegistrationRequest);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomersById(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Long customerId,
                               @RequestBody CustomerUpdateRequest updateRequest) {
        customerService.updateCustomer(customerId, updateRequest);
    }
}
