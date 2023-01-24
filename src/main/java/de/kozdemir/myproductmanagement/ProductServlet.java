package de.kozdemir.myproductmanagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = {"", "/products"})
public class ProductServlet extends HttpServlet {

    private ProductRepository repository = new ProductRepository();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final String action = request.getParameter("a");

        if(action != null && action.equals("form")) {
            getServletContext().getRequestDispatcher("/WEB-INF/form.jsp").forward(request, response);
        }
        else {
            final String search = request.getParameter("s");
            if(search != null) {
                request.setAttribute("products", repository.find(search));
            }
            else {
                request.setAttribute("products", repository.find());
            }
            getServletContext().getRequestDispatcher("/WEB-INF/list.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final String name = request.getParameter("name");
        final String description = request.getParameter("description");
        final double price = Double.parseDouble(request.getParameter("price"));

        repository.save(new Product(name, description, price)); // Speichern

        response.sendRedirect("products"); // Auf die Übersicht umleiten
    }





}
