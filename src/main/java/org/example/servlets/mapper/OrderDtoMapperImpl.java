package org.example.servlets.mapper;

import org.example.model.Order;
import org.example.servlets.dto.OrderDTO;

public class OrderDtoMapperImpl implements OrderDtoMapper {
    @Override
    public Order orderDTOToOrder(OrderDTO orderDTO) {
        return new Order(orderDTO.getId(), orderDTO.getNumber(), orderDTO.getItems());
    }

    @Override
    public OrderDTO orderToOrderDTO(Order order) {
        return new OrderDTO(order.getId(), order.getNumber(), order.getItems());
    }
}
