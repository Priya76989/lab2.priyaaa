module org.example.priya {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Add this if you are using SQL features
    // Add other modules as needed

    opens org.example.priya to javafx.fxml; // Adjust package name as necessary
    exports org.example.priya; // Adjust package name as necessary
}
