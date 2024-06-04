package org.example.repository.mapper;

import org.example.model.Order;

import java.sql.ResultSet;

public interface OrderResultSetMapper {
    Order map(ResultSet resultSet);
}
