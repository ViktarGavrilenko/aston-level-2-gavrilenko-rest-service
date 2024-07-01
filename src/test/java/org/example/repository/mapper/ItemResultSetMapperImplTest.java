package org.example.repository.mapper;

import org.example.model.Item;
import org.example.repository.impl.OrderRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.example.services.impl.OrderServiceImplTest.orderList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ItemResultSetMapperImplTest {
    @Mock
    private ResultSet resultSet;

    @Mock
    private OrderRepositoryImpl repository;

    @InjectMocks
    private ItemResultSetMapperImpl itemResultSetMapper;

    private final int id = 1;
    private final String name = "SomeName";
    private final int price = 10;

    @Test
    void mapTest() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("id")).thenReturn(id);
        Mockito.when(resultSet.getString("name")).thenReturn(name);
        Mockito.when(resultSet.getInt("price")).thenReturn(price);
        Mockito.when(repository.getListOrderByIdItem(id)).thenReturn(orderList(id));
        Item item = new Item(id, name, price, orderList(id));
        assertEquals(item, itemResultSetMapper.map(resultSet));
    }

    @Test
    void mapWithOutOrder() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("id")).thenReturn(id);
        Mockito.when(resultSet.getString("name")).thenReturn(name);
        Mockito.when(resultSet.getInt("price")).thenReturn(price);
        Item item = new Item(id, name, price, new ArrayList<>());
        assertEquals(item, itemResultSetMapper.mapWithOutOrder(resultSet));
    }
}