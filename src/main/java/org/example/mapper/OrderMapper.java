package org.example.mapper;

import org.example.dto.OrderDTO;
import org.example.entity.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {
    Order orderDTOToOrder(OrderDTO orderDTO);

    OrderDTO orderToOrderDTO(Order order);
}
