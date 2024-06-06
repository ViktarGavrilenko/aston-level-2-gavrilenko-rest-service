package org.example.servlets;

import org.example.model.Buyer;
import org.example.model.Item;
import org.example.model.Order;
import org.example.repository.impl.ItemRepositoryImpl;
import org.example.services.impl.BuyerServiceImpl;
import org.example.servlets.dto.BuyerDTO;
import org.example.servlets.mapper.BuyerDtoMapperImpl;

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
            if (buyer != null) {
                printWriter.write(buyer.toString());
            }
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //
        int id = 50;
        String name = "SomeName";
        List<Integer> orders = new ArrayList<>();
        orders.add(3);
        orders.add(6);
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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        service.delete(id);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        int id = 2;
        String name = "Дмитрий";
        List<Integer> orders = new ArrayList<>();
        ItemRepositoryImpl itemRepository = new ItemRepositoryImpl();

/*        int idOrder = 94;
        int number = 333;
        List<Item> items = new ArrayList<>();

        for (int j = 1; j < 3; j++) {
            items.add(itemRepository.get(j));
        }
        orders.add(new Order(idOrder, number, items));

        int idOrder2 = 93;
        int number2 = 75;
        List<Item> items2 = new ArrayList<>();
        for (int j = 1; j < 2; j++) {
            items2.add(itemRepository.get(j));
        }
        orders.add(new Order(idOrder2, number2, items2));*/

        int idOrder3 = 97;
        int number3 = 9777;
        List<Item> items3 = new ArrayList<>();
        for (int j = 4; j < 6; j++) {
            items3.add(itemRepository.get(j));
        }
        orders.add(4);


        //
        Buyer buyer = dtoMapper.buyerDTOToBuyer(new BuyerDTO(id, name, orders));
        service.update(buyer);
    }
}