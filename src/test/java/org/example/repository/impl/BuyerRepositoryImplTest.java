package org.example.repository.impl;

import db.BaseTest;
import org.example.model.Buyer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BuyerRepositoryImplTest extends BaseTest {

    @Test
    void getTest() throws IOException {
        Buyer saveBuyer = buyerRepository.save(getBuyerFromJsonFile());
        Buyer getBuyer = buyerRepository.get(saveBuyer.getId());
        assertEquals(getBuyer, getBuyerFromJsonFile());
    }

    @Test
    void getAll() {
        int size = 5;
        List<Buyer> buyers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Buyer buyer = new Buyer(i, "SomeName" + i, new ArrayList<>());
            buyers.add(buyer);
            buyerRepository.save(buyer);
        }
        List<Buyer> getAllBuyers = buyerRepository.getAll();
        assertEquals(getAllBuyers, buyers);
    }

    @Test
    void save() throws IOException {
        Buyer saveBuyer = buyerRepository.save(getBuyerFromJsonFile());
        Buyer getBuyer = buyerRepository.get(saveBuyer.getId());
        assertEquals(getBuyer, getBuyerFromJsonFile());
    }

    @Test
    void update() throws IOException {
        Buyer saveBuyer = buyerRepository.save(getBuyerFromJsonFile());
        saveBuyer.setName("NewName");
        buyerRepository.update(saveBuyer);
        Buyer getUpdateBuyer = buyerRepository.get(saveBuyer.getId());
        assertEquals(getUpdateBuyer, saveBuyer);
    }

    @Test
    void delete() throws IOException {
        Buyer saveBuyer = buyerRepository.save(getBuyerFromJsonFile());
        assertEquals(buyerRepository.get(saveBuyer.getId()), saveBuyer);
        buyerRepository.delete(saveBuyer.getId());
        assertNull(buyerRepository.get(saveBuyer.getId()));
    }
}