package org.example.services.impl;

import org.example.model.Buyer;
import org.example.model.Order;
import org.example.repository.impl.BuyerRepositoryImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

class BuyerServiceImplTest {


    @Test
    void getAll() {
    }

    @Test
    void get() {
    }

    @Mock
    private BuyerRepositoryImpl buyerRepository = Mockito.mock(BuyerRepositoryImpl.class);

    @InjectMocks
    private BuyerServiceImpl buyerService = Mockito.mock(BuyerServiceImpl.class);

    @Test
    void save() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(1, 1, new ArrayList<>());
        orders.add(order);
        Buyer buyer = new Buyer(1, "Иван", orders);

        when(buyerRepository.save(Mockito.any(Buyer.class))).thenReturn(buyer);
        Buyer saveBuyer = buyerService.save(buyer);
        Assert.assertEquals(buyer, saveBuyer);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}