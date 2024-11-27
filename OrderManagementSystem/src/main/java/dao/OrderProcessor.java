package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.OrderNotFoundException;
import exception.UserNotFoundException;
import model.Order;
import model.Product;
import model.User;
import util.Connectionhelper;

public class OrderProcessor implements IOrdermanagementRepository {

	public static final String CREATE_ORDER = "INSERT INTO order_products (productId) VALUES (?)";
	private static final String IS_USER_EXISTS = "SELECT COUNT(*) FROM User WHERE userId = ?";
	private static final String IS_ORDER_EXISTS = "SELECT COUNT(*) FROM `order` WHERE orderId = ?";
	public static final String CANCEL_ORDER = "DELETE FROM order_products WHERE orderId = ? AND productId = ?";
	private static final String IS_ADMIN_USER = "SELECT role FROM User WHERE userId = ?";
	public static final String CREATE_PRODUCT = "INSERT INTO products (name, price) VALUES (?, ?)";
	private static final String CREATE_USER = "INSERT INTO User (userId, username, password, role) VALUES (?, ?, ?, ?)";
	private static final String GET_ORDER_BY_USER = "SELECT p.* FROM Product p JOIN Order_Products op ON p.productId = op.productId JOIN `Order` o ON op.orderId = o.orderId WHERE o.userId = ?";
	private static final String GET_ALL_PRODUCT = "SELECT * FROM Product";
	

	 public void createOrder(Connection mockConnection, Order order) throws ClassNotFoundException, SQLException {
	        Connection conn = Connectionhelper.getConnection();
	        if (!isUserExists(conn, ((Order) mockConnection).getUserId())) {
	            createUser(mockConnection, null);
	        }
	        
	        PreparedStatement stmt = conn.prepareStatement(CREATE_ORDER);
	        stmt.setInt(1, ((Order) mockConnection).getUserId());  

	    }


	 public void cancelOrder(int orderId, int productId) throws SQLException, UserNotFoundException, ClassNotFoundException {
		    Connection connection = null;
		    PreparedStatement preparedStatement = null;
		    ResultSet resultSet = null;

		    try {
		        connection = Connectionhelper.getConnection();

		        String checkProductSQL = "SELECT * FROM order_products WHERE orderId = ? AND productId = ?";
		        preparedStatement = connection.prepareStatement(checkProductSQL);
		        preparedStatement.setInt(1, orderId);
		        preparedStatement.setInt(2, productId);

		        resultSet = preparedStatement.executeQuery();
		        if (!resultSet.next()) {
		            throw new UserNotFoundException("Order with ID " + orderId + " and Product ID " + productId + " not found.");
		        }

		        preparedStatement = connection.prepareStatement(CANCEL_ORDER);
		        preparedStatement.setInt(1, orderId);
		        preparedStatement.setInt(2, productId);

		        int rowsAffected = preparedStatement.executeUpdate();
		        if (rowsAffected > 0) {
		            System.out.println("Order record cancelled successfully.");
		        } else {
		            System.out.println("Order cancellation failed. The record might not exist.");
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw e;  
		    } finally {
		        if (resultSet != null) {
		            resultSet.close();
		        }
		        if (preparedStatement != null) {
		            preparedStatement.close();
		        }
		        if (connection != null) {
		            connection.close();
		        }
		    }
		}
    
    public void createProduct(Connection conn, User user, Product product) throws SQLException {
        if (!"Admin".equalsIgnoreCase(user.getRole())) {
            throw new SQLException("Only admin users are authorized to create products.");
        }

        PreparedStatement stmt = conn.prepareStatement(CREATE_PRODUCT);
        stmt.setString(1, product.getProductName());
        stmt.setDouble(2, product.getPrice());
        stmt.executeUpdate();
    }


    public void createUser(Connection conn, User user) throws SQLException {
        if (!"Admin".equalsIgnoreCase(user.getRole()) && !"User".equalsIgnoreCase(user.getRole())) {
            throw new SQLException("Invalid role: " + user.getRole() + ". Allowed values are 'Admin' or 'User'.");
        }

        PreparedStatement stmt = conn.prepareStatement(CREATE_USER);
        stmt.setInt(1, user.getUserId());
        stmt.setString(2, user.getUsername());
        stmt.setString(3, user.getPassword());
        stmt.setString(4, user.getRole());
        stmt.executeUpdate();
    }



    public List<Product> getAllProducts(Connection conn) throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement(GET_ALL_PRODUCT);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            products.add(new	 Product(rs.getInt("productId"), rs.getString("productName"), rs.getString("description"), rs.getDouble("price"), rs.getInt("quantityInStock"), rs.getString("type")));
        }
        return products;
    }


    @Override
    public List<Product> getOrderByUser(User user) throws UserNotFoundException, Exception {
        List<Product> products = new ArrayList<>();
        Connection conn = Connectionhelper.getConnection();
        if (!isUserExists(conn, user.getUserId())) {
            throw new UserNotFoundException("User with ID " + user.getUserId() + " not found.");
        }
        PreparedStatement stmt = conn.prepareStatement(GET_ORDER_BY_USER);
        stmt.setInt(1, user.getUserId());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            products.add(new Product(rs.getInt("productId"), rs.getString("productName"), rs.getString("description"), rs.getDouble("price"), rs.getInt("quantityInStock"), rs.getString("type")));
        }
        return products;
    }

    public boolean isUserExists(Connection conn, int userId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(IS_USER_EXISTS);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        return rs.next() && rs.getInt(1) > 0;
    }

    public boolean isOrderExists(Connection conn, int orderId) throws SQLException {
        
        try (PreparedStatement stmt = conn.prepareStatement(IS_ORDER_EXISTS)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw e;
        }
    }


    public boolean isAdminUser(Connection conn, int userId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(IS_ADMIN_USER);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        return rs.next() && "Admin".equalsIgnoreCase(rs.getString("role"));
    }
}


