package com.example.white_elephant.models;

import android.annotation.SuppressLint;
import android.os.Parcel;

import java.io.Serializable;

@SuppressLint("ParcelCreator")
public class UserModel implements com.example.white_elephant.models.Model, Serializable {

    public String email;
    private String password;

        /***
     *  Construct an empty UserModel
     */
    public UserModel() {
        setEmail("");
        setPassword("");
    }

    /***
     *  Construct a UserModel with data
     */
    public UserModel(String email, String password) {
        setEmail(email);
        setPassword(password);

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getEmail());
        dest.writeString(this.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passoword) {
        this.password = passoword;
    }
}
