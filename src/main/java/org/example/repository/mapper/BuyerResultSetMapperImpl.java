package org.example.repository.mapper;

import org.example.model.Buyer;
import org.example.repository.impl.OrderRepositoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.db.DBConnectionProvider.SQL_QUERY_FAILED;

public class BuyerResultSetMapperImpl implements BuyerResultSetMapper {
    OrderRepositoryImpl repository = new OrderRepositoryImpl();

    @Override
    public Buyer map(ResultSet resultSet) {
        Buyer buyer;
        try {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                buyer = new Buyer(id, name, repository.getListOfBuyerOrdersById(id));
            } else {
                buyer = null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
        return buyer;
    }
}