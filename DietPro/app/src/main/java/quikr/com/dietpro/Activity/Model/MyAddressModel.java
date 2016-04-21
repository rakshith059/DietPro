package quikr.com.dietpro.Activity.Model;

/**
 * Created by Rakshith
 */
public class MyAddressModel {

    private String addressId;
    private String userId;
    private String addressTitle;
    private String addressDetail;
    private String addressLandMark;
    private String addressPincode;
    private String addressCity;
    private String addressState;
    private String addressPhone;
    private String addressEmail;
    private String addressType;
    private String addressIsDefault;
    private String addressCrtDt;

    public MyAddressModel(String addressId, String userId, String addressTitle, String addressDetail, String addressLandMark, String addressPincode, String addressCity, String addressState, String addressPhone, String addressEmail, String addressType, String addressIsDefault, String addressCrtDt) {
        this.setAddressId(addressId);
        this.setUserId(userId);
        this.setAddressTitle(addressTitle);
        this.setAddressDetail(addressDetail);
        this.setAddressLandMark(addressLandMark);
        this.setAddressPincode(addressPincode);
        this.setAddressCity(addressCity);
        this.setAddressState(addressState);
        this.setAddressPhone(addressPhone);
        this.setAddressEmail(addressEmail);
        this.setAddressType(addressType);
        this.setAddressIsDefault(addressIsDefault);
        this.setAddressCrtDt(addressCrtDt);
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAddressLandMark() {
        return addressLandMark;
    }

    public void setAddressLandMark(String addressLandMark) {
        this.addressLandMark = addressLandMark;
    }

    public String getAddressPincode() {
        return addressPincode;
    }

    public void setAddressPincode(String addressPincode) {
        this.addressPincode = addressPincode;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }

    public String getAddressEmail() {
        return addressEmail;
    }

    public void setAddressEmail(String addressEmail) {
        this.addressEmail = addressEmail;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddressIsDefault() {
        return addressIsDefault;
    }

    public void setAddressIsDefault(String addressIsDefault) {
        this.addressIsDefault = addressIsDefault;
    }

    public String getAddressCrtDt() {
        return addressCrtDt;
    }

    public void setAddressCrtDt(String addressCrtDt) {
        this.addressCrtDt = addressCrtDt;
    }
}
