package com.online.reservationsystem;

import com.online.reservationsystem.controller.auth.Login;
import com.online.reservationsystem.controller.ticket.Status;
import com.online.reservationsystem.util.OptionalMethod;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    private OptionalMethod method;
    private CustomDialog customDialog;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customDialog= new CustomDialog();
        method = new OptionalMethod();

    }

    public void bookTicketClick(MouseEvent mouseEvent) {
        customDialog.showFxmlDialog2("ticket/bookTicket.fxml","BOOK TICKET");
    }

    public void bookingHistoryClick(MouseEvent mouseEvent) {
        Main.primaryStage.setUserData(Status.ALL);

        customDialog.showFxmlFullDialog("ticket/reservationHistory.fxml","Reservation History".toUpperCase());
    }

    public void cancelTicketClick(MouseEvent mouseEvent) {

        customDialog.showFxmlFullDialog("ticket/cancelTicket.fxml","CANCEL RESERVATION".toUpperCase());
    }

    public void viewProfileClick(MouseEvent mouseEvent) {
        Main.primaryStage.setUserData(Login.authInformation.getUserId());
        customDialog.showFxmlDialog2("user/profile.fxml","My Profile");
    }

    public void pnrStatusClick(MouseEvent mouseEvent) {

        customDialog.showFxmlFullDialog("ticket/pnrStatus.fxml","PNR STATUS".toUpperCase());

    }

    public void cancelHistoryClick(MouseEvent mouseEvent) {
        Main.primaryStage.setUserData(Status.CANCELED);

        customDialog.showFxmlFullDialog("ticket/reservationHistory.fxml","Reservation History".toUpperCase());

    }

    public void logoutBnClick(ActionEvent actionEvent) {


        ImageView image = new ImageView(new ImageLoader().load("img/warning_ic.png"));
        image.setFitWidth(45);
        image.setFitHeight(45);
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning ");
        alert.setGraphic(image);
        alert.setHeaderText("Are you sure you want to logout?");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(Main.primaryStage);
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);
        if (button == ButtonType.OK) {
            new Main().changeScene("auth/login.fxml", "LOGIN HERE");
            Login.authInformation = null;
        } else {
            alert.close();
        }
    }
}
