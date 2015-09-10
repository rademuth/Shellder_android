package edu.virginia.cs.cs4720.shellder;

/**
 * Created by Robbie on 9/9/2015.
 */

public class Item {

    private int number;
    private String description;

    public Item() {
        this.number = 0;
        this.description = "";
    }

    public Item(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
