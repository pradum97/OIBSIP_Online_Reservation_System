package com.online.reservationsystem.model;

public class TrainModel {

    private int trainNumber,seat;
    private String trainName;

    public TrainModel(int trainNumber, int seat, String trainName) {
        this.trainNumber = trainNumber;
        this.seat = seat;
        this.trainName = trainName;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }
}
