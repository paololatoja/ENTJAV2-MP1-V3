package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Order;
import model.Product;

import utility.Fireship;

public class Remove extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int index = Integer.parseInt(request.getParameter("item_index"));
		
		Order order				= (Order) getServletContext().getAttribute("ORDER");
		ArrayList<Product> cart = order.getCart();
		
		Fireship.log("Removing item from cart: [item-id]: " + cart.get(index).getId() + ", [cart-index]: item " + index);
		order.removeItemFromCart(cart.get(index));
		
		// go back to cart
		response.sendRedirect("cart.jsp");
	}

}
