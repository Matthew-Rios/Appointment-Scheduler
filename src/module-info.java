module C868 {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    opens com.mattrios;
    opens com.mattrios.main;
    opens com.mattrios.login;
    opens com.mattrios.users;
    opens com.mattrios.appointments;
    opens com.mattrios.customers;
    opens com.mattrios.reports;
}