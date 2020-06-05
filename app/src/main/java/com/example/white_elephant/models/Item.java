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
    private List<String> saved;
    private List<String> matches;
    private String imageUrl;

    /***
     *  Construct an empty ItemModel
     */
    public Item() {
        setDescription("");
        setValue(-1);
        setImageUrl("");
        setTags(new ArrayList<>());
        setLiked(new ArrayList<>());
        setDisliked(new ArrayList<>());
        setSaved(new ArrayList<>());
        setMatches(new ArrayList<>());
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
        setLiked(new ArrayList<>());
        setDisliked(new ArrayList<>());
        setSaved(new ArrayList<>());
        setMatches(new ArrayList<>());
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

    public List<String> getSaved() { return saved; }

    public void setSaved(List<String> saved) { this.saved = saved; }

    public void addSaved(String save) { this.saved.add(save); }

    public List<String> getMatches() { return matches; }

    public void setMatches(List<String> matches) { this.matches = matches; }

    public void addMatch(String match) { this.matches.add(match); }

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
        double lowerBound = -1;
        double multiplier = .9;
        int[] priceArray = {15, 50, 100, 500, 1000};
        // price lower bound ratios depends on price range

        // special case for large values
        if (price > 1000){
            lowerBound = price * multiplier;
        } else{
            multiplier = .4;
            for (int val: priceArray){
                if (price <= val){
                    lowerBound = price * multiplier;
                    break;
                }
                multiplier += .1;
            }
        }

        return lowerBound;
    }

    public static double upperBound(double price){
        double upperBound = -1;
        double multiplier = 1.1;
        int[] priceArray = {15, 50, 100, 500, 1000};

        // special case for small values
        if (price <= 10){
            upperBound = 16;
        } // special case for large values
        else if (price > 1000){
            upperBound = price * multiplier;
        }
        else{
            multiplier = 1.6;
            for (int val: priceArray){
                if (price <= val){
                    upperBound = price * multiplier;
                    break;
                }
                multiplier -= .1;
            }
        }
        return upperBound;
    }
}
