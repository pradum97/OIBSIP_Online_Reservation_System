package com.online.reservationsystem.controller.ticket;

import com.online.reservationsystem.CustomDialog;
import com.online.reservationsystem.Main;
import com.online.reservationsystem.controller.auth.Login;
import com.online.reservationsystem.database.DBConnection;
import com.online.reservationsystem.model.BookingModel;
import com.online.reservationsystem.model.TrainModel;
import com.online.reservationsystem.util.OptionalMethod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Trains implements Initializable {


    public TableView<TrainModel> tableView;
    public TableColumn<TrainModel, String> colTrainNumber;
    public TableColumn<TrainModel, String> colTrainName;
    public TableColumn<TrainModel, Integer> colSeat;
    public TableColumn<TrainModel, String> colAction;
    public Label fromNameL;
    public Label toNameL;
    public Label journeyDateL;

    private BookingModel bookingModel;

    private ObservableList<TrainModel> trainList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bookingModel = (BookingModel) Main.primaryStage.getUserData();
        setJourneyData();

        getTrain();
    }

    private void getTrain() {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = new DBConnection().getConnection();

            String query = "SELECT * FROM TRAIN WHERE FROM_STN = ? AND TO_STN = ?;";
            ps = connection.prepareStatement(query);
            ps.setString(1, bookingModel.getFromName());
            ps.setString(2, bookingModel.getToName());

            rs = ps.executeQuery();

            while (rs.next()) {

                int seat = rs.getInt("SEATS");
                int trainNumber = rs.getInt("TR_NO");
                String trainName = rs.getString("TR_NAME");

                TrainModel trainModel = new TrainModel(trainNumber, seat, trainName);
                trainList.add(trainModel);
            }

            colTrainName.setCellValueFactory(new PropertyValueFactory<>("trainName"));
            colTrainNumber.setCellValueFactory(new PropertyValueFactory<>("trainNumber"));
            colSeat.setCellValueFactory(new PropertyValueFactory<>("seat"));

            tableView.setItems(trainList);

            setOptionalCell();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(connection, ps, rs);
        }
    }

    private void setOptionalCell() {

        Callback<TableColumn<TrainModel, String>, TableCell<TrainModel, String>>
                cellFactory = (TableColumn<TrainModel, String> param) -> new TableCell<>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);

                } else {

                    TrainModel trainModel = tableView.getItems().get(getIndex());

                    Button bookNowBn = new Button("BOOK NOW");

                   bookNowBn.setOnAction(actionEvent -> {

                       TextField nameTf = new TextField();
                       nameTf.setPromptText("Enter full name");

                       TextField age = new TextField();
                       age.setPromptText("Enter age");

                       TextField phone = new TextField();
                       phone.setPromptText("Enter phone number");

                       nameTf.setMinHeight(35);
                       phone.setMinHeight(35);
                       age.setMinHeight(35);

                       nameTf.setStyle("-fx-border-color: grey;-fx-border-radius: 3;-fx-focus-traversable: false");
                       phone.setStyle("-fx-border-color: grey;-fx-border-radius: 3;-fx-focus-traversable: false");
                       age.setStyle("-fx-border-color: grey;-fx-border-radius: 3;-fx-focus-traversable: false");


                       Button cancelBn = new Button("CANCEL");
                       Button submitBn = new Button("SUBMIT");

                       cancelBn.setStyle("-fx-background-color: red;-fx-background-radius: 3;-fx-text-fill: white;-fx-cursor: hand");
                       submitBn.setStyle("-fx-background-color: blue;-fx-background-radius: 3;-fx-text-fill: white;-fx-cursor: hand");

                       cancelBn.setOnAction(new EventHandler<ActionEvent>() {
                           @Override
                           public void handle(ActionEvent actionEvent) {

                               Stage stage = (Stage) nameTf.getScene().getWindow();
                               if (null != stage && stage.isShowing()){
                                   stage.close();
                               }
                           }
                       });

                       OptionalMethod method = new OptionalMethod();

                       submitBn.setOnAction(new EventHandler<ActionEvent>() {
                           @Override
                           public void handle(ActionEvent actionEvent) {

                               String nameStr = nameTf.getText();
                               String ageStr = age.getText();
                               String phoneStr = phone.getText();

                               if (nameStr.isEmpty()){
                                   method.show_popup("Please enter passenger full name",nameTf);
                                   return;
                               }else if (phoneStr.isEmpty()){
                                   method.show_popup("Please enter passenger phone number",phone);
                                   return;
                               }else if (ageStr.isEmpty()){
                                   method.show_popup("Please enter passenger age",age);
                                   return;
                               }

                               Connection connection = null;
                               PreparedStatement ps = null;

                               try {
                                   connection = new DBConnection().getConnection();
                                   String query = "INSERT INTO HISTORY(PASSENGER_NAME, PASSENGER_PHONE, PASSENGER_AGE, JOURNEY_DATE, TRAIN_NO, " +
                                           "FROM_STN, TO_STN, SEATS_NUM, AMOUNT,status,reservation_by,pnr_number)\n" +
                                           "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

                                   ps = connection.prepareStatement(query);
                                   ps.setString(1,nameStr);
                                   ps.setString(2,phoneStr);
                                   ps.setString(3,ageStr);
                                   ps.setString(4,bookingModel.getJourneyDate());
                                   ps.setInt(5,trainModel.getTrainNumber());
                                   ps.setString(6,bookingModel.getFromName());
                                   ps.setString(7,bookingModel.getToName());

                                   ps.setInt(8,new Random().nextInt(100));
                                   ps.setInt(9,new Random().nextInt(100)+50);
                                   ps.setString(10, Status.BOOKED.name());
                                   ps.setInt(11, Login.authInformation.getUserId());
                                   ps.setLong(12, OptionalMethod.generatePNR());

                                   int res = ps.executeUpdate();

                                   CustomDialog customDialog = new CustomDialog();

                                   if (res>0){
                                       Main.primaryStage.setUserData(true);
                                       customDialog.showAlertBox("success","Ticket successfully booked");
                                       Stage stage = (Stage) nameTf.getScene().getWindow();
                                       if (null != stage && stage.isShowing()){
                                           stage.close();
                                       }

                                   }

                               } catch (SQLException e) {
                                   throw new RuntimeException(e);
                               }finally {
                                   DBConnection.closeConnection(connection,ps,null);
                               }
                           }
                       });


                       HBox buttonContainer = new HBox(cancelBn,submitBn);
                       buttonContainer.setAlignment(Pos.CENTER);
                       buttonContainer.setSpacing(30);


                       Label title = new Label("ADD PASSENGER DETAILS");
                       title.setStyle("-fx-font-family: 'Arial Black'");
                       HBox titleHb = new HBox(title);
                       titleHb.setAlignment(Pos.CENTER);

                       VBox mainContainer = new VBox(titleHb,new Separator(Orientation.HORIZONTAL),nameTf,phone,age,buttonContainer);
                       mainContainer.setSpacing(20);
                       mainContainer.setStyle("-fx-padding: 15");

                       Stage stage = new Stage();
                       Scene scene = new Scene(mainContainer,400,300);
                       stage.initOwner(Main.primaryStage);
                       stage.setScene(scene);
                       stage.showAndWait();

                       if (Main.primaryStage.getUserData() instanceof Boolean){

                           boolean isBooked = (boolean) Main.primaryStage.getUserData();

                           if (isBooked){
                               Stage stage1 = (Stage) fromNameL.getScene().getWindow();
                               if (null != stage1 && stage1.isShowing()){
                                   stage1.close();
                               }
                           }
                       }

                   });

                   bookNowBn.setStyle("-fx-background-color: #006666;-fx-background-radius: 2;-fx-text-fill: white;-fx-font-weight: bold");
                    HBox managebtn = new HBox(bookNowBn);

                    managebtn.setStyle("-fx-alignment:center");
                    HBox.setMargin(bookNowBn, new Insets(5, 0, 5, 30));

                    setGraphic(managebtn);
                    setText(null);
                }
            }

        };

        colAction.setCellFactory(cellFactory);
    }

    private void setJourneyData() {

        fromNameL.setText(bookingModel.getFromName());
        toNameL.setText(bookingModel.getToName());
        journeyDateL.setText(bookingModel.getJourneyDate());
    }
}
