package db;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Buyer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.example.utils.StreamUtils.getTextFromInputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBTest extends BaseTest {
    @Test
    void shouldGetCustomers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = getTextFromInputStream(DBTest.class.getClassLoader().getResourceAsStream("buyer.json"));
        Buyer buyer = mapper.readValue(json, Buyer.class);
        List<Buyer> buyers = buyerRepository.getAll();
        assertEquals(0, buyers.size());
        buyerRepository.save(buyer);
        buyers = buyerRepository.getAll();
        assertEquals(1, buyers.size());
    }
}