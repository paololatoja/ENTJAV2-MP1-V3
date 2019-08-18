package model;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.descriptor.JspConfigDescriptor;

import exceptions.*;

public class Order implements FireshipOnlineOrdering {
	
	private User user;												// User
	private ArrayList<Product> cart = new ArrayList<Product>();		// Shopping Cart Store
	private float vatRate;											// default rate = 12%
	private double VAT;												// gross_price + (grossprice x 12%)
	
	private String dateOrdered;
	
	// Validation
	private boolean creditCardStatus = false;
	
	// Computed Fields
	private double grossPrice; 		// (SUBTOTAL)
	private double netPrice;		// (SUBTOTAL + VAT_PRICE)
	
	
	
	
	
	// Def. Constructor
	public Order() {
	}
	
	public void resetOrder(float vat_rate) {
		this.user = null;
		this.VAT = 0;
		this.dateOrdered = null;
		this.creditCardStatus = false;
		this.grossPrice = 0;
		this.netPrice = 0;
		setVatRate(vat_rate);
		updatePrice();
	}
	
	
	
	
	
	
	/* -------------------------------------------- INTERFACE METHODS -------------------------------------------- */
	@Override
	public void validateCreditCard() {
		try {
			String creditCard = user.getCreditCardNumber();
			
			if(creditCard.length() == 16) {
				int sum = 0;
		        int digit = 0;
		        
		        for(int i=0 ; i < creditCard.length(); i++) {
		        	// get the numeric int value of the creditCart character
		            digit = Character.getNumericValue(creditCard.charAt(i));
		            
		            if(i % 2 == 0) { // even INDEX position (first, third, fifth, ...)
		            	if((digit * 2) > 9) { 															// if the double of a number is composed of 2 digits...
		            		int firstD = Integer.parseInt(Integer.toString(digit * 2).substring(0, 1)); // then get the first digit...
		            		int secondD = Integer.parseInt(Integer.toString(digit * 2).substring(1));	// then get the second digit...
		            		int tempSum = firstD + secondD; 											// then get the sum of the two digits...
		            		sum += tempSum; 															// then store
		            		
		            	} else {
		            		sum += digit; // store it right away
		            	}
		            	
		            } else {
		            	sum += digit; // if the number is in odd INDEX position (second, fourth, sixth, ...) , store it right away 
		            }
		        }
		        
		        // if the final sum of the process is divisible by 10, the credit card is valid...
		        if (sum%10 == 0) {
		        	creditCardStatus = true;
		        } else {
		    		// INVALID CREDIT CARD
		        	throw new InvalidCardNumberException();
		        } 
		        
			} else {
				// INVALID NUMBER LENGTH
				throw new InvalidCardLengthException(); //CreditCard should be (STRICTLY) composed of 16 digits
			}
		
		} catch (InvalidCardNumberException icne) {
			System.err.println(icne.getMessage());
		} catch (InvalidCardLengthException icle) {
			System.err.println(icle.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Exception error printStackTrace: " + e.getMessage());
		}
		
	}
	@Override
	public void computeGrossPay() {
		for(Product item : cart) {
			grossPrice += (item.getPrice() * item.getQuantity());
		}
	}
	@Override
	public void computeVAT() {
		VAT = grossPrice * vatRate;
	}
	@Override
	public void generatePDFReceipt() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void netPay() {
		netPrice = grossPrice + VAT;
	}
	@Override
	public void printPDFSalesReport() {
		
	}
	/* ---------------------------------------------------------------------------------------------------------- */
	
	
	
	
	
	

	// GET CART ARRAY LIST
	public ArrayList<Product> getCart() {
		return cart;
	}
	public User getUser() {
		return this.user;
	}
	
	// setter for 'vatRate'
	public void setVatRate(float rate) {
		this.vatRate = (rate / 100);
	}
	
	
	
	public String getSubtotal() {
		return String.format("%,.2f", grossPrice);
	}
	public String getVAT() {
		return String.format("%,.2f", VAT);
	}
	public String getTotal() {
		return String.format("%,.2f", netPrice);
	}
	
	
	
	// CART OPERATIONS (ADD & REMOVE ITEMS) ----------------------------------------------------------------------------------------------------------------------- >
	public boolean removeItemFromCart(Product item) {		
		if(cart.remove(item)) {
			System.out.println("( "+ new SimpleDateFormat().format(new Date()) + " ) Successfully REMOVED an item from the cart!");
			updatePrice();
			return true;
		}
		
		return false;
	}
	
	public boolean addItemToCart(Product item) {
		if(cart.add(item)) {
			System.out.println("( "+ new SimpleDateFormat().format(new Date()) + " ) Successfully added an item added the cart!");
			System.out.println("( "+ new SimpleDateFormat().format(new Date()) + " ) Shopping Cart UPDATED!");
			updatePrice();
			return true; //success
		}
		
		return false; //failed
	}
	// ------------------------------------------------------------------------------------------------------------------------------------------------------------ >
	
	
	
	
	
	
	
	private void setOrderDate(Date date) {
		this.dateOrdered = new SimpleDateFormat().format(new Date());
	}
	public String getOrderDate() {
		return this.dateOrdered;
	}
	
	private void updatePrice() {
		//reset
		grossPrice = 0;
		VAT = 0;
		
		computeGrossPay();
		computeVAT();
		netPay();
	}
	
	// TRIGGERED WHEN PAY BUTTON IS CLICKED ----------------------------------------------------------------------------------------------------------------------- >
	public boolean processOrder(User user) {
		// get user
		this.user = user;
		
		// validate user credit card
		this.validateCreditCard();
		
		if(creditCardStatus) {
			// set date Ordered
			setOrderDate(new Date());
			return true;
		}
		
		return false;
	}
	
	public ArrayList<Product> updateStocks(ArrayList<Product> cart, ArrayList<Product> products) {
		Product item = null;
		String id = null;
		int quantity = 0;
		int stocks;
		
		for(int index=0; index < cart.size(); ++index) { //for every item in the cart get its quantity
			item = cart.get(index);
			id = item.getId();
			quantity = item.getQuantity();
			
			// find the item in the product list
			for(Product p : products) {
				if(id.equals(p.getId())) {
					System.out.println("\nITEM FOUND!");
					System.out.println("ID: " + p.getId());
					System.out.println("Stocks: " + p.getStocks());
					stocks = p.getStocks() - quantity;
					p.setStock(stocks);
					System.out.println(p.getId() + " Stock Quantity Updated: " + p.getStocks());
					break;
				}
			}
		}
		
		// display all the product stocks
		System.out.println("\nUpdated Product List::::::::::::::::::::::::::::::::::::::::::::");
		for(Product p : products) {
			System.out.println("ID: " + p.getId());
			System.out.println("Stocks Left: " + p.getStocks() + "\n");
		}
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		
		return products;
	}
}
