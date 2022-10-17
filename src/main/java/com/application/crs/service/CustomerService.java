package com.application.crs.service;

import com.application.crs.dto.Reward;
import com.application.crs.error.InvalidInputException;
import com.application.crs.error.ResourceNotFoundException;
import com.application.crs.model.Customer;
import com.application.crs.model.Purchase;
import com.application.crs.repository.CustomerRepository;
import com.application.crs.repository.PurchaseRepository;
import com.application.crs.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    @Value("${app.reward.minimum.eligible.amount}")
    private Integer REWARD_MINIMUM_ELIGIBLE_AMOUNT;

    @Value("${app.reward.minimum.eligible.point}")
    private Integer REWARD_MINIMUM_ELIGIBLE_POINT;

    @Value("${app.reward.maximum.eligible.amount}")
    private Integer REWARD_MAXIMUM_ELIGIBLE_AMOUNT;

    @Value("${app.reward.maximum.eligible.point}")
    private Integer REWARD_MAXIMUM_ELIGIBLE_POINT;

    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;

    public CustomerService(CustomerRepository customerRepository, PurchaseRepository purchaseRepository) {
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public Page<Customer> findAllCustomers(Integer page, Integer size) {
        return customerRepository.findAllCustomers(PageRequest.of(page, size));
    }

    public Customer findCustomerById(Long customerId) {
        if(customerId <= 0) {
            throw new InvalidInputException("Invalid Customer ID was given!");
        }
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found!"));
    }

    public Page<Purchase> findAllPurchases(Long customerId, Integer page, Integer size) {
        if(customerId <= 0 || page < 0 || size <= 0) {
            throw new InvalidInputException("Invalid Customer ID or Page data was given!");
        }
        return purchaseRepository.findAllPurchases(customerId, PageRequest.of(page, size));
    }

    public Reward findRewards(Long customerId) {
        if(customerId <= 0) {
            throw new InvalidInputException("Invalid Customer ID was given!");
        }
        final Map<Integer, Double> totalPurchaseByMonth = new HashMap<>();
        final LocalDate endDate = LocalDate.now();
        final LocalDate startDate = endDate.minusMonths(3);
        final List<Purchase> purchases = purchaseRepository.findAllPurchases(customerId, startDate, endDate);
        if(purchases != null && !purchases.isEmpty()) {
            for(Purchase purchase: purchases) {
                int month = purchase.getPurchaseDate().getMonthValue();
                double amounts;
                if(totalPurchaseByMonth.containsKey(month)) {
                    amounts = totalPurchaseByMonth.get(month) + purchase.getAmount();
                } else {
                    amounts = purchase.getAmount();
                }
                totalPurchaseByMonth.put(month, amounts);
            }
        }
        return calculateRewards(totalPurchaseByMonth);
    }

    public Reward calculateRewards(Map<Integer, Double> totalPurchaseByMonth) {
        Reward reward = new Reward();
        if(!totalPurchaseByMonth.isEmpty()) {
            totalPurchaseByMonth.forEach((k, v) -> {
                Integer rewards = getReward(v);
                reward.getRewardsByMonth().put(ApplicationUtils.getMonth(k), rewards);
                reward.setTotalReward(reward.getTotalReward() + rewards);
            });
        }
        return reward;
    }

    public Integer getReward(Double amount) {
        if(amount != null) {
            int reward = 0;
            if(amount > REWARD_MINIMUM_ELIGIBLE_AMOUNT) {
                reward += REWARD_MINIMUM_ELIGIBLE_AMOUNT * REWARD_MINIMUM_ELIGIBLE_POINT;
            }
            if(amount > REWARD_MAXIMUM_ELIGIBLE_AMOUNT) {
                double difference = amount - REWARD_MAXIMUM_ELIGIBLE_AMOUNT;
                reward += (int) difference * REWARD_MAXIMUM_ELIGIBLE_POINT;
            }
            return reward;
        }
        return 0;
    }
}
