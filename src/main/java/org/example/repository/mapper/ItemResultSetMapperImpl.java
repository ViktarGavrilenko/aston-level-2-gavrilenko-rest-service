package org.example.repository.mapper;

import org.example.model.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.db.MySqlUtil.SQL_QUERY_FAILED;

public class ItemResultSetMapperImpl implements ItemResultSetMapper {
    @Override
    public Item map(ResultSet resultSet) {
        Item item;
        try {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                item = new Item(id, name, price);
            } else {
                item = null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
        return item;
    }
}