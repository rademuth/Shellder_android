package edu.virginia.cs.cs4720.shellder;

/**
 * Created by Robbie on 9/9/2015.
 */

public class BucketListItem {

    private int id;
    private String description;
    private boolean complete;

    /* Might want to add the following fields
     * - Latitude
     * - Longitude
     * - Picture
     */

    public BucketListItem() {
        this.id = 0;
        this.description = "";
        this.complete = false;
    }

    public BucketListItem(int id, String description) {
        this.id = id;
        this.description = description;
        this.complete = false;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getComplete() {
        return this.complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

}
