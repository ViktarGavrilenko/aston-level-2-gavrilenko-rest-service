package org.example.servlets.mapper;

import org.example.servlets.dto.ItemDTO;
import org.example.model.Item;
import org.mapstruct.Mapper;

@Mapper
public interface ItemDtoMapper {
    Item itemDTOToItem(ItemDTO itemDTO);

    ItemDTO itemToItemDTO(Item item);
}
