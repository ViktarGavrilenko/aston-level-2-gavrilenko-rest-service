package org.example.services;

import org.example.entity.Buyer;

import java.util.List;

public interface BuyerService {
    List<Buyer> getAll();

    Buyer get(int id);
}