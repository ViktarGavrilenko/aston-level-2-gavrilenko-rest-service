package org.example.servlets.mapper;

import org.example.model.Item;
import org.example.servlets.dto.ItemDTO;

public class ItemDtoMapperImpl implements ItemDtoMapper{

    @Override
    public Item itemDTOToItem(ItemDTO itemDTO) {
        return new Item(itemDTO.getName(), itemDTO.getPrice());
    }

    @Override
    public ItemDTO itemToItemDTO(Item item) {
        return new ItemDTO(item.getName(), item.getPrice());
    }
}
