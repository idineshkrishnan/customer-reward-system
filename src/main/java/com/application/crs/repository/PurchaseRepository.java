package com.application.crs.repository;

import com.application.crs.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, Long> {

    @Query(value = "SELECT p FROM Purchase p WHERE p.customerId = ?1")
    Page<Purchase> findAllPurchases(Long customerId, Pageable pageable);

    @Query(value = "SELECT p FROM Purchase p WHERE p.customerId = :customerId AND p.purchaseDate BETWEEN :startDate AND :endDate")
    List<Purchase> findAllPurchases(@Param("customerId") Long customerId,
                                    @Param("startDate") LocalDate from,
                                    @Param("endDate") LocalDate to);
}
