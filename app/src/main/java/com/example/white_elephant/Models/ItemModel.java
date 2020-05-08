package com.example.white_elephant.Models;

import android.content.ClipData;

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
}
