package org.example.servlets;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpPost;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.example.services.impl.BuyerServiceImplTest.buyerList;
import static org.example.services.impl.BuyerServiceImplTest.getTemplateBuyer;
import static org.example.utils.StreamUtils.getTextFromInputStream;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    BuyerDtoMapperImpl dtoMapper;

    @Mock
    BuyerServiceImpl buyerService;

    @InjectMocks
    BuyerServlet buyerServlet;

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
    void doPost() throws IOException {
/*        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String json = getTextFromInputStream(req.getInputStream());
            BuyerDTO dto = MAPPER.readValue(json, BuyerDTO.class);

            Buyer buyer = dtoMapper.buyerDTOToBuyer(dto);
            Buyer saved = service.save(buyer);
            BuyerDTO buyerDTO = dtoMapper.buyerToBuyerDTO(saved);
        }*/
        String initialString = "{\n" +
                "  \"id\": 0,\n" +
                "  \"name\": \"Илья\",\n" +
                "  \"orders\": [\n" +
                "    1,\n" +
                "    2,\n" +
                "    3\n" +
                "  ]\n" +
                "}";
        InputStream inputStream = new ByteArrayInputStream(initialString.getBytes());
        when(request.getInputStream()).thenReturn((ServletInputStream) inputStream);
    }

    @Test
    void doPut() {
    }

    @Test
    void doDelete() {
    }
}