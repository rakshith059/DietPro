package quikr.com.dietpro.Activity.Model;

/**
 * Created by Rakshith
 */
public class CartModel {

    private String cartId;
    private String userId;
    private String prodId;
    private String prodType;
    private String prodQty;
    private String prodPrice;
    private String prodDetails;
    private String cartStatus;
    private String cartCrtdt;

    public CartModel(String cartId, String userId, String prodId, String prodType, String prodQty, String prodPrice, String prodDetails, String cartStatus, String cartCrtdt) {
        this.cartId         = cartId;
        this.userId         = userId;
        this.prodId         = prodId;
        this.prodType       = prodType;
        this.prodQty        = prodQty;
        this.prodPrice      = prodPrice;
        this.prodDetails    = prodDetails;
        this.cartStatus     = cartStatus;
        this.cartCrtdt      = cartCrtdt;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public String getProdQty() {
        return prodQty;
    }

    public void setProdQty(String prodQty) {
        this.prodQty = prodQty;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdDetails() {
        return prodDetails;
    }

    public void setProdDetails(String prodDetails) {
        this.prodDetails = prodDetails;
    }

    public String getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(String cartStatus) {
        this.cartStatus = cartStatus;
    }

    public String getCartCrtdt() {
        return cartCrtdt;
    }

    public void setCartCrtdt(String cartCrtdt) {
        this.cartCrtdt = cartCrtdt;
    }
}
