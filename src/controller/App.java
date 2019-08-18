package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.Main;
import model.Order;
import model.Product;

import utility.Fireship;

public class App extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Declarations
	public static ArrayList<Product> products;
	public static ArrayList<Order> order_history;
	public static Order order;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		Fireship.log("-- Fireship Shopping Started --");
		
		Main app = new Main();
		app.extractFileContent();
		
		// Initialize Everything
		products = app.getProductList();
		
		if(products.size() < 1) {
			Fireship.log("There are currently no items to be initialized for the app.");
		} else {
			Fireship.log("Products initialized to Fireship!");
		}
		
		order_history = new ArrayList<Order>();
		order = new Order();
		
		// get the vat rate configurations from the servletcontext parameters and set the vat rate of the order object
		order.setVatRate(Float.parseFloat(getServletContext().getInitParameter("VAT_RATE")));
		 
		// Bind all to application
		ServletContext sc = getServletContext();
		sc.setAttribute("INITIAL", products);
		sc.setAttribute("ORDER", order);
		sc.setAttribute("ORDER_HISTORY", order_history);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("home.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
