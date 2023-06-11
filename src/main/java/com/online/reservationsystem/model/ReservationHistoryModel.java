package com.online.reservationsystem.model;

public class ReservationHistoryModel {
   private int history_id ;
   private String passengerName,passengerPhone,passengerAge,journeyDate,fromStation,
    toStation,status;

   private int trainNumber,seatNumber,amount;
   private String trainName;
   private Long pnrNumber;

    public ReservationHistoryModel(int history_id, String passengerName, String passengerPhone, String passengerAge, String journeyDate, String fromStation, String toStation,
                                   String status, int trainNumber, int seatNumber, int amount, String trainName, Long pnrNumber) {
        this.history_id = history_id;
        this.passengerName = passengerName;
        this.passengerPhone = passengerPhone;
        this.passengerAge = passengerAge;
        this.journeyDate = journeyDate;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.status = status;
        this.trainNumber = trainNumber;
        this.seatNumber = seatNumber;
        this.amount = amount;
        this.trainName = trainName;
        this.pnrNumber = pnrNumber;
    }

    public Long getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(Long pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getPassengerAge() {
        return passengerAge;
    }

    public void setPassengerAge(String passengerAge) {
        this.passengerAge = passengerAge;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
