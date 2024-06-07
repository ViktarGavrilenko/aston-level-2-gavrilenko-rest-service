package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;

import static org.example.utils.StreamUtils.getJsonFromRequest;

@WebServlet(name = "ItemServlet", value = "/item/*")
public class ItemServlet extends HttpServlet {
    private static ItemServiceImpl service = new ItemServiceImpl();
    private static ItemDtoMapperImpl dtoMapper = new ItemDtoMapperImpl();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String str = req.getPathInfo();
        if (str == null) {
            List<Item> items = service.getAll();
            List<ItemDTO> itemDTOS = new ArrayList<>();
            for (Item item : items) {
                itemDTOS.add(dtoMapper.itemToItemDTO(item));
            }
        } else {
            Item item = service.get(Integer.parseInt(str.substring(1)));
            if (item != null) {
                ItemDTO itemDTO = dtoMapper.itemToItemDTO(item);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getJsonFromRequest(req);
        ItemDTO dto = MAPPER.readValue(json, ItemDTO.class);
        Item item = dtoMapper.itemDTOToItem(dto);
        Item saved = service.save(item);
        ItemDTO itemDTO = dtoMapper.itemToItemDTO(saved);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getJsonFromRequest(req);
        ItemDTO dto = MAPPER.readValue(json, ItemDTO.class);
        Item item = dtoMapper.itemDTOToItem(dto);
        service.update(item);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        service.delete(id);
    }
}