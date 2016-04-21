package quikr.com.dietpro.Activity.Model;

/**
 * Created by Rakshith
 */
public class RecyclerOrderDetailModel {
    private String      orderDate;
    private String      orderItemName;
    private String      orderItemQuantity;
    private String      orderItemPrice;
    private Double      orderTotalPrice;

    public RecyclerOrderDetailModel(String orderItemName , String orderItemQuantity , String orderItemPrice)
    {
        this.orderItemName      = orderItemName;
        this.orderItemQuantity  = orderItemQuantity;
        this.orderItemPrice     = orderItemPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderItemName() {
        return orderItemName;
    }

    public void setOrderItemName(String orderItemName) {
        this.orderItemName = orderItemName;
    }

    public String getOrderItemQuantity() {
        return orderItemQuantity;
    }

    public void setOrderItemQuantity(String orderItemQuantity) {
        this.orderItemQuantity = orderItemQuantity;
    }

    public String getOrderItemPrice() {
        return orderItemPrice;
    }

    public void setOrderItemPrice(String orderItemPrice) {
        this.orderItemPrice = orderItemPrice;
    }

    public Double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(Double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }
}
