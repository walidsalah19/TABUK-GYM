package com.tabukgym.tabukgym.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ClubModel implements Parcelable {
    private String name,email,longitude,latitude,logo,subPrice,phone,comRegister,id,status;

    public ClubModel(String name, String email, String longitude, String latitude, String logo, String subPrice, String phone, String comRegister, String id, String status) {
        this.name = name;
        this.email = email;
        this.longitude = longitude;
        this.latitude = latitude;
        this.logo = logo;
        this.subPrice = subPrice;
        this.phone = phone;
        this.comRegister = comRegister;
        this.id = id;
        this.status = status;
    }

    protected ClubModel(Parcel in) {
        name = in.readString();
        email = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        logo = in.readString();
        subPrice = in.readString();
        phone = in.readString();
        comRegister = in.readString();
        id = in.readString();
        status = in.readString();
    }

    public static final Creator<ClubModel> CREATOR = new Creator<ClubModel>() {
        @Override
        public ClubModel createFromParcel(Parcel in) {
            return new ClubModel(in);
        }

        @Override
        public ClubModel[] newArray(int size) {
            return new ClubModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(String subPrice) {
        this.subPrice = subPrice;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComRegister() {
        return comRegister;
    }

    public void setComRegister(String comRegister) {
        this.comRegister = comRegister;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(longitude);
        parcel.writeString(latitude);
        parcel.writeString(logo);
        parcel.writeString(subPrice);
        parcel.writeString(phone);
        parcel.writeString(comRegister);
        parcel.writeString(id);
        parcel.writeString(status);
    }
}
