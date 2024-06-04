package org.example.repository.mapper;

import org.example.model.Item;

import java.sql.ResultSet;

public interface ItemResultSetMapper {
    Item map(ResultSet resultSet);
}
