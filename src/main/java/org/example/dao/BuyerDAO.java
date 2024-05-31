package org.example.dao;

import org.example.dto.BuyerDTO;

import java.util.List;
import java.util.Optional;

import static org.example.db.MySqlUtil.getListFirstColumn;

public class BuyerDAO implements DAO<BuyerDTO, Integer> {
    private static final String BUYERS = "SELECT name FROM buyers where %s";

    @Override
    public Optional<BuyerDTO> get(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<BuyerDTO> getAll() {
        getListFirstColumn(String.format(BUYERS, 1));

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
