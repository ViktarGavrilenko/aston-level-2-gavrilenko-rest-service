package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Order;
import org.example.services.impl.OrderServiceImpl;
import org.example.servlets.dto.OrderDTO;
import org.example.servlets.mapper.OrderDtoMapperImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.utils.StreamUtils.getTextFromInputStream;

@WebServlet(name = "OrderServlet", value = "/order/*")
public class OrderServlet extends HttpServlet {
    private OrderServiceImpl service = new OrderServiceImpl();
    private OrderDtoMapperImpl dtoMapper = new OrderDtoMapperImpl();
    private final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String str = req.getPathInfo();
        if (str == null) {
            List<Order> orders = service.getAll();
            List<OrderDTO> orderDTOS = new ArrayList<>();
            for (Order order : orders) {
                orderDTOS.add(dtoMapper.orderToOrderDTO(order));
            }

        } else {
            Order order = service.get(Integer.parseInt(str.substring(1)));
            if (order != null) {
                OrderDTO orderDTO = dtoMapper.orderToOrderDTO(order);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getTextFromInputStream(req.getInputStream());
        OrderDTO dto = MAPPER.readValue(json, OrderDTO.class);
        Order order = dtoMapper.orderDTOToOrder(dto);
        Order saved = service.save(order);
        OrderDTO itemDTO = dtoMapper.orderToOrderDTO(saved);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getTextFromInputStream(req.getInputStream());
        OrderDTO dto = MAPPER.readValue(json, OrderDTO.class);
        Order order = dtoMapper.orderDTOToOrder(dto);
        service.update(order);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        service.delete(id);
    }
}