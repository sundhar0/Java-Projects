package model;
import java.sql.Date;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private Date orderDate;
    private List<Product> products;


	public Order(int orderId, int userId, Date orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
    }


    public Order(int userId2, List<Product> products1) {
		// TODO Auto-generated constructor stub
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}

