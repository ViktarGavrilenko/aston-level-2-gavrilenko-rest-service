package org.example.services;

import org.example.dao.BuyerDAO;
import org.example.dto.BuyerDTO;
import org.example.entity.Buyer;

import java.util.List;

public class BuyerService {
    private BuyerDAO buyerDAO;

    public List<BuyerDTO> getAll() {
        return buyerDAO.getAll();
    }
}