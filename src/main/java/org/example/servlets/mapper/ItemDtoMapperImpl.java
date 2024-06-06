package org.example.servlets.mapper;

import org.example.model.Item;
import org.example.model.Order;
import org.example.repository.impl.OrderRepositoryImpl;
import org.example.servlets.dto.ItemDTO;

import java.util.ArrayList;
import java.util.List;

public class ItemDtoMapperImpl implements ItemDtoMapper {
    OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();

    @Override
    public Item itemDTOToItem(ItemDTO itemDTO) {
        List<Order> orders = new ArrayList<>();
        for (int order : itemDTO.getOrders()) {
            orders.add(orderRepository.get(order));
        }
        return new Item(itemDTO.getId(), itemDTO.getName(), itemDTO.getPrice(), orders);
    }

    @Override
    public ItemDTO itemToItemDTO(Item item) {
        List<Integer> orders = new ArrayList<>();
        for (Order order : item.getOrders()) {
            orders.add(order.getId());
        }
        return new ItemDTO(item.getId(), item.getName(), item.getPrice(), orders);
    }
}