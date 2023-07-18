package com.tabukgym.tabukgym.Models;

public class DeliveryModel {
    String clubName,custName,date,duration,status,id,custId;

    public DeliveryModel(String clubName, String custName, String date, String duration, String status, String id, String custId) {
        this.clubName = clubName;
        this.custName = custName;
        this.date = date;
        this.duration = duration;
        this.status = status;
        this.id = id;
        this.custId = custId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
}
