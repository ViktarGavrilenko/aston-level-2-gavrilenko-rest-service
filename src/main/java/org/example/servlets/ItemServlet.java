package org.example.servlets;

import org.example.model.Item;
import org.example.services.impl.ItemServiceImpl;
import org.example.servlets.dto.ItemDTO;
import org.example.servlets.mapper.ItemDtoMapperImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ItemServlet", value = "/item/*")
public class ItemServlet extends HttpServlet {
    private ItemServiceImpl service = new ItemServiceImpl();
    private ItemDtoMapperImpl dtoMapper = new ItemDtoMapperImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String str = req.getPathInfo();
        PrintWriter printWriter = resp.getWriter();
        if (str == null) {
            List<Item> items = service.getAll();
            printWriter.write(items.toString());
        } else {
            Item item = service.get(Integer.parseInt(str.substring(1)));
            printWriter.write(item.toString());
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int price = Integer.parseInt(req.getParameter("price"));
        Item item = dtoMapper.itemDTOToItem(new ItemDTO(id, name, price));
        Item saved = service.save(item);
        ItemDTO itemDTO = dtoMapper.itemToItemDTO(saved);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(itemDTO.toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int price = Integer.parseInt(req.getParameter("price"));
        Item item = dtoMapper.itemDTOToItem(new ItemDTO(id, name, price));
        service.update(item);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        service.delete(id);
    }
}