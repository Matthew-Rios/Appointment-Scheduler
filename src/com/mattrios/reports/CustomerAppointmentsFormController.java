package com.mattrios.reports;

import com.mattrios.Database;
import com.mattrios.appointments.Appointment;
import com.mattrios.appointments.AppointmentDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.Month;

/**
 * @author Matt Rios
 */
public class CustomerAppointmentsFormController {

    @FXML
    private ComboBox<Month> monthComboBox;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField totalField;

    private ObservableList<Appointment> allAppointments = AppointmentDAO.getAllUserAppointments();


    /**
     * Populates the month combo box with all months and the type combo box with each unique appointment type
     */
    public void initialize(){
        for (Month month : Month.values()) {
            monthComboBox.getItems().add(month);
        }
        for(Appointment appointment : allAppointments) {
            if(!typeComboBox.getItems().contains(appointment.getType())) {
                typeComboBox.getItems().add(appointment.getType());
            }
        }
    }

    /**
     * Triggers when a selection is made in the month combo box. If there is a selection in the type combo box, this
     * method also populates the total field with the total number of appointments that match both parameters.
     *
     * @param e Dummy action event variable that is not used
     */
    public void monthComboBoxSelected(ActionEvent e) {
        if (typeComboBox.getSelectionModel().getSelectedItem() != null) {
            totalField.setText(Database.getMonthTypeAppointmentTotal(monthComboBox.getSelectionModel().getSelectedItem(),
                    typeComboBox.getSelectionModel().getSelectedItem()));
        }
    }

    /**
     * Triggers when a selection is made in the type combo box. If there is a selection in the month combo box, this
     * method also populates the total field with the total number of appointments that match both parameters.
     *
     * @param e Dummy action event variable that is not used
     */
    public void typeComboBoxSelected(ActionEvent e) {
        if (monthComboBox.getSelectionModel().getSelectedItem() != null) {
            totalField.setText(Database.getMonthTypeAppointmentTotal(monthComboBox.getSelectionModel().getSelectedItem(),
                    typeComboBox.getSelectionModel().getSelectedItem()));
        }
    }

    /**
     * Closes the dialog.
     */
    public void closeStage() {
        Stage stage = (Stage) monthComboBox.getScene().getWindow();
        stage.close();
    }

    /**
     * Calls the close stage method when the exit button is clicked.
     */
    @FXML
    public void exitButtonClicked() {
        closeStage();
    }
}
