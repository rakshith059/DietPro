package quikr.com.dietpro.Activity.Model;

/**
 * Created by Rakshith
 */
public class DeliciousRecipesModel {

    private String      gridImage;
    private String      dRecipesId;
    private String      dRecipesName;
    private String      dRecipesAlt;
    private String      dRecipesDescription;
    private String      dRecipesPrice;

    public DeliciousRecipesModel(String gridImage, String dRecipesId, String dRecipesName, String dRecipesAlt, String dRecipesDescription, String dRecipesPrice)
    {
        this.setGridImage(gridImage);
        this.dRecipesId = dRecipesId;
        this.setdRecipesName(dRecipesName);
        this.dRecipesAlt = dRecipesAlt;
        this.dRecipesDescription = dRecipesDescription;
        this.dRecipesPrice = dRecipesPrice;
    }

    public String getGridImage() {
        return gridImage;
    }

    public void setGridImage(String gridImage) {
        this.gridImage = gridImage;
    }


    public String getdRecipesId() {
        return dRecipesId;
    }

    public void setdRecipesId(String dRecipesId) {
        this.dRecipesId = dRecipesId;
    }

    public String getdRecipesName() {
        return dRecipesName;
    }

    public void setdRecipesName(String dRecipesName) {
        this.dRecipesName = dRecipesName;
    }

    public String getdRecipesAlt() {
        return dRecipesAlt;
    }

    public void setdRecipesAlt(String dRecipesAlt) {
        this.dRecipesAlt = dRecipesAlt;
    }

    public String getdRecipesDescription() {
        return dRecipesDescription;
    }

    public void setdRecipesDescription(String dRecipesDescription) {
        this.dRecipesDescription = dRecipesDescription;
    }

    public String getdRecipesPrice() {
        return dRecipesPrice;
    }

    public void setdRecipesPrice(String dRecipesPrice) {
        this.dRecipesPrice = dRecipesPrice;
    }
}
