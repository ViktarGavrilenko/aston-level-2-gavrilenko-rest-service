package org.example.repository.impl;

import org.example.model.Item;
import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.example.repository.mapper.OrderResultSetMapperImpl;

import java.util.ArrayList;
import java.util.List;

import static org.example.db.MySqlUtil.*;
import static org.example.db.MySqlUtil.SQL_QUERY_FAILED;
import static org.example.repository.impl.BuyerRepositoryImpl.ORDERS_OF_BUYER;

public class OrderRepositoryImpl implements OrderRepository {
    public static final String ID_ORDERS = "SELECT id FROM orders where 1";
    public static final String ORDER_BY_ID = "SELECT id, number FROM orders where id=%s";
    public static final String INSERT_ORDER = "INSERT INTO order(number) VALUES ('%s')";
    public static final String INSERT_ORDER_ITEMS = "INSERT INTO order_items(id_order, id_item) VALUES (%s, %s)";
    OrderResultSetMapperImpl orderResultSetMapper = new OrderResultSetMapperImpl();

    @Override
    public Order get(Integer id) {
        return orderResultSetMapper.map(sendSelectQuery(String.format(ORDER_BY_ID, id)));
    }

    @Override
    public List<Order> getAll() {
        List<Integer> idOrders = getListFirstColumnInt(ID_ORDERS);
        List<Order> orders = new ArrayList<>();
        for (int idOrder : idOrders) {
            orders.add(get(idOrder));
        }
        return orders;
    }

    @Override
    public Order save(Order order) {
        if (sendSqlQuery(String.format(INSERT_ORDER, order.getNumber()))) {
            List<Item> items = order.getItems();
            return order;
        } else {
            throw new IllegalArgumentException(SQL_QUERY_FAILED);
        }
    }

    @Override
    public void update(Integer id, Order order) {

    }

    @Override
    public void delete(Integer id) {

    }

    public List<Order> getListOfBuyerOrdersById(int idBuyer) {
        List<Integer> idOrders = getListFirstColumnInt(String.format(ORDERS_OF_BUYER, idBuyer));
        List<Order> orders = new ArrayList<>();
        for (int idOrder : idOrders) {
            orders.add(get(idOrder));
        }
        return orders;
    }
}