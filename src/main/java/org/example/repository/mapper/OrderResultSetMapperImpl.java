package org.example.repository.mapper;

import org.example.model.Order;
import org.example.repository.impl.ItemRepositoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.example.db.MySqlUtil.SQL_QUERY_FAILED;

public class OrderResultSetMapperImpl implements OrderResultSetMapper {
    ItemRepositoryImpl repository = new ItemRepositoryImpl();

    @Override
    public Order map(ResultSet resultSet) {
        Order order;
        try {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int number = resultSet.getInt("number");
                order = new Order(id, number, repository.getListItemsInOrderById(id));
            } else {
                order = null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
        return order;
    }

    public Order mapWithOutItems(ResultSet resultSet) {
        Order order;
        try {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int number = resultSet.getInt("number");
                order = new Order(id, number, new ArrayList<>());
            } else {
                order = null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
        return order;
    }
}