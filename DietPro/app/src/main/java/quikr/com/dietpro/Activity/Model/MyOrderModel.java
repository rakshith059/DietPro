package quikr.com.dietpro.Activity.Model;

/**
 * Created by Rakshith
 */
public class MyOrderModel {
    private int             orderId;
    private String        orderDate;
    private String      orderPrice;
    private String      orderDetail;
    private String  orderQty;

    public MyOrderModel()
    {}

    public MyOrderModel(int orderId, String date, String price, String detail) {
        this.orderId        = orderId;
        this.orderDate      = date;
        this.orderPrice     = price;
        this.orderDetail    = detail;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(String orderQty) {
        this.orderQty = orderQty;
    }
}
