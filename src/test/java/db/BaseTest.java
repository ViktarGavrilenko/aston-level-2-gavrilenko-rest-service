package db;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.db.DBConnectionProvider;
import org.example.model.Buyer;
import org.example.model.Item;
import org.example.model.Order;
import org.example.repository.impl.BuyerRepositoryImpl;
import org.example.repository.impl.ItemRepositoryImpl;
import org.example.repository.impl.OrderRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.MySQLContainer;

import java.io.IOException;

import static org.example.utils.StreamUtils.getTextFromInputStream;

public class BaseTest {
    public static final String DELETE_ALL_BUYER = "DELETE FROM buyers WHERE 1;";
    public static final String DELETE_ALL_ITEM = "DELETE FROM items WHERE 1;";
    public static final String DELETE_ALL_ORDER = "DELETE FROM orders WHERE 1;";
    public static final String DELETE_ALL_BUYER_ORDER = "DELETE FROM buyer_order WHERE 1;";
    public static final String DELETE_ALL_ORDER_ITEMS = "DELETE FROM order_items WHERE 1;";

    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(
            "mysql:8"
    );
    protected static DBConnectionProvider connectionProvider;
    protected static BuyerRepositoryImpl buyerRepository;
    protected static ItemRepositoryImpl itemRepository;
    protected static OrderRepositoryImpl orderRepository;

    @BeforeAll
    static void beforeAll() {
        mySQLContainer.start();
        connectionProvider = new DBConnectionProvider(
                mySQLContainer.getJdbcUrl(),
                mySQLContainer.getUsername(),
                mySQLContainer.getPassword()
        );
        buyerRepository = new BuyerRepositoryImpl(connectionProvider);
        itemRepository = new ItemRepositoryImpl(connectionProvider);
        orderRepository = new OrderRepositoryImpl(connectionProvider);
    }

    @BeforeEach
    void setUp() {
        connectionProvider.sendSqlQuery(DELETE_ALL_BUYER);
        connectionProvider.sendSqlQuery(DELETE_ALL_ITEM);
        connectionProvider.sendSqlQuery(DELETE_ALL_ORDER);
        connectionProvider.sendSqlQuery(DELETE_ALL_BUYER_ORDER);
        connectionProvider.sendSqlQuery(DELETE_ALL_ORDER_ITEMS);
    }

    protected Order getOrderFromJsonFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = getTextFromInputStream(DBTest.class.getClassLoader().getResourceAsStream("order.json"));
        return mapper.readValue(json, Order.class);
    }

    protected Buyer getBuyerFromJsonFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = getTextFromInputStream(DBTest.class.getClassLoader().getResourceAsStream("buyer.json"));
        return mapper.readValue(json, Buyer.class);
    }

    protected Item getItemFromJsonFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = getTextFromInputStream(DBTest.class.getClassLoader().getResourceAsStream("item.json"));
        return mapper.readValue(json, Item.class);
    }
}