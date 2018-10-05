package com.growtogether.myrestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("serialno")
    @Expose
    public int userSerialNo;
    @SerializedName("email")
    @Expose
    public String userEmail;
    @SerializedName("name")
    @Expose
    public String userName;
    @SerializedName("password")
    @Expose
    public String userPassword;
    @SerializedName("phone")
    @Expose
    public String userPhone;
    @SerializedName("created")
    @Expose
    public String userCreated;

    public int getUserSerialNo() {
        return userSerialNo;
    }

    public void setUserSerialNo(int userSerialNo) {
        this.userSerialNo = userSerialNo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }
}
