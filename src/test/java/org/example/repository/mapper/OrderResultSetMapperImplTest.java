package org.example.repository.mapper;

import org.example.model.Order;
import org.example.repository.impl.ItemRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.example.services.impl.ItemServiceImplTest.itemList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderResultSetMapperImplTest {
    @Mock
    private ResultSet resultSet;

    @Mock
    private ItemRepositoryImpl repository;

    @InjectMocks
    private OrderResultSetMapperImpl orderResultSetMapper;

    private final int id = 1;
    private final int number = 10;

    @Test
    void mapTest() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("id")).thenReturn(id);
        Mockito.when(resultSet.getInt("number")).thenReturn(number);
        Mockito.when(repository.getListItemsInOrderById(id)).thenReturn(itemList(id));
        Order order = new Order(id, number, itemList(id));
        assertEquals(order, orderResultSetMapper.map(resultSet));
    }

    @Test
    void mapWithOutOrder() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("id")).thenReturn(id);
        Mockito.when(resultSet.getInt("number")).thenReturn(number);
        Order order = new Order(id, number, new ArrayList<>());
        assertEquals(order, orderResultSetMapper.mapWithOutItems(resultSet));
    }
}