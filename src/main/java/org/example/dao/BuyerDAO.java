package org.example.dao;

import org.example.dto.BuyerDTO;

import java.util.List;
import java.util.Optional;

public class BuyerDAO implements DAO<BuyerDTO, Integer> {
    @Override
    public Optional<BuyerDTO> get(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<BuyerDTO> getAll() {
        return List.of();
    }

    @Override
    public void save(BuyerDTO buyer) {

    }

    @Override
    public void update(BuyerDTO buyer) {

    }

    @Override
    public void delete(Integer id) {

    }
}
