package by.dre.je.jdbc.servlet;

import by.dre.je.jdbc.service.FlightService;
import by.dre.je.jdbc.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/content")
public class ContentServlet extends HttpServlet {

    FlightService flightService = FlightService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var flights = flightService.findAll();
        req.setAttribute("flights", flights);
        String path = JspHelper.getPath("content");
        req.getRequestDispatcher(path).forward(req, resp);
    }
}
