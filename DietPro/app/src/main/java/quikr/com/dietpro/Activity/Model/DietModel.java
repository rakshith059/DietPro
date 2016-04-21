package quikr.com.dietpro.Activity.Model;

/**
 * Created by Rakshith
 */
public class DietModel {
    private String          backgroundImage;
    private String          title;
    private String          categoryId;
    private String          categoryContent;

    public DietModel(String backgroundImage, String categoryId, String categoryTitle, String categoryContent) {
        this.setBackgroundImage(backgroundImage);
        this.setTitle(categoryTitle);
        this.setCategoryId(categoryId);
        this.setCategoryContent(categoryContent);
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryContent() {
        return categoryContent;
    }

    public void setCategoryContent(String categoryContent) {
        this.categoryContent = categoryContent;
    }
}
