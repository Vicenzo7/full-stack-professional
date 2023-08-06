package com.vicenzo.customer.controller;

import com.vicenzo.customer.model.Customer;
import com.vicenzo.customer.dto.CustomerRegistrationRequest;
import com.vicenzo.customer.dto.CustomerUpdateRequest;
import com.vicenzo.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

    private final CustomerService customerService;


    @GetMapping("customers")
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("customers/{customerId}")
    public Customer getCustomersById(@PathVariable Long customerId) {
        return customerService.getCustomer(customerId);
    }

    @PostMapping("customers")
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        customerService.addCustomer(customerRegistrationRequest);
    }

    @DeleteMapping("customers/{customerId}")
    public void deleteCustomersById(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);
    }

    @PutMapping("customers/{customerId}")
    public void updateCustomer(@PathVariable("customerId") Long customerId,
                               @RequestBody CustomerUpdateRequest updateRequest) {
        customerService.updateCustomer(customerId, updateRequest);
    }
}
