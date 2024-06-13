package db;

import org.example.db.DBConnectionProvider;
import org.example.repository.impl.BuyerRepositoryImpl;
import org.example.repository.impl.ItemRepositoryImpl;
import org.example.repository.impl.OrderRepositoryImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.MySQLContainer;

public class BaseTest {
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(
            "mysql:8"
    );

    protected BuyerRepositoryImpl buyerRepository;
    protected ItemRepositoryImpl itemRepository;
    protected OrderRepositoryImpl orderRepository;

    @BeforeAll
    static void beforeAll() {
        mySQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mySQLContainer.stop();
    }

    @BeforeEach
    void setUp() {
        DBConnectionProvider connectionProvider = new DBConnectionProvider(
                mySQLContainer.getJdbcUrl(),
                mySQLContainer.getUsername(),
                mySQLContainer.getPassword()
        );
        buyerRepository = new BuyerRepositoryImpl(connectionProvider);
        itemRepository = new ItemRepositoryImpl(connectionProvider);
        orderRepository = new OrderRepositoryImpl(connectionProvider);
    }
}