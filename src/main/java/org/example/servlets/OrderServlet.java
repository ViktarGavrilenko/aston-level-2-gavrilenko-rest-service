package org.example.servlets;

import org.example.model.Item;
import org.example.model.Order;
import org.example.repository.impl.ItemRepositoryImpl;
import org.example.services.impl.OrderServiceImpl;
import org.example.servlets.dto.OrderDTO;
import org.example.servlets.mapper.OrderDtoMapperImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/order/*")
public class OrderServlet extends HttpServlet {
    OrderServiceImpl service = new OrderServiceImpl();
    private OrderDtoMapperImpl dtoMapper = new OrderDtoMapperImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String str = req.getPathInfo();
        PrintWriter printWriter = resp.getWriter();
        if (str == null) {
            List<Order> items = service.getAll();
            printWriter.write(items.toString());
        } else {
            Order order = service.get(Integer.parseInt(str.substring(1)));
            if (order != null) {
                printWriter.write(order.toString());
            }
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
/*        int number = Integer.parseInt(req.getParameter("number"));
        List<Item> items = (List<Item>) req.getAttribute("items");*/
        //
        int id = (int) (Math.random() * 200);
        int number = (int) (Math.random() * 10);
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            items.add(new Item(i, "something " + i, i + 8));
        }
        //
        Order order = dtoMapper.orderDTOToOrder(new OrderDTO(id, number, items));
        Order saved = service.save(order);
        OrderDTO itemDTO = dtoMapper.orderToOrderDTO(saved);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(itemDTO.toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        /*        int number = Integer.parseInt(req.getParameter("number"));
        List<Item> items = (List<Item>) req.getAttribute("items");*/
        //
        int id = 1;
        int number = 1;
        List<Item> items = new ArrayList<>();
        ItemRepositoryImpl itemRepository = new ItemRepositoryImpl();
        for (int i = 1; i < 2; i++) {
            items.add(itemRepository.get(i));
        }
        //
        Order order = dtoMapper.orderDTOToOrder(new OrderDTO(id, number, items));
        service.update(order);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        service.delete(id);
    }
}