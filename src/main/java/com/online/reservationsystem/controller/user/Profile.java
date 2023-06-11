package com.online.reservationsystem.controller.user;

import com.online.reservationsystem.CustomDialog;
import com.online.reservationsystem.Main;
import com.online.reservationsystem.controller.auth.Login;
import com.online.reservationsystem.database.DBConnection;
import com.online.reservationsystem.model.AuthInformation;
import com.online.reservationsystem.util.OptionalMethod;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class Profile implements Initializable {
    public Label fullNameL, genderL;
    public Label emailL;
    public Label usernameL;
    public Label phoneL;
    public Label createdDateL;

    private OptionalMethod method;
    private CustomDialog customDialog;
    int userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        method = new OptionalMethod();
        customDialog = new CustomDialog();

         userId = (int) Main.primaryStage.getUserData();

        if (userId < 1) {

            Platform.runLater(() -> {

                Stage stage = (Stage) fullNameL.getScene().getWindow();

                if (null != stage && stage.isShowing()) {
                    stage.close();
                }
            });

        } else {
            getUserDataById(userId);
        }

    }

    private void getUserDataById(int id) {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = new DBConnection().getConnection();

            String query = "select * from users where user_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {

                int userId = rs.getInt("user_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String gender = rs.getString("gender");
                String email = rs.getString("email");
                String usernameStr = rs.getString("username");
                String phone = rs.getString("phone");
                String phoneCode = rs.getString("phone_code");
                String createdDate = rs.getString("created_date");

                AuthInformation authInformation = new AuthInformation(userId, usernameStr, email, gender,
                        firstName, lastName, phoneCode, phone, createdDate);

                setUserDate(authInformation);

            } else {
                customDialog.showAlertBox("Login failed !!", "Incorrect username or password");
            }


        } catch (SQLException e) {
            customDialog.showAlertBox("Login failed !!", "Something went wrong. Please try again !!");
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(connection, ps, rs);
        }
    }

    private void setUserDate(AuthInformation authInfo) {


        String fullName = authInfo.getFirstName() + " " +( null == authInfo.getLastName() ||
                authInfo.getLastName().isEmpty() ? "" : authInfo.getLastName());

        fullNameL.setText(fullName);
        genderL.setText(authInfo.getGender());
        emailL.setText(authInfo.getEmail());
        usernameL.setText(authInfo.getUsername());
        createdDateL.setText(authInfo.getCreatedDate());

        if (!Objects.equals(authInfo.getPhone(), "")  || !authInfo.getPhone().isEmpty()){
            phoneL.setText(("+"+authInfo.getPhoneCode()+"-"+ authInfo.getPhone()));
        }

    }


}
