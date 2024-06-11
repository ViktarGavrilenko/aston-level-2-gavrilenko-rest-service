package org.example.servlets.mapper;

import org.example.model.Item;
import org.example.model.Order;
import org.example.repository.impl.ItemRepositoryImpl;
import org.example.servlets.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderDtoMapperImpl implements OrderDtoMapper {
    ItemRepositoryImpl itemRepository = new ItemRepositoryImpl();

    @Override
    public Order orderDTOToOrder(OrderDTO orderDTO) {
        List<Item> items = new ArrayList<>();
        for (int item : orderDTO.getItems()) {
            items.add(itemRepository.get(item));
        }
        return new Order(orderDTO.getId(), orderDTO.getNumber(), items);
    }

    @Override
    public OrderDTO orderToOrderDTO(Order order) {
        List<Integer> items = new ArrayList<>();
        for (Item item : order.getItems()) {
            items.add(item.getId());
        }
        return new OrderDTO(order.getId(), order.getNumber(), items);
    }
}