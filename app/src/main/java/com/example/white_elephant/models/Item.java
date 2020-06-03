package com.example.white_elephant.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item extends DBItem implements Parcelable, Serializable {
    private String user;
    private String name;
    private String description;
    private double value;
    private List<String> tags;

    private List<String> liked;
    private List<String> disliked;
    private String imageUrl;

    /***
     *  Construct an empty ItemModel
     */
    public Item() {
        setDescription("");
        setValue(-1);
        setImageUrl("");
        setTags(new ArrayList<>());
    }

    /***
     *  Construct an ItemModel with data
     */
    public Item(String name, String description, double value, List<String> tags) {
        setName(name);
        setDescription(description);
        setValue(value);
        setImageUrl("");
        setTags(tags);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    /***
     * @return the name of the object
     */
    public String getName() {
        return name;
    }

    /*** Sets the name of the object
     *
     * @param name: the new name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * @return the description of the item
     */
    public String getDescription() {
        return description;
    }

    /*** Sets the description of the item
     *
     * @param description: the new description of the item
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /***
     * @return the dollar value of the item
     */
    public double getValue() {
        return value;
    }

    /*** Sets the dollar value of the item
     *
     * @param value: the new dollar value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getDisliked() {
        return disliked;
    }

    public void setDisliked(List<String> disliked) {
        this.disliked = disliked;
    }

    public void addDisliked(String dislike) { this.disliked.add(dislike); }

    public List<String> getLiked() {
        return liked;
    }

    public void setLiked(List<String> liked) {
        this.liked = liked;
    }

    public void addLiked(String like) { this.liked.add(like); }


    /* ALL CODE TO IMPLEMENT PARCELABLE THIS ALLOWS THE OBJECT TO BE PASSED IN A BUNDLE */

    protected Item(Parcel in) {
        this.setName(in.readString());
        this.setDescription(in.readString());
        this.setValue(in.readDouble());
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getName());
        dest.writeString(this.getDescription());
        dest.writeDouble(this.getValue());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static double lowerBound(double price){
        double upper;
        if (price >= 200){
            upper = price * .8;
        } else{
            upper = price * .7;
        }
        return upper;
    }

    public static double upperBound(double price){
        double upper;
        if (price <= 10){
            upper = 20;
        } else if (price >= 200){
           upper = price * 1.2;
        } else {
            upper = price * 1.3;
        }
        return upper;
    }
}
