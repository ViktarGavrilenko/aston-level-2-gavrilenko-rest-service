package org.example.servlets.mapper;

import org.example.model.Buyer;
import org.example.servlets.dto.BuyerDTO;

public class BuyerDtoMapperImpl implements BuyerDtoMapper{
    @Override
    public Buyer buyerDTOToBuyer(BuyerDTO buyerDTO) {
        return new Buyer(buyerDTO.getId(), buyerDTO.getName(), buyerDTO.getOrders());
    }

    @Override
    public BuyerDTO buyerToBuyerDTO(Buyer buyer) {
        return new BuyerDTO(buyer.getId(), buyer.getName(), buyer.getOrders());
    }
}
