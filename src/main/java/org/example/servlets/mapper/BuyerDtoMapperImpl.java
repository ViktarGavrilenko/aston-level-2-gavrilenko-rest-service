package org.example.servlets.mapper;

import org.example.model.Buyer;
import org.example.model.Order;
import org.example.repository.impl.OrderRepositoryImpl;
import org.example.servlets.dto.BuyerDTO;

import java.util.ArrayList;
import java.util.List;

public class BuyerDtoMapperImpl implements BuyerDtoMapper {
    OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();

    @Override
    public Buyer buyerDTOToBuyer(BuyerDTO buyerDTO) {
        List<Order> orders = new ArrayList<>();
        for (int idOrder : buyerDTO.getOrders()) {
            orders.add(orderRepository.get(idOrder));
        }
        return new Buyer(buyerDTO.getId(), buyerDTO.getName(), orders);
    }

    @Override
    public BuyerDTO buyerToBuyerDTO(Buyer buyer) {
        List<Integer> orders = new ArrayList<>();
        for (Order order : buyer.getOrders()) {
            orders.add(order.getId());
        }
        return new BuyerDTO(buyer.getId(), buyer.getName(), orders);
    }
}