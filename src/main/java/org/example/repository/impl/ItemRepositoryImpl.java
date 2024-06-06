package org.example.repository.impl;

import org.example.model.Item;
import org.example.repository.ItemRepository;
import org.example.repository.mapper.ItemResultSetMapperImpl;

import java.util.ArrayList;
import java.util.List;

import static org.example.db.MySqlUtil.*;
import static org.example.repository.impl.BuyerRepositoryImpl.ITEMS_OF_ORDER;

public class ItemRepositoryImpl implements ItemRepository {
    public static final String ITEM_BY_ID = "SELECT id, name, price FROM items where id=%s";
    public static final String ITEM_BY_NAME_AND_PRICE = "SELECT id, name, price FROM items where name='%s' and price=%s";
    public static final String ID_ITEMS = "SELECT id FROM items where 1";
    public static final String INSERT_ITEM = "INSERT INTO items(name, price) VALUES ('%s', %s)";
    public static final String DELETE_ITEM_BY_ID = "DELETE FROM items where id = %s";
    public static final String UPDATE_ITEM_BY_ID = "UPDATE items SET name='%s', price=%s where id = %s";

    ItemResultSetMapperImpl itemResultSetMapper = new ItemResultSetMapperImpl();

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
            return itemResultSetMapper.map(sendSelectQuery(
                    String.format(ITEM_BY_NAME_AND_PRICE, item.getName(), item.getPrice())));
        } else {
            throw new IllegalArgumentException(SQL_QUERY_FAILED);
        }
    }

    @Override
    public void update(Item item) {
        sendSqlQuery(String.format(UPDATE_ITEM_BY_ID, item.getName(), item.getPrice(), item.getId()));
    }

    @Override
    public void delete(Integer id) {
        sendSqlQuery(String.format(DELETE_ITEM_BY_ID, id));
    }

    public List<Item> getListItemsInOrderById(int idOrder) {
        List<Integer> idItems = getListFirstColumnInt(String.format(ITEMS_OF_ORDER, idOrder));
        List<Item> items = new ArrayList<>();
        for (int idItem : idItems) {
            Item item = itemResultSetMapper.map(sendSelectQuery(String.format(ITEM_BY_ID, idItem)));
            items.add(item);
        }
        return items;
    }
}