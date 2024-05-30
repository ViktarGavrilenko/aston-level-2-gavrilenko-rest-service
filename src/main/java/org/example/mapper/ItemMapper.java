package org.example.mapper;

import org.example.dto.ItemDTO;
import org.example.entity.Item;
import org.mapstruct.Mapper;

@Mapper
public interface ItemMapper {
    Item itemDTOtoItem(ItemDTO itemDTO);

    ItemDTO itemToItemDTO(Item item);
}
