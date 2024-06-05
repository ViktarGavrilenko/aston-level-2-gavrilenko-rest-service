package org.example.repository.impl;

import org.example.model.Buyer;
import org.example.model.Item;
import org.example.model.Order;
import org.example.repository.BuyerRepository;
import org.example.repository.mapper.BuyerResultSetMapperImpl;

import java.util.ArrayList;
import java.util.List;

import static org.example.db.MySqlUtil.*;

public class BuyerRepositoryImpl implements BuyerRepository {
    public static final String ID_BUYERS = "SELECT id FROM buyers where 1";
    public static final String SELECT_BUYERS = "SELECT id, name FROM buyers where id=%s";
    public static final String BUYER_BY_NAME= "SELECT id, name FROM buyers where name='%s'";

    public static final String INSERT_BUYER = "INSERT INTO buyers (name) VALUES ('%s')";
    public static final String INSERT_BUYER_ORDERS = "INSERT INTO buyer_order (id_buyer, id_order) VALUES (%s, %s)";

    public static final String ORDERS_OF_BUYER =
            "SELECT o.id FROM orders o LEFT JOIN buyer_order bo ON bo.id_order=o.id WHERE bo.id_buyer=%s;";
    public static final String ITEMS_OF_ORDER =
            "SELECT i.id FROM items i LEFT JOIN order_items oi ON oi.id_item=i.id WHERE oi.id_order=%s;";

    BuyerResultSetMapperImpl buyerResultSetMapper = new BuyerResultSetMapperImpl();
    OrderRepositoryImpl orderRepository  = new OrderRepositoryImpl();

    @Override
    public Buyer get(Integer id) {
        return buyerResultSetMapper.map(sendSelectQuery(String.format(SELECT_BUYERS, id)));
    }

    @Override
    public List<Buyer> getAll() {
        List<Integer> idBuyers = getListFirstColumnInt(ID_BUYERS);
        List<Buyer> buyers = new ArrayList<>();
        for (int idBuyer : idBuyers) {
            buyers.add(get(idBuyer));
        }
        return buyers;
    }

    @Override
    public Buyer save(Buyer buyer) {
        setAutoCommitFalse();
        if (sendSqlQuery(String.format(INSERT_BUYER, buyer.getName()))) {
            int idBuyer = buyerResultSetMapper.map(
                    sendSelectQuery(String.format(BUYER_BY_NAME, buyer.getName()))).getId();
            List<Order> orders = buyer.getOrders();
            for (Order order : orders) {
                Order saveOrder = orderRepository.save(order);
                sendSqlQuery(String.format(INSERT_BUYER_ORDERS, idBuyer, saveOrder.getId()));
                setAutoCommitFalse();
            }
            completeTransaction();
            setAutoCommitTrue();
            return buyer;
        } else {
            setAutoCommitTrue();
            throw new IllegalArgumentException(SQL_QUERY_FAILED);
        }
    }

    @Override
    public void update(Buyer buyer) {

    }

    @Override
    public void delete(Integer id) {

    }
}