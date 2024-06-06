package org.example.repository.impl;

import org.example.model.Item;
import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.example.repository.mapper.OrderResultSetMapperImpl;

import java.util.ArrayList;
import java.util.List;

import static org.example.db.MySqlUtil.*;
import static org.example.repository.impl.BuyerRepositoryImpl.ORDERS_OF_BUYER;

public class OrderRepositoryImpl implements OrderRepository {
    public static final String ID_ORDERS = "SELECT id FROM orders where 1";
    public static final String ORDER_BY_NUMBER = "SELECT id, number FROM orders where number=%s";
    public static final String ORDER_BY_ID = "SELECT id, number FROM orders where id=%s";
    public static final String INSERT_ORDER = "INSERT INTO orders (number) VALUES (%s)";
    public static final String INSERT_ORDER_ITEMS = "INSERT INTO order_items(id_order, id_item) VALUES (%s, %s)";
    public static final String UPDATE_ORDER_BY_ID = "UPDATE orders SET number=%s where id = '%s'";
    public static final String DELETE_ORDER_ITEMS = "DELETE FROM order_items WHERE id_order = %s and id_item=%s";
    public static final String DELETE_ORDER = "DELETE FROM orders WHERE id = %s ";
    public static final String DELETE_ORDER_FROM_ORDER_ITEMS = "DELETE FROM order_items WHERE id_order = %s";
    public static final String DELETE_BUYER_ORDER_BY_ID_ORDER = "DELETE FROM buyer_order WHERE id_order = %s";

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
        setAutoCommitFalse();
        if (sendSqlQuery(String.format(INSERT_ORDER, order.getNumber()))) {
            int idOrder = orderResultSetMapper.map(
                    sendSelectQuery(String.format(ORDER_BY_NUMBER, order.getNumber()))).getId();
            List<Item> items = order.getItems();
            for (Item item : items) {
                sendSqlQuery(String.format(INSERT_ORDER_ITEMS, idOrder, item.getId()));
            }
            completeTransaction();
            setAutoCommitTrue();
            return get(idOrder);
        } else {
            setAutoCommitTrue();
            throw new IllegalArgumentException(SQL_QUERY_FAILED);
        }
    }

    @Override
    public void update(Order order) {
        setAutoCommitFalse();
        sendSqlQuery(String.format(UPDATE_ORDER_BY_ID, order.getNumber(), order.getId()));
        List<Item> newItems = order.getItems();
        List<Item> oldItem = get(order.getId()).getItems();
        List<Item> listForAdd = new ArrayList<>(newItems);
        listForAdd.removeAll(oldItem);
        List<Item> listForDelete = new ArrayList<>(oldItem);
        listForDelete.removeAll(newItems);
        for (Item item : listForDelete) {
            sendSqlQuery(String.format(DELETE_ORDER_ITEMS, order.getId(), item.getId()));
        }
        for (Item item : listForAdd) {
            sendSqlQuery(String.format(INSERT_ORDER_ITEMS, order.getId(), item.getId()));
        }
        completeTransaction();
        setAutoCommitTrue();
    }

    @Override
    public void delete(Integer id) {
        setAutoCommitFalse();
        sendSqlQuery(String.format(DELETE_ORDER_FROM_ORDER_ITEMS, id));
        sendSqlQuery(String.format(DELETE_ORDER, id));
        completeTransaction();
        setAutoCommitTrue();
    }

    public List<Order> getListOfBuyerOrdersById(int idBuyer) {
        List<Integer> idOrders = getListFirstColumnInt(String.format(ORDERS_OF_BUYER, idBuyer));
        List<Order> orders = new ArrayList<>();
        for (int idOrder : idOrders) {
            Order order = get(idOrder);
            if (order == null) {
                sendSqlQuery(String.format(DELETE_BUYER_ORDER_BY_ID_ORDER, idOrder));
            } else {
                orders.add(order);
            }
        }
        return orders;
    }
}