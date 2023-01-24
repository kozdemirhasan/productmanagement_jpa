package de.kozdemir.myproductmanagement;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/api")
public class ApiServlet extends HttpServlet {

    private ProductRepository repository = new ProductRepository();
    private Gson gson = new Gson();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        List<Product> products;
        final String search = request.getParameter("s");

        if(search != null) {
            products = repository.find(search);
        }
        else {
            products = repository.find();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Übersetzt die Produkt-Liste in JSON
        response.getWriter().write(gson.toJson(products));
    }
}