package com.example.white_elephant.Models;

import android.content.ClipData;
import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemModel implements Model {
    private String name;
    private String description;
    private double value;

    /***
     *  Construct an empty ItemModel
     */
    public ItemModel() {
        setName("");
        setDescription("");
        setValue(-1);
    }

    /***
     *  Construct an ItemModel with data
     */
    public ItemModel(String name, String description, double value) {
        setName(name);
        setDescription(description);
        setValue(value);
    }

    /*** Construct a new ItemModel from a json object
     *
     * @param json: the json object to construct using
     */
    public ItemModel(JSONObject json) {
        fromJson(json);
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

    /*** Fills the item with data from a json object
     * @param json: the json object to use
     */
    @Override
    public void fromJson(JSONObject json) {
        try {
            this.setName(json.getString("Name"));
            this.setDescription(json.getString("Description"));
            this.setValue(json.getDouble("Value"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /***
     * @return A json representation of the item data
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("Name", this.getName());
            json.put("Description", this.getDescription());
            json.put("Value", this.getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    /* ALL CODE TO IMPLEMENT PARCELABLE THIS ALLOWS THE OBJECT TO BE PASSED IN A BUNDLE */

    protected ItemModel(Parcel in) {
        this.setName(in.readString());
        this.setDescription(in.readString());
        this.setValue(in.readDouble());
    }

    public static final Creator<ItemModel> CREATOR = new Creator<ItemModel>() {
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