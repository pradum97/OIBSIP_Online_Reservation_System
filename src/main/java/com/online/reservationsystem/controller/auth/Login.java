package com.online.reservationsystem.controller.auth;

import com.online.reservationsystem.CustomDialog;
import com.online.reservationsystem.Main;
import com.online.reservationsystem.database.DBConnection;
import com.online.reservationsystem.model.AuthInformation;
import com.online.reservationsystem.util.OptionalMethod;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login implements Initializable {

    public TextField usernameTf;
    public PasswordField passwordTf;
    public Button login_button;
    public HBox passwordContainer;
    private OptionalMethod method;
    private CustomDialog customDialog;
    public static AuthInformation authInformation;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        method = new OptionalMethod();
        customDialog = new CustomDialog();


    }


    public void enterPress(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            callThread();
        }
    }

    public void loginBn(ActionEvent event) {
        callThread();
    }

    private void callThread() {

        String username = usernameTf.getText();
        String password = passwordTf.getText();

        if (username.isEmpty()) {
            method.show_popup("Please enter username", usernameTf);
            return;
        } else if (password.isEmpty()) {
            method.show_popup("Please enter password", passwordContainer);
            return;
        }


        startLogin(username, password);

    }

    private void startLogin(String username, String password) {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = new DBConnection().getConnection();

            String query = "select * from users where username = ? and password = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

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

                authInformation = new AuthInformation(userId, usernameStr, email, gender,
                        firstName, lastName, phoneCode, phone, createdDate);

                Platform.runLater(() -> new Main().changeScene("dashboard.fxml", "DASHBOARD"));


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
}
