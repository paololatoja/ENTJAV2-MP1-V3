package test;

import java.util.ArrayList;

import model.Product;

public class DeductStocks {
	
	private static ArrayList<Product> cart, products;
	
	public DeductStocks(ArrayList<Product> cart, ArrayList<Product> products) {
		cart = new ArrayList<Product>();
		products = new ArrayList<Product>();
		
		DeductStocks.cart = cart;
		DeductStocks.products = products;
		main();
	}
	
	public static void main() {
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
					System.out.println(p.getId() + " Stocks: " + p.getStocks());
					stocks = p.getStocks() - quantity;
					p.setStock(stocks);
					System.out.println(p.getId() + " Stocks Updated: " + p.getStocks());
					break;
				} else {
					System.out.println("PRODUCT NOT FOUND!");
				}
			}
		}
		
		// display all the product stocks
		for(Product p : products) {
			System.out.println("ID: " + p.getId());
			System.out.println("Stocks Left: " + p.getStocks() + "\n");
		}
	}
}
