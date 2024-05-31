package org.example.servlets;

import org.example.dto.BuyerDTO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.example.db.MySqlUtil.getFirstColumn;

@WebServlet("/buyer/*")
public class BuyerController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String str = req.getPathInfo();
        System.out.println(str);
        String id;
        if (str == null) {
            List<BuyerDTO> buyerDTOS = getAll();
        } else {
            id = "id=" + str.substring(1);
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write("Hello! " + );
        printWriter.close();
    }

}