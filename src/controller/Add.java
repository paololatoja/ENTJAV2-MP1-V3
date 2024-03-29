package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Order;
import model.Product;

public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Order order;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name 		= request.getParameter("item_name").trim();
		Double price 		= Double.parseDouble(request.getParameter("item_price").trim());
		int quantity 		= Integer.parseInt(request.getParameter("quantity").trim());
		String id 			= request.getParameter("item_id").trim();
		int rating 			= Integer.parseInt(request.getParameter("item_rating").trim());
		int stocks 			= Integer.parseInt(request.getParameter("item_stocks").trim());
		String imageName	= request.getParameter("item_image").trim();
		
		// set item to be added to cart
		Product item = new Product(id, name, imageName, price, stocks, rating, quantity);
		
		// get cart from app context
		order = (Order) getServletContext().getAttribute("ORDER");
		
		if(order.addItemToCart(item)) {
			response.sendRedirect("home.jsp");
		}
	}

}
