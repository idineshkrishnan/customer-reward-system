package com.application.crs.repository;

import com.application.crs.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    @Query(value = "SELECT c FROM Customer c")
    Page<Customer> findAllCustomers(Pageable pageable);
}
