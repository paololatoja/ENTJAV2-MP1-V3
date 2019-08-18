package model;

import java.util.ArrayList;

public class Product {
	
	private String 	id;
	private String 	name;
	private String 	imageName;
	private double 	price;
	private int 	stock;
	private int 	quantity; /* Quantity ordered */
	private int		rating;

	
	
	/*---------------------- CONSTRUCTOR ----------------------*/		
	public Product(String id, String name, String imageName, double price, int stock, int rating, int quantity) {
		this.id 		= id;
		this.name 		= name;
		this.imageName 	= imageName;
		this.price 		= price;
		this.stock 		= stock;
		this.rating		= rating;
		this.quantity	= quantity;
	}
	
	public Product(String id, String name, String imageName, double price, int stock, int rating) {
		this.id 		= id;
		this.name 		= name;
		this.imageName 	= imageName;
		this.price 		= price;
		this.stock 		= stock;
		this.rating		= rating;
	}

	/*---------------------- GETTERS ----------------------*/
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getImagePath() {
		return imageName;
	}
	public double getPrice() {
		return price;
	}
	public int getStocks() {
		return stock;
	}
	public int getQuantity() {
		return quantity;
	}	
	public int getRating() {
		return rating;
	}

	/*---------------------- SETTERS ----------------------*/
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setImagePath(String imagePath) {
		this.imageName = imagePath;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	/*---------------------- METHODS ----------------------*/
}
