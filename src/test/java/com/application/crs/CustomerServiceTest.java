package com.application.crs;

import com.application.crs.dto.Reward;
import com.application.crs.error.InvalidInputException;
import com.application.crs.model.Customer;
import com.application.crs.model.Purchase;
import com.application.crs.repository.CustomerRepository;
import com.application.crs.repository.PurchaseRepository;
import com.application.crs.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    private CustomerService service;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @BeforeEach
    public void setup() {
        service = new CustomerService(customerRepository, purchaseRepository);
    }

    @Test
    public void findCustomers() {
        Page<Customer> customers = Mockito.mock(Page.class);
        Mockito.when(customerRepository.findAllCustomers(Mockito.any())).thenReturn(customers);
        Page<Customer> result = service.findAllCustomers(0, 10);
        Assert.notNull(result, "Not Null");
    }

    @Test
    public void findCustomerById() {
        Optional<Customer> customer = Optional.of(new Customer());
        Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(customer);
        Customer result = service.findCustomerById(10000L);
        Assert.notNull(result, "Verified");
    }

    @Test
    public void findCustomerByIdException() {
        Optional<Customer> customer = Optional.of(new Customer());
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            service.findCustomerById(-1000L);
        });
        Assert.isTrue(exception.getMessage().equals("Invalid Customer ID was given!"), "Verified");
    }

    @Test
    public void findAllPurchases() {
        Page<Purchase> purchases = Mockito.mock(Page.class);
        Mockito.when(purchaseRepository.findAllPurchases(Mockito.any(), Mockito.any()))
                .thenReturn(purchases);
        Page<Purchase> result = service.findAllPurchases(10000L, 0, 10);
        Assert.notNull(result, "Verified");
    }

    @Test
    public void findAllPurchasesException() {
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            service.findAllPurchases(-100L, 0, 10);
        });
        Assert.notNull(exception.getMessage().equals("Invalid Customer ID or Page data was given!"), "Verified");
    }

    @Test
    public void findRewards() {

        ReflectionTestUtils.setField(service, "REWARD_MINIMUM_ELIGIBLE_AMOUNT", 50);
        ReflectionTestUtils.setField(service, "REWARD_MINIMUM_ELIGIBLE_POINT", 1);
        ReflectionTestUtils.setField(service, "REWARD_MAXIMUM_ELIGIBLE_AMOUNT", 100);
        ReflectionTestUtils.setField(service, "REWARD_MAXIMUM_ELIGIBLE_POINT", 2);

        List<Purchase> purchases = new ArrayList<>();
        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setAmount(120D);
        purchases.add(purchase);
        Mockito.when(purchaseRepository.findAllPurchases(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(purchases);
        Reward result = service.findRewards(10000L);
        Assert.notNull(result, "Verified");
    }
}
