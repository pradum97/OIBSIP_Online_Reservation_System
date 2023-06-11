module com.online.reservationsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;



    opens  com.online.reservationsystem to javafx.fxml;
    exports  com.online.reservationsystem;

    opens  com.online.reservationsystem.controller.auth to javafx.fxml;
    exports  com.online.reservationsystem.controller.auth;

    opens  com.online.reservationsystem.controller.ticket to javafx.fxml;
    exports  com.online.reservationsystem.controller.ticket;

    opens  com.online.reservationsystem.controller.user to javafx.fxml;
    exports  com.online.reservationsystem.controller.user;

    opens  com.online.reservationsystem.model to javafx.fxml;
    exports  com.online.reservationsystem.model;


    opens  com.online.reservationsystem.util to javafx.fxml;
    exports  com.online.reservationsystem.util;



}