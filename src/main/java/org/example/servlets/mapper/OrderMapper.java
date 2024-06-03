package org.example.servlets.mapper;

import org.example.servlets.dto.OrderDTO;
import org.example.model.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {
    Order orderDTOToOrder(OrderDTO orderDTO);

    OrderDTO orderToOrderDTO(Order order);
}
