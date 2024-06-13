package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Buyer;
import org.example.services.impl.BuyerServiceImpl;
import org.example.servlets.dto.BuyerDTO;
import org.example.servlets.mapper.BuyerDtoMapperImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.utils.StreamUtils.getTextFromInputStream;

@WebServlet(name = "BuyerServlet", value = "/buyer/*")
public class BuyerServlet extends HttpServlet {
    private BuyerServiceImpl buyerService = new BuyerServiceImpl();
    private BuyerDtoMapperImpl dtoMapper = new BuyerDtoMapperImpl();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String str = req.getPathInfo();
        if (str == null) {
            List<Buyer> buyers = buyerService.getAll();
            List<BuyerDTO> buyerDTOS = new ArrayList<>();
            for (Buyer buyer : buyers) {
                buyerDTOS.add(dtoMapper.buyerToBuyerDTO(buyer));
            }
        } else {
            Buyer buyer = buyerService.get(Integer.parseInt(str.substring(1)));
            if (buyer != null) {
                BuyerDTO buyerDTO = dtoMapper.buyerToBuyerDTO(buyer);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getTextFromInputStream(req.getInputStream());
        BuyerDTO dto = mapper.readValue(json, BuyerDTO.class);
        Buyer buyer = dtoMapper.buyerDTOToBuyer(dto);
        Buyer saved = buyerService.save(buyer);
        BuyerDTO buyerDTO = dtoMapper.buyerToBuyerDTO(saved);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        buyerService.delete(id);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getTextFromInputStream(req.getInputStream());
        BuyerDTO dto = mapper.readValue(json, BuyerDTO.class);
        Buyer buyer = dtoMapper.buyerDTOToBuyer(dto);
        buyerService.update(buyer);
    }
}