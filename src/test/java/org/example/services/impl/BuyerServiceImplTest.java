package org.example.services.impl;

import org.example.model.Buyer;
import org.example.model.Order;
import org.example.repository.impl.BuyerRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuyerServiceImplTest {
    @Mock
    private BuyerRepositoryImpl buyerRepository;

    @InjectMocks
    private BuyerServiceImpl buyerService;

    public static Buyer getTemplateBuyer(int id) {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(id, id, new ArrayList<>());
        orders.add(order);
        return new Buyer(id, "Name" + id, orders);
    }

    public static List<Buyer> buyerList(int size) {
        List<Buyer> buyerList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            buyerList.add(getTemplateBuyer(i));
        }
        return buyerList;
    }

    @Test
    void getAll() {
        when(buyerRepository.getAll()).thenReturn(buyerList(5));
        List<Buyer> getAllBuyer = buyerService.getAll();
        Assertions.assertEquals(getAllBuyer, buyerList(5));
    }

    @Test
    void get() {
        when(buyerRepository.get(Mockito.anyInt())).thenReturn(getTemplateBuyer(1));
        Buyer getBuyer = buyerService.get(1);
        Assertions.assertEquals(getBuyer, getTemplateBuyer(1));
    }

    @Test
    void save() {
        when(buyerRepository.save(Mockito.any(Buyer.class))).thenReturn(getTemplateBuyer(1));
        Buyer saveBuyer = buyerService.save(getTemplateBuyer(1));
        Assertions.assertEquals(saveBuyer, getTemplateBuyer(1));
    }

    @Test
    void update() {
        buyerService.update(getTemplateBuyer(1));
        Mockito.verify(buyerRepository).update(getTemplateBuyer(1));
    }

    @Test
    void delete() {
        buyerService.delete(1);
        Mockito.verify(buyerRepository).delete(1);
    }
}