package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import exception.OrderNotFoundException;
import exception.UserNotFoundException;
import model.Order;
import model.Product;
import model.User;

public interface IOrdermanagementRepository {
	void createOrder(Connection mockConnection, Order order) throws ClassNotFoundException, SQLException;
	void cancelOrder(int orderId, int productId) throws SQLException, UserNotFoundException, ClassNotFoundException;
    void createProduct(Connection conn, User user, Product product) throws SQLException;
    void createUser(Connection conn, User user) throws SQLException;
    List<Product> getAllProducts(Connection conn) throws SQLException;
    List<Product> getOrderByUser(User user) throws UserNotFoundException, Exception;
}
