package org.example.servlets;

import org.example.model.Order;
import org.example.services.impl.OrderServiceImpl;
import org.example.servlets.dto.OrderDTO;
import org.example.servlets.mapper.OrderDtoMapperImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.example.services.impl.OrderServiceImplTest.getTemplateOrder;
import static org.example.services.impl.OrderServiceImplTest.orderList;
import static org.example.utils.StreamUtils.getTextFromInputStream;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private OrderDtoMapperImpl dtoMapper;

    @Mock
    private OrderServiceImpl orderService;

    @InjectMocks
    private OrderServlet orderServlet;

    @Test
    void doGetAllTest() {
        int size = 5;
        when(request.getPathInfo()).thenReturn(null);
        when(orderService.getAll()).thenReturn(orderList(size));
        orderServlet.doGet(request, response);
        Mockito.verify(dtoMapper, times(size)).orderToOrderDTO(any(Order.class));
    }

    @Test
    void doGetTest() {
        when(request.getPathInfo()).thenReturn("/1");
        when(orderService.get(Mockito.anyInt())).thenReturn(getTemplateOrder(1));
        orderServlet.doGet(request, response);
        Mockito.verify(dtoMapper, times(1)).orderToOrderDTO(any(Order.class));
    }

    @Test
    void doPostTest() throws IOException {
        ServletInputStream servletInputStream = getServletInputStream(getTextFromInputStream(
                OrderServletTest.class.getClassLoader().getResourceAsStream("order.json")));
        when(request.getInputStream()).thenReturn(servletInputStream);
        when(orderService.save(any(Order.class))).thenReturn(getTemplateOrder(1));
        when(dtoMapper.orderDTOToOrder(any(OrderDTO.class))).thenReturn(getTemplateOrder(1));
        orderServlet.doPost(request, response);
        Mockito.verify(dtoMapper, times(1)).orderDTOToOrder(any(OrderDTO.class));
        Mockito.verify(dtoMapper, times(1)).orderToOrderDTO(any(Order.class));
    }

    @Test
    void doPutTest() throws IOException {
        ServletInputStream servletInputStream = getServletInputStream(getTextFromInputStream(
                OrderServletTest.class.getClassLoader().getResourceAsStream("order.json")));
        when(request.getInputStream()).thenReturn(servletInputStream);
        orderServlet.doPut(request, response);
        Mockito.verify(dtoMapper, times(1)).orderDTOToOrder(any(OrderDTO.class));
        Mockito.verify(orderService, times(1)).update(any());
    }

    @Test
    void doDeleteTest() {
        when(request.getParameter("id")).thenReturn("1");
        orderServlet.doDelete(request, response);
        Mockito.verify(orderService, times(1)).delete(1);
    }

    public static @NotNull ServletInputStream getServletInputStream(String body) {
        ByteArrayInputStream textBody = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() {
                return textBody.read();
            }
        };
        return servletInputStream;
    }
}