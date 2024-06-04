package org.example.repository.impl;

import org.example.model.Buyer;
import org.example.repository.BuyerRepository;
import org.example.repository.mapper.BuyerResultSetMapperImpl;

import java.util.ArrayList;
import java.util.List;

import static org.example.db.MySqlUtil.getListFirstColumnInt;
import static org.example.db.MySqlUtil.sendSelectQuery;

public class BuyerRepositoryImpl implements BuyerRepository {
    public static final String ID_BUYERS = "SELECT id FROM buyers where 1";
    public static final String SELECT_BUYERS = "SELECT id, name FROM buyers where id=%s";

    public static final String ORDERS_OF_BUYER =
            "SELECT o.id FROM orders o LEFT JOIN buyer_order bo ON bo.id_order=o.id WHERE bo.id_buyer=%s;";
    public static final String ITEMS_OF_ORDER =
            "SELECT i.id FROM items i LEFT JOIN order_items oi ON oi.id_item=i.id WHERE oi.id_order=%s;";

    BuyerResultSetMapperImpl buyerResultSetMapper = new BuyerResultSetMapperImpl();

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
        return buyer;
    }

    @Override
    public void update(Integer id, Buyer buyer) {

    }

    @Override
    public void delete(Integer id) {

    }
}