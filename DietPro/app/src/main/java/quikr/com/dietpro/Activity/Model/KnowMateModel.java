package quikr.com.dietpro.Activity.Model;

import java.io.Serializable;

/**
 * Created by Rakshith
 */
public class KnowMateModel implements Serializable{
    private String          knowMateId;
    private String          knowMateCatId;
    private String          knowMateName;
    private String          knowMateDetail;
    private String          knowMateCreateDate;

    public KnowMateModel(String knowMateId , String knowMateCatId , String knowMateName , String knowMateDetail , String knowMateCreateDate)
    {
        this.setKnowMateId(knowMateId);
        this.setKnowMateCatId(knowMateCatId);
        this.setKnowMateName(knowMateName);
        this.setKnowMateDetail(knowMateDetail);
        this.setKnowMateCreateDate(knowMateCreateDate);
    }

    public String getKnowMateId() {
        return knowMateId;
    }

    public void setKnowMateId(String knowMateId) {
        this.knowMateId = knowMateId;
    }

    public String getKnowMateCatId() {
        return knowMateCatId;
    }

    public void setKnowMateCatId(String knowMateCatId) {
        this.knowMateCatId = knowMateCatId;
    }

    public String getKnowMateName() {
        return knowMateName;
    }

    public void setKnowMateName(String knowMateName) {
        this.knowMateName = knowMateName;
    }

    public String getKnowMateDetail() {
        return knowMateDetail;
    }

    public void setKnowMateDetail(String knowMateDetail) {
        this.knowMateDetail = knowMateDetail;
    }

    public String getKnowMateCreateDate() {
        return knowMateCreateDate;
    }

    public void setKnowMateCreateDate(String knowMateCreateDate) {
        this.knowMateCreateDate = knowMateCreateDate;
    }
}
