package org.example.services.impl;

import org.example.repository.impl.BuyerRepositoryImpl;
import org.example.model.Buyer;
import org.example.services.BuyerService;

import java.util.List;

public class BuyerServiceImpl implements BuyerService {
    private BuyerRepositoryImpl buyerRepository = new BuyerRepositoryImpl();

    @Override
    public List<Buyer> getAll() {
        return buyerRepository.getAll();
    }

    @Override
    public Buyer get(int id) {
        return buyerRepository.get(id);
    }

    @Override
    public Buyer save(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    @Override
    public void update(Buyer buyer) {
        buyerRepository.update(buyer);
    }

    @Override
    public void delete(int buyerId) {
        buyerRepository.delete(buyerId);
    }
}