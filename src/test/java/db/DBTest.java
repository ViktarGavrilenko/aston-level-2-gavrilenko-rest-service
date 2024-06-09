package db;

import org.example.db.DBConnectionProvider;
import org.example.model.Buyer;
import org.example.model.Order;
import org.example.repository.impl.BuyerRepositoryImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBTest {
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(
            "mysql:8"
    );

    BuyerRepositoryImpl buyerRepository;

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
    }

    @Test
    void shouldGetCustomers() {
        List<Order> orders = new ArrayList<>();

        buyerRepository.save(new Buyer(1, "Иван", orders));

        List<Buyer> buyers = buyerRepository.getAll();
        assertEquals(1, buyers.size());
    }
}