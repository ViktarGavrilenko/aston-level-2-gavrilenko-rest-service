package org.example.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import db.BaseTest;
import db.DBTest;
import org.example.model.Buyer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.utils.StreamUtils.getTextFromInputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BuyerRepositoryImplTest extends BaseTest {

    @Test
    void shouldGetCustomers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = getTextFromInputStream(DBTest.class.getClassLoader().getResourceAsStream("buyer.json"));
        Buyer buyer = mapper.readValue(json, Buyer.class);
        List<Buyer> buyers = buyerRepository.getAll();
        assertEquals(0, buyers.size());
        buyerRepository.save(buyer);
        buyers = buyerRepository.getAll();
        assertEquals(1, buyers.size());
    }

    @Test
    void getTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = getTextFromInputStream(DBTest.class.getClassLoader().getResourceAsStream("buyer.json"));
        Buyer buyer = mapper.readValue(json, Buyer.class);
        buyerRepository.save(buyer);
        Buyer getBuyer = buyerRepository.get(1);
        assertEquals(getBuyer, buyer);
    }

    @Test
    void getAll() {
        int size = 5;
        List<Buyer> buyers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Buyer buyer = new Buyer(i, "Name" + i, new ArrayList<>());
            buyers.add(buyer);
            buyerRepository.save(buyer);
        }
        List<Buyer> getAllBuyers = buyerRepository.getAll();
        assertEquals(getAllBuyers, buyers);
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}