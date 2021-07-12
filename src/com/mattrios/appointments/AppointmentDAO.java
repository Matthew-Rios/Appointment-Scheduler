package com.mattrios.appointments;

import com.mattrios.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * @author Matt Rios
 */
public class AppointmentDAO {

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * Calls PreparedStatements.getAllAppointments to generate the initial Observable list which holds all the
     * appointment objects. This list is then returned to the calling method to populate the appointments tableview.
     *
     * @return allAppointments
     */
    public static ObservableList<Appointment> getAllUserAppointments() {
        allAppointments = Database.getAllUserAppointments();
        return allAppointments;
    }

    public static ObservableList<Appointment> getAllAppointments() {
        return Database.getAllAppointments();
    }

    /**
     * Adds a new appointment passed from the calling method into the allAppointments observable list.
     *
     * @param appointment the appointment to be added to the list
     */
    public static void addAppointment(Appointment appointment) {
        allAppointments.add(appointment);
    }

    /**
     * Removes an appointment passed from the calling method from the allAppointments observable list.
     *
     * @param appointment the appointment to be removed from the list.
     */
    public static void removeAppointment(Appointment appointment) {
        allAppointments.remove(appointment);
    }


    /**
     * Updates an existing appointment in the allAppointments list by removing the original appointment which matches
     * the appointmentID and adding the new appointment passed to this method.
     *
     * @param removeAppointmentID the ID of the appointment to be removed.
     * @param addAppointment the appointment to be added to the list.
     */
    public static void editAppointment(int removeAppointmentID, Appointment addAppointment) {
        for (Appointment appointment : allAppointments) {
            if (appointment.getAppointmentID() == removeAppointmentID) {
                allAppointments.remove(appointment);
                break;
            }
        }
        allAppointments.add(addAppointment);
    }

    public static Appointment lookupAppointment(int appointmentId) {
        Appointment appointmentToLookup = null;

        for (Appointment appointment : allAppointments) {
            if (appointment.getAppointmentID() == appointmentId) {
                appointmentToLookup = appointment;
                break;
            }
        }

        if (appointmentToLookup == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not find appointment id: " + appointmentId);
            alert.showAndWait();
        }

        return appointmentToLookup;
    }

    public static ObservableList<Appointment> lookupAppointment(String appointmentTitle) {
        ObservableList<Appointment> matchingAppointments = FXCollections.observableArrayList();
        appointmentTitle = appointmentTitle.toLowerCase();

        if (appointmentTitle == null) {
            return allAppointments;
        }

        for (Appointment appointment : allAppointments) {
            if (appointment.getTitle().toLowerCase().contains(appointmentTitle)) {
                matchingAppointments.add(appointment);
            }
        }

        if (matchingAppointments.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not find appointments matching appointment title: "
                    + appointmentTitle);
            alert.showAndWait();
        }

        return matchingAppointments;
    }
}
