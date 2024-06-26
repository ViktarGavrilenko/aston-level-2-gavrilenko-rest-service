package org.example.servlets;

import org.example.model.Item;
import org.example.services.impl.ItemServiceImpl;
import org.example.servlets.dto.ItemDTO;
import org.example.servlets.mapper.ItemDtoMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.example.services.impl.ItemServiceImplTest.getTemplateItem;
import static org.example.services.impl.ItemServiceImplTest.itemList;
import static org.example.servlets.OrderServletTest.getServletInputStream;
import static org.example.utils.StreamUtils.getTextFromInputStream;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ItemDtoMapperImpl dtoMapper;

    @Mock
    private ItemServiceImpl itemService;

    @InjectMocks
    private ItemServlet itemServlet;

    @Test
    void doGetAllTest() {
        int size = 5;
        when(request.getPathInfo()).thenReturn(null);
        when(itemService.getAll()).thenReturn(itemList(size));
        itemServlet.doGet(request, response);
        Mockito.verify(dtoMapper, times(size)).itemToItemDTO(any(Item.class));
    }

    @Test
    void doGetTest() {
        when(request.getPathInfo()).thenReturn("/1");
        when(itemService.get(Mockito.anyInt())).thenReturn(getTemplateItem(1));
        itemServlet.doGet(request, response);
        Mockito.verify(dtoMapper, times(1)).itemToItemDTO(any(Item.class));
    }

    @Test
    void doPostTest() throws IOException {
        ServletInputStream servletInputStream = getServletInputStream(getTextFromInputStream(
                OrderServletTest.class.getClassLoader().getResourceAsStream("itemDTO.json")));
        when(request.getInputStream()).thenReturn(servletInputStream);
        when(itemService.save(any(Item.class))).thenReturn(getTemplateItem(1));
        when(dtoMapper.itemDTOToItem(any(ItemDTO.class))).thenReturn(getTemplateItem(1));
        itemServlet.doPost(request, response);
        Mockito.verify(dtoMapper, times(1)).itemDTOToItem(any(ItemDTO.class));
        Mockito.verify(dtoMapper, times(1)).itemToItemDTO(any(Item.class));
    }

    @Test
    void doPutTest() throws IOException {
        ServletInputStream servletInputStream = getServletInputStream(getTextFromInputStream(
                OrderServletTest.class.getClassLoader().getResourceAsStream("itemDTO.json")));
        when(request.getInputStream()).thenReturn(servletInputStream);
        itemServlet.doPut(request, response);
        Mockito.verify(dtoMapper, times(1)).itemDTOToItem(any(ItemDTO.class));
        Mockito.verify(itemService, times(1)).update(any());
    }

    @Test
    void doDeleteTest() {
        when(request.getParameter("id")).thenReturn("1");
        itemServlet.doDelete(request, response);
        Mockito.verify(itemService, times(1)).delete(1);
    }
}