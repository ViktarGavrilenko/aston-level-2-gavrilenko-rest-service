package org.example.repository.mapper;

import org.example.model.Item;
import org.example.repository.impl.OrderRepositoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.example.db.DBConnectionProvider.SQL_QUERY_FAILED;

public class ItemResultSetMapperImpl implements ItemResultSetMapper {
    OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();

    @Override
    public Item map(ResultSet resultSet) {
        Item item;
        try {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                item = new Item(id, name, price, orderRepository.getListOrderByIdItem(id));
            } else {
                item = null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
        return item;
    }

    public Item mapWithOutOrder(ResultSet resultSet) {
        Item item;
        try {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                item = new Item(id, name, price, new ArrayList<>());
            } else {
                item = null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
        return item;
    }
}