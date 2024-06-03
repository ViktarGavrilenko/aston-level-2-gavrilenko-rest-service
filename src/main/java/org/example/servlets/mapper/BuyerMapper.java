package org.example.servlets.mapper;

import org.example.servlets.dto.BuyerDTO;
import org.example.model.Buyer;
import org.mapstruct.Mapper;

@Mapper
public interface BuyerMapper {
    Buyer buyerDTOToBuyer(BuyerDTO buyerDTO);

    BuyerDTO buyerToBuyerDTO(Buyer buyer);
}