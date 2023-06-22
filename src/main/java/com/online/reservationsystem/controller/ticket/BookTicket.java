package com.online.reservationsystem.controller.ticket;

import com.online.reservationsystem.CustomDialog;
import com.online.reservationsystem.Main;
import com.online.reservationsystem.model.BookingModel;
import com.online.reservationsystem.util.OptionalMethod;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class BookTicket implements Initializable {


    public TextField fromTf;
    public TextField toTf;
    public DatePicker journeyDate;
    public ComboBox<String> classTypeCom;

    private CustomDialog customDialog;
    private OptionalMethod method;

    private ObservableList<String> classType = FXCollections.observableArrayList("All Classes","Anubhuti Class (EA)","AC First Class (1A)",
            "Vistadome AC (EV)","Exec. Chair Car (EC)","AC 2 Tier (2A)","First Class (FC)","Sleeper (SL)","Second Sitting (2S)","AC Chair car (CC)");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customDialog = new CustomDialog();
        method = new OptionalMethod();
        classTypeCom.setItems(classType);

        method.convertDateFormat(journeyDate);
        classTypeCom.getSelectionModel().select("All Classes");
    }

    public void searchBnClick(ActionEvent actionEvent) {

        String fromName = fromTf.getText();
        String toName = toTf.getText();
        String journeyDateStr = journeyDate.getEditor().getText();
        String classTypeStr = classTypeCom.getSelectionModel().getSelectedItem();


        if (fromName.isEmpty()){
            method.show_popup("Please enter from name",fromTf);
            return;
        }else  if (toName.isEmpty()){
            method.show_popup("Please enter to name",toTf);
            return;
        }else if (journeyDateStr.isEmpty()){
            method.show_popup("Please enter from name",journeyDate);
            return;
        }else  if (classTypeStr.isEmpty()){
            method.show_popup("Please select class type",classTypeCom);
            return;
        }

        BookingModel bm = new BookingModel(fromName,toName,classTypeStr,journeyDateStr);
        Main.primaryStage.setUserData(bm);

        Stage stage = (Stage) fromTf.getScene().getWindow();

        if (null !=  stage && stage.isShowing()){
            stage.close();
        }

        Platform.runLater(() -> customDialog.showFxmlDialog2("ticket/trains.fxml","Available Trains"));
    }
}
