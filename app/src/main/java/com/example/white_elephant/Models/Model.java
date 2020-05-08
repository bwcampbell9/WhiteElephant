package com.example.white_elephant.Models;

import android.os.Parcelable;

import org.json.JSONObject;

public interface Model extends Parcelable {
    public void fromJson(JSONObject json);
    public JSONObject toJson();
}
