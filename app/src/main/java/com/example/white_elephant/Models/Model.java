package com.example.white_elephant.Models;

import org.json.JSONObject;

public interface Model {
    public void fromJson(JSONObject json);
    public JSONObject toJson();
}
