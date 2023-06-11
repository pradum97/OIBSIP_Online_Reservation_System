package com.online.reservationsystem.controller.ticket;

import com.online.reservationsystem.Main;
import com.online.reservationsystem.controller.auth.Login;
import com.online.reservationsystem.database.DBConnection;
import com.online.reservationsystem.model.ReservationHistoryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReservationHistory implements Initializable {


    public TableView<ReservationHistoryModel> historyTableView;
    public TableColumn<ReservationHistoryModel, Integer> historyIdColumn;
    public TableColumn<ReservationHistoryModel, String> passengerNameColumn;
    public TableColumn<ReservationHistoryModel, String> passengerPhoneColumn;
    public TableColumn<ReservationHistoryModel, String> passengerAgeColumn;
    public TableColumn<ReservationHistoryModel, String> journeyDateColumn;
    public TableColumn<ReservationHistoryModel, Integer> trainNoColumn;
    public TableColumn<ReservationHistoryModel, String> fromStnColumn;
    public TableColumn<ReservationHistoryModel, String> toStnColumn;
    public TableColumn<ReservationHistoryModel, String> statusColumn;
    public TableColumn<ReservationHistoryModel, Integer> seatsNumColumn;
    public TableColumn<ReservationHistoryModel, Integer> trainNameColumn;
    public TableColumn<ReservationHistoryModel, Long> pnrColumn;
    public TableColumn<ReservationHistoryModel, Integer> amountColumn;
    public Label titleL;

    private ObservableList<ReservationHistoryModel> historyList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Status status = (Status) Main.primaryStage.getUserData();

        if (status == Status.CANCELED){
            titleL.setText("CANCELED HISTORY");
        }else {
            titleL.setText("RESERVATION HISTORY");
        }

        getHistory(status);
    }

    public void getHistory(Status pnrStatus) {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            connection = new DBConnection().getConnection();

            String query;

            if (pnrStatus == Status.CANCELED) {
                query = """
                        SELECT * FROM HISTORY h
                        left join train t on h.train_no = t.tr_no where reservation_by =? and status = ?
                        """;
            } else {

                query = """
                        SELECT * FROM HISTORY h
                        left join train t on h.train_no = t.tr_no where reservation_by =?
                        """;
            }

            ps = connection.prepareStatement(query);
            ps.setInt(1, Login.authInformation.getUserId());
            if (pnrStatus == Status.CANCELED) {
                ps.setString(2, pnrStatus.name());
            }
            rs = ps.executeQuery();

            while (rs.next()) {
                int historyId = rs.getInt("HISTORY_ID");
                String passengerName = rs.getString("PASSENGER_NAME");
                String passengerPhone = rs.getString("PASSENGER_PHONE");
                String passengerAge = rs.getString("PASSENGER_AGE");
                String journeyDate = rs.getString("JOURNEY_DATE");
                int trainNo = rs.getInt("TRAIN_NO");
                String fromStn = rs.getString("FROM_STN");
                String toStn = rs.getString("TO_STN");
                String status = rs.getString("STATUS");
                String trainName = rs.getString("tr_name");
                int seatsNum = rs.getInt("SEATS_NUM");
                int amount = rs.getInt("AMOUNT");
                long pnrNum = rs.getInt("PNR_NUMBER");

                ReservationHistoryModel rhm = new ReservationHistoryModel(historyId, passengerName, passengerPhone,
                        passengerAge, journeyDate, fromStn, toStn, status, trainNo, seatsNum, amount, trainName, pnrNum);

                historyList.add(rhm);

            }


            historyIdColumn.setCellValueFactory(new PropertyValueFactory<>("history_id"));
            passengerNameColumn.setCellValueFactory(new PropertyValueFactory<>("passengerName"));
            passengerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("passengerPhone"));
            passengerAgeColumn.setCellValueFactory(new PropertyValueFactory<>("passengerAge"));
            journeyDateColumn.setCellValueFactory(new PropertyValueFactory<>("journeyDate"));
            fromStnColumn.setCellValueFactory(new PropertyValueFactory<>("fromStation"));
            toStnColumn.setCellValueFactory(new PropertyValueFactory<>("toStation"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            trainNoColumn.setCellValueFactory(new PropertyValueFactory<>("trainNumber"));
            seatsNumColumn.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));
            amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
            trainNameColumn.setCellValueFactory(new PropertyValueFactory<>("trainName"));
            pnrColumn.setCellValueFactory(new PropertyValueFactory<>("pnrNumber"));

            historyTableView.setItems(historyList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(connection, ps, rs);
        }
    }
}
