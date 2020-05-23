package com.example.white_elephant.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemModel implements com.example.white_elephant.models.Model, Serializable {
    public String id;
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
    public ItemModel() {
        setDescription("");
        setValue(-1);
        setTags(new ArrayList<String>());
    }

    /***
     *  Construct an ItemModel with data
     */
    public ItemModel(String name, String description, double value, List<String> tags) {
        setName(name);
        setDescription(description);
        setValue(value);
        setTags(tags);
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

    public List<String> getTags() { return tags; }

    public void setTags(List<String> tags) { this.tags = tags; }

    public String getImageUrl() {return imageUrl; }

    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl; }

    public List<String> getDisliked() { return disliked; }

    public void setDisliked(List<String> disliked) { this.disliked = disliked; }

    public List<String> getLiked() { return liked; }

    public void setLiked(List<String> liked) { this.liked = liked; }


    /* ALL CODE TO IMPLEMENT PARCELABLE THIS ALLOWS THE OBJECT TO BE PASSED IN A BUNDLE */

    protected ItemModel(Parcel in) {
        this.setName(in.readString());
        this.setDescription(in.readString());
        this.setValue(in.readDouble());
    }

    public static final Parcelable.Creator<ItemModel> CREATOR = new Parcelable.Creator<ItemModel>() {
        @Override
        public ItemModel createFromParcel(Parcel in) {
            return new ItemModel(in);
        }

        @Override
        public ItemModel[] newArray(int size) {
            return new ItemModel[size];
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
}
