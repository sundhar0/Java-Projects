package main;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.OrderProcessor;
import model.Order;
import model.Product;
import model.User;
import util.Connectionhelper;

public class OrderManagement {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		
		User user = new User();
		Product product = new Product();
		List<Product> products = new ArrayList<>();
		
		OrderProcessor orderProcessor = new OrderProcessor();
		
		
		
		while(true) {
			System.out.println("1. Create a New User");
			System.out.println("2. Create a product");
			System.out.println("3. Make an Order");
			System.out.println("4. Cancel Order");
			System.out.println("5. View all Product");
			System.out.println("6. View Order by User");
			System.out.println("7. Exit");
			int choice = sc.nextInt();
			switch(choice) {
			
			case 1 -> {
			    System.out.println("Enter User ID:");
			    int userId = sc.nextInt();
			    sc.nextLine(); 

			    System.out.println("Enter Username:");
			    String username = sc.nextLine();

			    System.out.println("Enter Password:");
			    String password = sc.nextLine();

			    System.out.println("Enter Role (Admin/User):");
			    String role = sc.nextLine();

			    user = new User(userId, username, password, role);

			    Connection conn = Connectionhelper.getConnection(); 
			    orderProcessor.createUser(conn, user);

			    System.out.println("User created successfully!");
			    break;
			}

			
			case 2 -> {
			    System.out.println("Enter User ID:");
			    int userId = sc.nextInt();
			    sc.nextLine(); 

			    System.out.println("Enter Username:");
			    String username = sc.nextLine();

			    System.out.println("Enter Password:");
			    String password = sc.nextLine();

			    System.out.println("Enter Role (Admin/User):");
			    String role = sc.nextLine();

			    user = new User(userId, username, password, role);

			    Connection conn = Connectionhelper.getConnection(); 
			    System.out.println("Enter Product ID:");
			    int productId = sc.nextInt();
			    sc.nextLine(); 

			    System.out.println("Enter Product Name:");
			    String productName = sc.nextLine();

			    System.out.println("Enter Description : ");
			    String description = sc.nextLine();

			    System.out.println("Enter Price : ");
			    double price = sc.nextDouble();

			    System.out.println("Enter Quantity in Stock : ");
			    int quantityInStock = sc.nextInt();
			    sc.nextLine();

			    System.out.println("Product type (Electronics/Clothing) : ");
			    String type = sc.nextLine();

			    product = new Product(productId, productName, description, price, quantityInStock, type);

			    orderProcessor.createProduct(conn, user, product);

			    System.out.println("Product added successfully!");
			}

			
			case 3 -> { 
			    System.out.println("Enter User ID:");
			    int userId = sc.nextInt();
			    sc.nextLine(); 

			    System.out.println("Enter Username:");
			    String username = sc.nextLine();

			    System.out.println("Enter Password:");
			    String password = sc.nextLine();

			    System.out.println("Enter Role (Admin/User):");
			    String role = sc.nextLine();

			    user = new User(userId, username, password, role);

			    System.out.println("Enter the number of products to add:");
			    int productCount = sc.nextInt();

			    List<Product> products1 = new ArrayList<>();
			    for (int i = 0; i < productCount; i++) {
			        System.out.println("Enter Product ID:");
			        int productId = sc.nextInt();
			        sc.nextLine(); 

			        System.out.println("Enter Product Name:");
			        String productName = sc.nextLine();

			        System.out.println("Enter Description : ");
			        String description = sc.nextLine();

			        System.out.println("Enter Price : ");
			        double price = sc.nextDouble();

			        System.out.println("Enter Quantity in Stock : ");
			        int quantityInStock = sc.nextInt();
			        sc.nextLine();

			        System.out.println("Product type (Electronics/Clothing) : ");
			        String type = sc.nextLine();

			        products1.add(new Product(productId, productName, description, price, quantityInStock, type));
			    }

			    Order order = new Order(userId, products1); 

			    Connection conn = Connectionhelper.getConnection(); 
			    orderProcessor.createOrder(conn, order); 
			    System.out.println("Order created successfully!");
			}

			
			case 4->{
				System.out.println("Enter Product ID:");
		        int userId = sc.nextInt();

		        System.out.println("Enter Order ID to cancel:");
		        int orderId = sc.nextInt();

		        orderProcessor.cancelOrder(userId, orderId);
		        

			}
			
			case 5->{
				System.out.println("Available Products:");
		        for (Product product1 : products) {
		            System.out.println("ID: " + product1.getProductId() +
		                               ", Name: " + product1.getProductName() +
		                               ", Description: " + product1.getDescription() +
		                               ", Price: " + product1.getPrice() +
		                               ", Quantity: " + product1.getQuantityInStock() +
		                               ", Type: " + product1.getType());
		        }
			}
			
			case 6->{
				System.out.println("Enter User ID:");
		        int userId = sc.nextInt();
		        sc.nextLine(); 

		        System.out.println("Enter Username:");
		        String username = sc.nextLine();

		        System.out.println("Enter Password:");
		        String password = sc.nextLine();

		        System.out.println("Enter Role (Admin/Customer):");
		        String role = sc.nextLine();

		        user = new User(userId, username, password, role);
		        products = orderProcessor.getOrderByUser(user);

		        System.out.println("Products Ordered by User:");
		        for (Product product1 : products) {
		            System.out.println("ID: " + product1.getProductId() +
		                               ", Name: " + product1.getProductName() +
		                               ", Description: " + product1.getDescription() +
		                               ", Price: " + product1.getPrice() +
		                               ", Quantity: " + product1.getQuantityInStock() +
		                               ", Type: " + product1.getType());
		        }
			}
			
			case 7->{
				System.out.println("Exit SucessFully...");
				System.exit(0);
			}
			
			default -> System.out.println("Invalid choice. Please try again.");
			
			}
		}
	}
}
