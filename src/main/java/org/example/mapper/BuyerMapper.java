package org.example.mapper;

import org.example.dto.BuyerDTO;
import org.example.entity.Buyer;
import org.mapstruct.Mapper;

@Mapper
public interface BuyerMapper {
    Buyer buyerDTOToBuyer(BuyerDTO buyerDTO);

    BuyerDTO buyerToBuyerDTO(Buyer buyer);
}