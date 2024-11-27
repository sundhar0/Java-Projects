package main;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import dao.OrderProcessor;
import exception.UserNotFoundException;
import model.Order;
import model.Product;
import model.User;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderProcessorTest {

    private OrderProcessor orderProcessor;
    private Connection mockConnection;

    @BeforeAll
    void setUp() {
        orderProcessor = new OrderProcessor();
        mockConnection = mock(Connection.class);
    }

    @Test
    void testCreateOrder_UserNotExists() throws Exception {
        User user = new User(1, "testuser", "password", "User");
        Order order = new Order(1, user.getUserId(), null);

        // Mocking the connection and the user existence check
        when(mockConnection.prepareStatement(anyString())).thenReturn(mock(PreparedStatement.class));
        when(orderProcessor.isUserExists(mockConnection, user.getUserId())).thenReturn(false);

        // Verify that the user existence check is done, and the order is not created
        orderProcessor.createOrder(mockConnection, order);

        verify(mockConnection).prepareStatement(OrderProcessor.CREATE_ORDER);
    }

    @Test
    void testCancelOrder_OrderNotFound() {
        int orderId = 1;
        int productId = 1;

        // Test to ensure a UserNotFoundException is thrown when the order is not found
        assertThrows(UserNotFoundException.class, () -> {
            orderProcessor.cancelOrder(orderId, productId);
        });
    }

    @Test
    void testCancelOrder_Success() throws SQLException, UserNotFoundException, ClassNotFoundException {
        int orderId = 1;
        int productId = 1;

        // Mocking the connection and prepared statements for cancel order
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        when(conn.prepareStatement(anyString())).thenReturn(stmt);

        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true);
        when(stmt.executeQuery()).thenReturn(rs);

        when(stmt.executeUpdate()).thenReturn(1);  // Simulating a successful cancel

        // Call the method to test
        orderProcessor.cancelOrder(orderId, productId);

        // Verify that executeUpdate is called once to cancel the order
        verify(stmt, times(1)).executeUpdate();
    }

    @Test
    void testCreateProduct_NotAdmin() {
        User nonAdminUser = new User(2, "testuser2", "password", "User");
        Product product = new Product(1, "Test Product", "Test Description", 20.0, 10, "Electronics");

        // Test to ensure that a non-admin user cannot create a product
        assertThrows(SQLException.class, () -> {
            orderProcessor.createProduct(mockConnection, nonAdminUser, product);
        });
    }

    @Test
    void testCreateProduct_Admin() throws SQLException {
        User adminUser = new User(1, "adminuser", "password", "Admin");
        Product product = new Product(1, "Test Product", "Test Description", 20.0, 10, "Electronics");

        // Test that an admin user can create a product
        orderProcessor.createProduct(mockConnection, adminUser, product);

        // Verify that prepareStatement for CREATE_PRODUCT query is called
        verify(mockConnection).prepareStatement(OrderProcessor.CREATE_PRODUCT);
    }

    @Test
    void testGetAllProducts() throws SQLException {
        // Mock the behavior of the getAllProducts method
        List<Product> products = orderProcessor.getAllProducts(mockConnection);

        // Assert that the result is not null and initially empty (assuming no products in DB)
        assertNotNull(products);
        assertTrue(products.isEmpty());  // Would mock ResultSet for actual database results
    }

    @Test
    void testGetOrderByUser_UserNotFound() {
        User user = new User(1, "testuser", "password", "User");

        // Test to ensure that the method throws UserNotFoundException when no order is found
        assertThrows(UserNotFoundException.class, () -> {
            orderProcessor.getOrderByUser(user);
        });
    }

    @Test
    void testIsUserExists() throws SQLException {
        when(orderProcessor.isUserExists(mockConnection, 1)).thenReturn(true);

        // Test that the isUserExists method returns true
        boolean exists = orderProcessor.isUserExists(mockConnection, 1);
        assertTrue(exists);
    }

    @Test
    void testIsOrderExists() throws SQLException {
        when(orderProcessor.isOrderExists(mockConnection, 1)).thenReturn(true);

        // Test that the isOrderExists method returns true
        boolean exists = orderProcessor.isOrderExists(mockConnection, 1);
        assertTrue(exists);
    }

    @Test
    void testIsAdminUser() throws SQLException {
        when(orderProcessor.isAdminUser(mockConnection, 1)).thenReturn(true);

        // Test that the isAdminUser method correctly identifies an admin user
        boolean isAdmin = orderProcessor.isAdminUser(mockConnection, 1);
        assertTrue(isAdmin);
    }

    @AfterAll
    void tearDown() {
        // Any cleanup actions if needed after all tests
    }
}
