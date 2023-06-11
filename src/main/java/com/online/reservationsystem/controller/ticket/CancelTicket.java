package com.online.reservationsystem.controller.ticket;

import com.online.reservationsystem.CustomDialog;
import com.online.reservationsystem.ImageLoader;
import com.online.reservationsystem.Main;
import com.online.reservationsystem.database.DBConnection;
import com.online.reservationsystem.util.OptionalMethod;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CancelTicket implements Initializable {
    public TextField pnrTf;
    public Label nameL;
    public Label phoneL;
    public Label ageL;
    public Label trainNameL;
    public Label trainNoL;
    public Label dateL;
    public Label seatNumL;
    public Label amountL;
    public Label fromStationL;
    public Label statusL;
    public Label toStationL;
    private OptionalMethod method;
    private String statusStr;
    private CustomDialog customDialog;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        method = new OptionalMethod();
        customDialog = new CustomDialog();

    }

    public void searchBnClick(ActionEvent actionEvent) {

        String pnrNumber = pnrTf.getText();

        if (pnrNumber.isEmpty()) {
            method.show_popup("Please enter pnr number !!", pnrTf);
            return;
        }

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            connection = new DBConnection().getConnection();
            ps = connection.prepareStatement("SELECT * FROM HISTORY h " +
                    "left join train t on h.train_no = t.tr_no where pnr_number = ?");
            ps.setLong(1, Long.parseLong(pnrNumber));
            rs = ps.executeQuery();

            if (rs.next()) {
                int historyId = rs.getInt("HISTORY_ID");
                String passengerName = rs.getString("PASSENGER_NAME");
                String passengerPhone = rs.getString("PASSENGER_PHONE");
                String passengerAge = rs.getString("PASSENGER_AGE");
                String journeyDate = rs.getString("JOURNEY_DATE");
                int trainNo = rs.getInt("TRAIN_NO");
                String fromStn = rs.getString("FROM_STN");
                String toStn = rs.getString("TO_STN");
                statusStr = rs.getString("STATUS");
                String trainName = rs.getString("tr_name");
                int seatsNum = rs.getInt("SEATS_NUM");
                int amount = rs.getInt("AMOUNT");
                long pnrNum = rs.getInt("PNR_NUMBER");

                nameL.setText(passengerName);
                phoneL.setText(String.valueOf(passengerPhone));
                ageL.setText(passengerAge);
                dateL.setText(journeyDate);
                trainNoL.setText(String.valueOf(trainNo));
                fromStationL.setText(fromStn);
                toStationL.setText(toStn);
                statusL.setText(statusStr);

                trainNameL.setText(trainName);
                seatNumL.setText(String.valueOf(seatsNum));
                amountL.setText(String.valueOf(amount));

            } else {
                customDialog.showAlertBox("Failed", "PNR Not found");
            }


        } catch (SQLException e) {
            customDialog.showAlertBox("Failed", "PNR Not found");
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(connection, ps, rs);
        }


    }

    public void cancelBnClick(ActionEvent actionEvent) {

        String pnrNumber = pnrTf.getText();

        if (pnrNumber.isEmpty()) {
            method.show_popup("Please enter pnr number !!", pnrTf);
            return;
        }

        if (statusStr.equals(Status.CANCELED.name())) {
            customDialog.showAlertBox("Failed !!", "PNR already cancelled");
            return;
        }

        ImageView image = new ImageView(new ImageLoader().load("img/warning_ic.png"));
        image.setFitWidth(45);
        image.setFitHeight(45);
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning ");
        alert.setGraphic(image);
        alert.setHeaderText("Are you sure you want cancel this ticket?");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(Main.primaryStage);
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);
        if (button == ButtonType.OK) {
            Connection connection = null;
            PreparedStatement ps = null;

            try {

                connection = new DBConnection().getConnection();
                ps = connection.prepareStatement("UPDATE HISTORY SET status =?  where pnr_number = ?");
                ps.setString(1, Status.CANCELED.name());
                ps.setLong(2, Long.parseLong(pnrNumber));

                int res = ps.executeUpdate();

                if (res >0 ){
                    customDialog.showAlertBox("Success","Ticket successfully canceled.");
                    searchBnClick(null);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                DBConnection.closeConnection(connection,ps,null);
            }
        } else {
            alert.close();
        }



    }
}
