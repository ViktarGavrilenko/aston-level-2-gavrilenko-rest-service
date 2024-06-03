package org.example.dao;

import org.example.entity.Buyer;
import org.example.entity.Item;
import org.example.entity.Order;

import java.util.ArrayList;
import java.util.List;

import static org.example.db.MySqlUtil.*;

public class BuyerDAO implements DAO<Buyer, Integer> {
    private static final String ID_BUYERS = "SELECT id FROM buyers where 1";
    private static final String NAME_BUYERS = "SELECT name FROM buyers where id=%s";
    private static final String NUMBER_ORDER_BY_ID = "SELECT number FROM orders where id=%s";
    private static final String ITEM_BY_ID = "SELECT name, price FROM items where id=%s";
    private static final String ORDERS_OF_BUYER =
            "SELECT o.id FROM orders o LEFT JOIN buyer_order bo ON bo.id_order=o.id WHERE bo.id_buyer=%s;";
    private static final String ITEMS_OF_ORDER =
            "SELECT i.id FROM items i LEFT JOIN order_items oi ON oi.id_item=i.id WHERE oi.id_order=%s;";

    @Override
    public Buyer get(Integer id) {
        return getBuyerById(id);
    }

    @Override
    public List<Buyer> getAll() {
        List<Integer> idBuyers = getListFirstColumnInt(ID_BUYERS);
        List<Buyer> buyers = new ArrayList<>();
        for (int idBuyer : idBuyers) {
            buyers.add(getBuyerById(idBuyer));
        }
        return buyers;
    }

    private Buyer getBuyerById(int idBuyer) {
        List<Integer> idOrders = getListFirstColumnInt(String.format(ORDERS_OF_BUYER, idBuyer));
        String nameBuyers = getFirstColumn(String.format(NAME_BUYERS, idBuyer));
        List<Order> orders = new ArrayList<>();
        for (int idOrder : idOrders) {
            int numberOrder = Integer.parseInt(getFirstColumn(String.format(NUMBER_ORDER_BY_ID, idOrder)));
            orders.add(new Order(numberOrder, getListItemsInOrderById(idOrder)));
        }
        return new Buyer(nameBuyers, orders);
    }

    private List<Item> getListItemsInOrderById(int idOrder) {
        List<Integer> idItems = getListFirstColumnInt(String.format(ITEMS_OF_ORDER, idOrder));
        List<Item> items = new ArrayList<>();
        for (int idItem : idItems) {
            Item item = getItemById(String.format(ITEM_BY_ID, idItem));
            items.add(item);
        }
        return items;
    }

    @Override
    public void save(Buyer buyer) {

    }

    @Override
    public void update(Buyer buyer) {

    }

    @Override
    public void delete(Integer id) {

    }
}
