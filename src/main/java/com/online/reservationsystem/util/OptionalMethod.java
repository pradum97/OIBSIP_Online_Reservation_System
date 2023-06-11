package com.online.reservationsystem.util;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class OptionalMethod {

    private static final String DIGITS = "0123456789";
    private static final int PNR_LENGTH = 6;

    public static long generatePNR() {
        Random random = new Random();
        StringBuilder pnr = new StringBuilder();

        for (int i = 0; i < PNR_LENGTH; i++) {
            int index = random.nextInt(DIGITS.length());
            char randomDigit = DIGITS.charAt(index);
            pnr.append(randomDigit);
        }

        return Long.parseLong( pnr.toString());
    }


    public static void minimizedStage(Stage stage ,boolean bool){
        stage.setMaximized(bool);
    }

    public void convertDateFormat(DatePicker... date) {
        for (DatePicker datePicker : date) {
            datePicker.setConverter(new StringConverter<>() {
                private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                @Override
                public String toString(LocalDate localDate) {
                    if (localDate == null)
                        return "";
                    return dateTimeFormatter.format(localDate);
                }
                @Override
                public LocalDate fromString(String dateString) {
                    if (dateString == null || dateString.trim().isEmpty()) {
                        return null;
                    }
                    return LocalDate.parse(dateString, dateTimeFormatter);
                }
            });
        }
    }

    public ProgressIndicator getProgressBar(double height, double width) {
        ProgressIndicator pi = new ProgressIndicator();
        pi.indeterminateProperty();
        pi.setPrefHeight(height);
        pi.setPrefWidth(width);

        return pi;
    }

    public void hideElement(Node... node) {
       for (Node n : node){
            n.setVisible(false);
            n.managedProperty().bind(n.visibleProperty());
        }
    }
    public ContextMenu show_popup(String message, Object textField) {
        ContextMenu form_Validator = new ContextMenu();
        form_Validator.setAutoHide(true);
        form_Validator.getItems().add(new MenuItem(message));
        form_Validator.show((Node) textField, Side.RIGHT, 10, 0);
        return form_Validator;
    }

    public void closeStage(Node node) {

        Stage stage = (Stage) node.getScene().getWindow();
        if (stage.isShowing()) {
            stage.close();
        }
    }

    public void selectTable(int index, TableView tableView) {

        if (!tableView.getSelectionModel().isEmpty()) {
            tableView.getSelectionModel().clearSelection();
        }

        tableView.getSelectionModel().select(index);
    }

}
