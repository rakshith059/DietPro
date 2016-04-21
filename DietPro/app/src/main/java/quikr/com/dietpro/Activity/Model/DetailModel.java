package quikr.com.dietpro.Activity.Model;

/**
 * Created by Rakshith
 */
public class DetailModel {
    private String      vitaminId;
    private String      vitaminName;
    private String      vitaminCategory;
    private String      vitaminContent;
    private String      vitaminQty;
    private String      vitaminUnits;
    private String      vitaminPrice;
    private String      vitaminQtyRs;
    private String      vitaminUnitsRs;
    private String      vitaminDiscription;
    private String      vitaminItemImage;

    public DetailModel(String vitaminId, String vitaminName, String vitaminCategory, String vitaminContent, String vitaminQty, String vitaminUnits, String vitaminPrice, String vitaminQtyRs, String vitaminUnitsRs, String vitaminDiscription, String vitaminItemImage) {
        this.setVitaminId(vitaminId);
        this.setVitaminName(vitaminName);
        this.setVitaminCategory(vitaminCategory);
        this.setVitaminContent(vitaminContent);
        this.setVitaminQty(vitaminQty);
        this.setVitaminUnits(vitaminUnits);
        this.setVitaminPrice(vitaminPrice);
        this.setVitaminQtyRs(vitaminQtyRs);
        this.setVitaminDiscription(vitaminDiscription);
        this.setVitaminItemImage(vitaminItemImage);
    }

    public String getVitaminId() {
        return vitaminId;
    }

    public void setVitaminId(String vitaminId) {
        this.vitaminId = vitaminId;
    }

    public String getVitaminName() {
        return vitaminName;
    }

    public void setVitaminName(String vitaminName) {
        this.vitaminName = vitaminName;
    }

    public String getVitaminCategory() {
        return vitaminCategory;
    }

    public void setVitaminCategory(String vitaminCategory) {
        this.vitaminCategory = vitaminCategory;
    }

    public String getVitaminContent() {
        return vitaminContent;
    }

    public void setVitaminContent(String vitaminContent) {
        this.vitaminContent = vitaminContent;
    }

    public String getVitaminQty() {
        return vitaminQty;
    }

    public void setVitaminQty(String vitaminQty) {
        this.vitaminQty = vitaminQty;
    }

    public String getVitaminUnits() {
        return vitaminUnits;
    }

    public void setVitaminUnits(String vitaminUnits) {
        this.vitaminUnits = vitaminUnits;
    }

    public String getVitaminPrice() {
        return vitaminPrice;
    }

    public void setVitaminPrice(String vitaminPrice) {
        this.vitaminPrice = vitaminPrice;
    }

    public String getVitaminQtyRs() {
        return vitaminQtyRs;
    }

    public void setVitaminQtyRs(String vitaminQtyRs) {
        this.vitaminQtyRs = vitaminQtyRs;
    }

    public String getVitaminUnitsRs() {
        return vitaminUnitsRs;
    }

    public void setVitaminUnitsRs(String vitaminUnitsRs) {
        this.vitaminUnitsRs = vitaminUnitsRs;
    }

    public String getVitaminDiscription() {
        return vitaminDiscription;
    }

    public void setVitaminDiscription(String vitaminDiscription) {
        this.vitaminDiscription = vitaminDiscription;
    }

    public String getVitaminItemImage() {
        return vitaminItemImage;
    }

    public void setVitaminItemImage(String vitaminItemImage) {
        this.vitaminItemImage = vitaminItemImage;
    }
}
