package org.example.repository.mapper;

import org.example.model.Buyer;

import java.sql.ResultSet;

public interface BuyerResultSetMapper {
    Buyer map(ResultSet resultSet);
}