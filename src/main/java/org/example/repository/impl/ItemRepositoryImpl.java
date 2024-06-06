package org.example.repository.impl;

import org.example.model.Item;
import org.example.model.Order;
import org.example.repository.ItemRepository;
import org.example.repository.mapper.ItemResultSetMapperImpl;

import java.util.ArrayList;
import java.util.List;

import static org.example.db.MySqlUtil.*;
import static org.example.repository.impl.BuyerRepositoryImpl.SELECT_ID_ITEMS_OF_ORDER_BY_ID_ORDER;
import static org.example.repository.impl.OrderRepositoryImpl.INSERT_ORDER_ITEMS;

public class ItemRepositoryImpl implements ItemRepository {
    public static final String ITEM_BY_ID = "SELECT id, name, price FROM items where id=%s";
    public static final String ITEM_BY_NAME_AND_PRICE = "SELECT id, name, price FROM items where name='%s' and price=%s";
    public static final String ID_ITEMS = "SELECT id FROM items where 1";
    public static final String INSERT_ITEM = "INSERT INTO items(name, price) VALUES ('%s', %s)";
    public static final String DELETE_ITEM_BY_ID = "DELETE FROM items where id = %s";
    public static final String DELETE_ITEM_FROM_ORDER_ITEMS = "DELETE FROM order_items WHERE id_item = %s";
    public static final String DELETE_ORDER_ITEMS = "DELETE FROM order_items WHERE id_order = %s and id_item=%s";
    public static final String UPDATE_ITEM_BY_ID = "UPDATE items SET name='%s', price=%s where id = %s";

    static ItemResultSetMapperImpl itemResultSetMapper = new ItemResultSetMapperImpl();

    @Override
    public Item get(Integer id) {
        return itemResultSetMapper.map(sendSelectQuery(String.format(ITEM_BY_ID, id)));
    }

    @Override
    public List<Item> getAll() {
        List<Integer> idItems = getListFirstColumnInt(ID_ITEMS);
        List<Item> items = new ArrayList<>();
        for (int idItem : idItems) {
            items.add(get(idItem));
        }
        return items;
    }

    @Override
    public Item save(Item item) {
        if (sendSqlQuery(String.format(INSERT_ITEM, item.getName(), item.getPrice()))) {
            int idItem = itemResultSetMapper.map(sendSelectQuery(
                    String.format(ITEM_BY_NAME_AND_PRICE, item.getName(), item.getPrice()))).getId();
            List<Order> orders = item.getOrders();
            for (Order order : orders) {
                sendSqlQuery(String.format(INSERT_ORDER_ITEMS, order.getId(), idItem));
            }
            return get(idItem);
        } else {
            throw new IllegalArgumentException(SQL_QUERY_FAILED);
        }
    }

    @Override
    public void update(Item item) {
        setAutoCommitFalse();
        sendSqlQuery(String.format(UPDATE_ITEM_BY_ID, item.getName(), item.getPrice(), item.getId()));
        List<Order> newOrder = item.getOrders();
        List<Order> oldOrder = get(item.getId()).getOrders();
        List<Order> ordersForAdd = new ArrayList<>(newOrder);
        ordersForAdd.removeAll(oldOrder);
        List<Order> ordersForDelete = new ArrayList<>(oldOrder);
        ordersForDelete.removeAll(newOrder);
        for (Order order : ordersForDelete) {
            sendSqlQuery(String.format(DELETE_ORDER_ITEMS, order.getId(), item.getId()));
        }
        for (Order order : ordersForAdd) {
            sendSqlQuery(String.format(INSERT_ORDER_ITEMS, order.getId(), item.getId()));
        }
        completeTransaction();
        setAutoCommitTrue();
    }

    @Override
    public void delete(Integer id) {
        setAutoCommitFalse();
        sendSqlQuery(String.format(DELETE_ITEM_FROM_ORDER_ITEMS, id));
        sendSqlQuery(String.format(DELETE_ITEM_BY_ID, id));
        completeTransaction();
        setAutoCommitTrue();
    }

    public List<Item> getListItemsInOrderById(int idOrder) {
        List<Integer> idItems = getListFirstColumnInt(String.format(SELECT_ID_ITEMS_OF_ORDER_BY_ID_ORDER, idOrder));
        List<Item> items = new ArrayList<>();
        for (int idItem : idItems) {
            Item item = itemResultSetMapper.mapWithOutOrder(sendSelectQuery(String.format(ITEM_BY_ID, idItem)));
            items.add(item);
        }
        return items;
    }
}