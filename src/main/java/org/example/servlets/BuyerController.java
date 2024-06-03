package org.example.servlets;

import org.example.model.Buyer;
import org.example.services.impl.BuyerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/buyer/*")
public class BuyerController extends HttpServlet {
    private BuyerServiceImpl buyerService = new BuyerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String str = req.getPathInfo();
        PrintWriter printWriter = resp.getWriter();
        if (str == null) {
            List<Buyer> buyers = buyerService.getAll();
            printWriter.write("Hello! " + buyers.toString());
        } else {
            Buyer buyer = buyerService.get(Integer.parseInt(str.substring(1)));
            printWriter.write("Hello! " + buyer.toString());
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
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