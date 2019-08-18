package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.bind.v2.TODO;

import model.Order;
import model.Product;
import model.User;
import test.OrderHistoryTesting;
import utility.Fireship;

public class ProcessOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("home.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName;
		String lastName;
		String creditCardNumber;
		String shippingAddress;
		
		firstName = request.getParameter("user_firstname").trim();
		lastName = request.getParameter("user_lastname").trim();
		shippingAddress = request.getParameter("shipping_address").trim();
		creditCardNumber = request.getParameter("creditCard").trim();
		
		User newUser = new User(firstName, lastName, creditCardNumber, shippingAddress);
		
		Fireship.log("PROCESSING ORDER...");
		
		// get the order object
		Order order = (Order) getServletContext().getAttribute("ORDER");
				
		if(order.processOrder(newUser)) {
			Fireship.log("Order Transaction Success. [Order date]: " + order.getOrderDate());
			ArrayList<Product> products = (ArrayList<Product>) getServletContext().getAttribute("INITIAL");
			ArrayList<Order> order_history = (ArrayList<Order>) getServletContext().getAttribute("ORDER_HISTORY");
			
			// record/add order to history
			order_history.add(order);
		
			// update stocks of each product
			products = order.updateStocks(order.getCart(), products);
			
			// reset order object to prepare for the next order
			Order newOrder = new Order();
			newOrder.resetOrder(Float.parseFloat(getServletContext().getInitParameter("VAT_RATE")));
			
			
			// TEST =================================================================================================================================================
			// new OrderHistoryTesting().main(order_history);
			// TEST =================================================================================================================================================
			
			
			// set the new order object to the servlet context else
			// it will keep the data of the previous order regardless if it's valid or invalid
			getServletContext().setAttribute("ORDER", newOrder);
			getServletContext().setAttribute("INITIAL", products);
			getServletContext().setAttribute("ORDER_HISTORY", order_history);
			
			response.sendRedirect("home.jsp");
			
		} else {
			Fireship.log("ORDER FAILED");
			
			// redirect Invalid.jsp (go back to cart or cancel?)
			response.sendRedirect("cart.jsp");
		}
	}

}
