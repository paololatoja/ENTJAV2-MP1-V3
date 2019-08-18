package test;

import java.util.ArrayList;
import java.util.Iterator;

import model.Order;
import model.Product;

public class OrderHistoryTesting {

	public static void main(ArrayList<Order> history) {
		System.out.println("ORDER HISTORY >>>");
		Order order;
		ArrayList<Product> cart;
		Product item;
		
		for(int index=0; index < history.size(); ++index) {
			order = history.get(index);
			cart = order.getCart();
			
			System.out.println("\n[" + (index+1) + "] ORDER ------------------------------------------------------------------------------------------- ");
			System.out.println("\t[ CUSTOMER DETAILS ]: \n");
			System.out.println("\tFIRST_NAME:" + order.getUser().getFirstName());
			System.out.println("\tLAST_NAME:" + order.getUser().getLastName());
			System.out.println("\tSHIPPING ADDRESS:" + order.getUser().getShippingAddress());
			
			System.out.println("\n\t[ CART ]: \n");
			for(int counter=0; counter < cart.size(); ++counter) {
				item = cart.get(counter);
				System.out.println("\t[" + counter + "]");
				System.out.println("\tID: " + item.getId());
				System.out.println("\tITEM_NAME: " + item.getName());
				System.out.println("\tITEM_PRICE:" + item.getPrice() + "\n");				
			}
			
		}
	}
}
