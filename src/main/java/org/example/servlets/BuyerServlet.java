package org.example.servlets;

import org.example.model.Buyer;
import org.example.model.Item;
import org.example.model.Order;
import org.example.repository.impl.ItemRepositoryImpl;
import org.example.services.impl.BuyerServiceImpl;
import org.example.servlets.dto.BuyerDTO;
import org.example.servlets.mapper.BuyerDtoMapperImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BuyerServlet", value = "/buyer/*")
public class BuyerServlet extends HttpServlet {
    private BuyerServiceImpl buyerService = new BuyerServiceImpl();
    private BuyerDtoMapperImpl dtoMapper = new BuyerDtoMapperImpl();
    private BuyerServiceImpl service = new BuyerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String str = req.getPathInfo();
        PrintWriter printWriter = resp.getWriter();
        if (str == null) {
            List<Buyer> buyers = buyerService.getAll();
            printWriter.write(buyers.toString());
        } else {
            Buyer buyer = buyerService.get(Integer.parseInt(str.substring(1)));
            printWriter.write(buyer.toString());
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //

        int id = 50;
        String name = "SomeName";
        List<Order> orders = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        ItemRepositoryImpl itemRepository = new ItemRepositoryImpl();
        items.add(itemRepository.get(3));
        items.add(itemRepository.get(5));
        orders.add(new Order(200, 300, items));
        List<Item> items2 = new ArrayList<>();
        items2.add(itemRepository.get(1));
        items2.add(itemRepository.get(4));
        orders.add(new Order(201, 301, items2));

/*        for (int i = 1; i < 3; i++) {
            int idOrder = i;
            int number = (int) Math.random() * 20;
            List<Item> items = new ArrayList<>();
            ItemRepositoryImpl itemRepository = new ItemRepositoryImpl();
            for (int j = 1; j < 4; j++) {
                items.add(itemRepository.get(j));
            }
            orders.add(new Order(idOrder, number, items));
        }*/

        //
        Buyer buyer = dtoMapper.buyerDTOToBuyer(new BuyerDTO(id, name, orders));
        Buyer saved = service.save(buyer);
        BuyerDTO buyerDTO = dtoMapper.buyerToBuyerDTO(saved);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(buyerDTO.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }
}