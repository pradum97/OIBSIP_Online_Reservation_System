package com.online.reservationsystem.model;

public class BookingModel {

    private String fromName,toName,classTypeStr,journeyDate;

    public BookingModel(String fromName, String toName, String classTypeStr, String journeyDate) {
        this.fromName = fromName;
        this.toName = toName;
        this.classTypeStr = classTypeStr;
        this.journeyDate = journeyDate;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getClassTypeStr() {
        return classTypeStr;
    }

    public void setClassTypeStr(String classTypeStr) {
        this.classTypeStr = classTypeStr;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }
}
