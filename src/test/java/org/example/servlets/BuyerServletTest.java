package org.example.servlets;

import org.example.model.Buyer;
import org.example.services.impl.BuyerServiceImpl;
import org.example.servlets.dto.BuyerDTO;
import org.example.servlets.mapper.BuyerDtoMapperImpl;
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

import static org.example.services.impl.BuyerServiceImplTest.buyerList;
import static org.example.services.impl.BuyerServiceImplTest.getTemplateBuyer;
import static org.example.servlets.OrderServletTest.getServletInputStream;
import static org.example.utils.StreamUtils.getTextFromInputStream;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuyerServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private BuyerDtoMapperImpl dtoMapper;

    @Mock
    private BuyerServiceImpl buyerService;

    @InjectMocks
    private BuyerServlet buyerServlet;

    @Test
    void doGetAllTest() {
        int size = 5;
        when(request.getPathInfo()).thenReturn(null);
        when(buyerService.getAll()).thenReturn(buyerList(size));
        buyerServlet.doGet(request, response);
        Mockito.verify(dtoMapper, times(size)).buyerToBuyerDTO(any(Buyer.class));
    }

    @Test
    void doGetTest() {
        when(request.getPathInfo()).thenReturn("/1");
        when(buyerService.get(Mockito.anyInt())).thenReturn(getTemplateBuyer(1));
        buyerServlet.doGet(request, response);
        Mockito.verify(dtoMapper, times(1)).buyerToBuyerDTO(any(Buyer.class));
    }

    @Test
    void doPostTest() throws IOException {
        ServletInputStream servletInputStream = getServletInputStream(getTextFromInputStream(
                OrderServletTest.class.getClassLoader().getResourceAsStream("buyer.json")));
        when(request.getInputStream()).thenReturn(servletInputStream);
        when(buyerService.save(any(Buyer.class))).thenReturn(getTemplateBuyer(1));
        when(dtoMapper.buyerDTOToBuyer(any(BuyerDTO.class))).thenReturn(getTemplateBuyer(1));
        buyerServlet.doPost(request, response);
        Mockito.verify(dtoMapper, times(1)).buyerDTOToBuyer(any(BuyerDTO.class));
        Mockito.verify(dtoMapper, times(1)).buyerToBuyerDTO(any(Buyer.class));
    }

    @Test
    void doPutTest() throws IOException {
        ServletInputStream servletInputStream = getServletInputStream(getTextFromInputStream(
                OrderServletTest.class.getClassLoader().getResourceAsStream("buyer.json")));
        when(request.getInputStream()).thenReturn(servletInputStream);
        buyerServlet.doPut(request, response);
        Mockito.verify(dtoMapper, times(1)).buyerDTOToBuyer(any(BuyerDTO.class));
        Mockito.verify(buyerService, times(1)).update(any());
    }

    @Test
    void doDeleteTest() {
        when(request.getParameter("id")).thenReturn("1");
        buyerServlet.doDelete(request, response);
        Mockito.verify(buyerService, times(1)).delete(1);
    }
}