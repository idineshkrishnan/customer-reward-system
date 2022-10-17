package com.application.crs.controller;

import com.application.crs.dto.Reward;
import com.application.crs.model.Customer;
import com.application.crs.model.Purchase;
import com.application.crs.service.CustomerService;
import com.application.crs.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/customers"})
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Page<Customer>> findAllCustomers(@RequestParam(value = "page", required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER) Integer page,
                                                           @RequestParam(value = "size", required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(customerService.findAllCustomers(page, size));
    }

    @GetMapping(value = {"/{customerId}"})
    public ResponseEntity<Customer> findCustomerById(@PathVariable(value = "customerId") Long customerId) {
        return ResponseEntity.ok(customerService.findCustomerById(customerId));
    }

    @GetMapping(value = {"/{customerId}/purchases"})
    public ResponseEntity<Page<Purchase>> findAllPurchases(@PathVariable(value = "customerId") Long customerId,
                                                           @RequestParam(value = "page", required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER) Integer page,
                                                           @RequestParam(value = "size", required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(customerService.findAllPurchases(customerId, page, size));
    }

    @GetMapping(value = {"/{customerId}/rewards"})
    public ResponseEntity<Reward> findRewards(@PathVariable(value = "customerId") Long customerId) {
        return ResponseEntity.ok(customerService.findRewards(customerId));
    }
}
