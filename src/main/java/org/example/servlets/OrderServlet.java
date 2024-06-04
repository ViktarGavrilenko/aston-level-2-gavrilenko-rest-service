package org.example.servlets;

import org.example.model.Item;
import org.example.model.Order;
import org.example.services.impl.OrderServiceImpl;
import org.example.servlets.dto.OrderDTO;
import org.example.servlets.mapper.OrderDtoMapperImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
            printWriter.write(order.toString());
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int number = Integer.parseInt(req.getParameter("number"));
        List<Item> items = (List<Item>) req.getAttribute("items");
        Order order = dtoMapper.orderDTOToOrder(new OrderDTO(number, items));
        Order saved = service.save(order);
        OrderDTO itemDTO = dtoMapper.orderToOrderDTO(saved);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(itemDTO.toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
