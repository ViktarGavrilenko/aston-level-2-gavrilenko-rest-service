package org.example.repository.mapper;

import org.example.model.Buyer;
import org.example.repository.impl.OrderRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BuyerResultSetMapperImplTest {

    @Mock
    private ResultSet resultSet;

    @Mock
    private OrderRepositoryImpl repository;

    @InjectMocks
    private BuyerResultSetMapperImpl buyerResultSetMapper;

    @Test
    void mapTest() throws SQLException {
        int id = 1;
        String name = "SomeName";
        List list = List.of(1, 2, 3);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("id")).thenReturn(id);
        Mockito.when(resultSet.getString("name")).thenReturn(name);
        Mockito.when(repository.getListOfBuyerOrdersById(id)).thenReturn(list);
        Buyer buyer = new Buyer(id, name, list);
        assertEquals(buyer, buyerResultSetMapper.map(resultSet));
    }
}