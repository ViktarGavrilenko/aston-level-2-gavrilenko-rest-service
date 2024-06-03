package org.example.services.impl;

import org.example.dao.BuyerDAO;
import org.example.entity.Buyer;
import org.example.services.BuyerService;

import java.util.List;

public class BuyerServiceImpl implements BuyerService {
    private BuyerDAO buyerDAO = new BuyerDAO();

    @Override
    public List<Buyer> getAll() {
        return buyerDAO.getAll();
    }

    @Override
    public Buyer get(int id) {
        return buyerDAO.get(id);
    }
}