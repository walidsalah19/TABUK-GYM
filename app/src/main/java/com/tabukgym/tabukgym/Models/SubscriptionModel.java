package com.tabukgym.tabukgym.Models;

public class SubscriptionModel {
    String clubName,custName,subPrice,date,subStatus,custPhone,custAge,custHeight,custWeight,
           subPeriod,trainingPeriod,custId,clubId,subId;

    public SubscriptionModel(String clubName, String custName, String subPrice, String date, String subStatus, String custPhone, String custAge, String custHeight, String custWeight, String subPeriod, String trainingPeriod, String custId, String clubId, String subId) {
        this.clubName = clubName;
        this.custName = custName;
        this.subPrice = subPrice;
        this.date = date;
        this.subStatus = subStatus;
        this.custPhone = custPhone;
        this.custAge = custAge;
        this.custHeight = custHeight;
        this.custWeight = custWeight;
        this.subPeriod = subPeriod;
        this.trainingPeriod = trainingPeriod;
        this.custId = custId;
        this.clubId = clubId;
        this.subId = subId;
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

    public String getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(String subPrice) {
        this.subPrice = subPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustAge() {
        return custAge;
    }

    public void setCustAge(String custAge) {
        this.custAge = custAge;
    }

    public String getCustHeight() {
        return custHeight;
    }

    public void setCustHeight(String custHeight) {
        this.custHeight = custHeight;
    }

    public String getCustWeight() {
        return custWeight;
    }

    public void setCustWeight(String custWeight) {
        this.custWeight = custWeight;
    }

    public String getSubPeriod() {
        return subPeriod;
    }

    public void setSubPeriod(String subPeriod) {
        this.subPeriod = subPeriod;
    }

    public String getTrainingPeriod() {
        return trainingPeriod;
    }

    public void setTrainingPeriod(String trainingPeriod) {
        this.trainingPeriod = trainingPeriod;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }
}
