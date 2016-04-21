package quikr.com.dietpro.Activity.Model;

/**
 * Created by Rakshith
 */
public class TrainerModel {

    private String      trainerId;
    private String      trainerName;
    private String      trainerLocation;
    private String      trainerLatitude;
    private String      trainerLongitude;
    private String      trainerContact;
    private String      trainerDetail;
    private String      trainerImage;
    private String      trainerCreateDate;

    public TrainerModel(String trainerId , String trainerName , String trainerLocation , String trainerLatitude , String trainerLongitude , String trainerContact , String trainerDetail , String trainerImage , String trainerCreateDate)
    {
        this.setTrainerId(trainerId);
        this.setTrainerName(trainerName);
        this.setTrainerLocation(trainerLocation);
        this.setTrainerLatitude(trainerLatitude);
        this.setTrainerLongitude(trainerLongitude);
        this.setTrainerContact(trainerContact);
        this.setTrainerDetail(trainerDetail);
        this.setTrainerImage(trainerImage);
        this.setTrainerCreateDate(trainerCreateDate);
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTrainerLocation() {
        return trainerLocation;
    }

    public void setTrainerLocation(String trainerLocation) {
        this.trainerLocation = trainerLocation;
    }

    public String getTrainerLatitude() {
        return trainerLatitude;
    }

    public void setTrainerLatitude(String trainerLatitude) {
        this.trainerLatitude = trainerLatitude;
    }

    public String getTrainerLongitude() {
        return trainerLongitude;
    }

    public void setTrainerLongitude(String trainerLongitude) {
        this.trainerLongitude = trainerLongitude;
    }

    public String getTrainerContact() {
        return trainerContact;
    }

    public void setTrainerContact(String trainerContact) {
        this.trainerContact = trainerContact;
    }

    public String getTrainerDetail() {
        return trainerDetail;
    }

    public void setTrainerDetail(String trainerDetail) {
        this.trainerDetail = trainerDetail;
    }

    public String getTrainerImage() {
        return trainerImage;
    }

    public void setTrainerImage(String trainerImage) {
        this.trainerImage = trainerImage;
    }

    public String getTrainerCreateDate() {
        return trainerCreateDate;
    }

    public void setTrainerCreateDate(String trainerCreateDate) {
        this.trainerCreateDate = trainerCreateDate;
    }
}
