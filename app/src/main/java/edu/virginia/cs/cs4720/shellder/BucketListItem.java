package edu.virginia.cs.cs4720.shellder;

public class BucketListItem {

    private int id;
    private String title;
    private String description;
    private float latitude;
    private float longitude;
    private String photoPath;
    private boolean complete;

    public BucketListItem() {
        this.id = 0;
        this.title = "";
        this.complete = false;
    }

    public BucketListItem(int id, String title, String description, float latitude, float longitude, String photoPath, boolean complete) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoPath = photoPath;
        this.complete = complete;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() { return this.description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getPhotoPath() {
        return this.photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public boolean getComplete() {
        return this.complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

}
