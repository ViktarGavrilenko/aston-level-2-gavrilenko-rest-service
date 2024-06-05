package org.example.servlets.mapper;

import org.example.model.Item;
import org.example.servlets.dto.ItemDTO;

public class ItemDtoMapperImpl implements ItemDtoMapper {

    @Override
    public Item itemDTOToItem(ItemDTO itemDTO) {
        return new Item(itemDTO.getId(), itemDTO.getName(), itemDTO.getPrice());
    }

    @Override
    public ItemDTO itemToItemDTO(Item item) {
        return new ItemDTO(item.getId(), item.getName(), item.getPrice());
    }
}