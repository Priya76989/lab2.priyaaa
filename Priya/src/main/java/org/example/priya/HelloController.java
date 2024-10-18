
package org.example.priya;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {


    public TextField iid;
    public TextField istudent;
    public TextField iteacher;
    public TextField iclass;

    @FXML
    private TableView<tution> tableView;
    @FXML
    private TableColumn<tution, Integer> id;
    @FXML
    private TableColumn<tution, String> student;
    @FXML
    private TableColumn<tution, String> teacher;
    @FXML
    private TableColumn<tution, Integer> className;

    private ObservableList<tution> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        student.setCellValueFactory(new PropertyValueFactory<>("student"));
        teacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        className.setCellValueFactory(new PropertyValueFactory<>("className"));

        // Set items to the table view
        tableView.setItems(list);
    }

    @FXML
    protected void onHelloButtonClick() {
        System.out.println("CLICKED");
        populateTable();
    }

    public void populateTable() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_lab1_priya";
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM tbl_tution";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            list.clear();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No data found in the database.");
            } else {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String studentName = resultSet.getString("student");
                    String teacherName = resultSet.getString("teacher");
                    int classNumber = resultSet.getInt("class");


                    list.add(new tution(id, studentName, teacherName, classNumber));
                }
                tableView.setItems(list); // Refresh the table view
                System.out.println("Data loaded successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
        }
    }

    public void onupdatebottonclick(ActionEvent actionEvent) {
        String id = iid.getText();          // Get ID from input
        String teacher = iteacher.getText(); // Get teacher from input
        String className = iclass.getText(); // Get class from input
        String student = istudent.getText(); // Get student from input

        // Constructing the update query using string concatenation
        String query = "UPDATE tbl_tution SET teacher = '" + teacher +
                "', class = '" + className +
                "', student = '" + student +
                "' WHERE id = " + id;

        String jdbcUrl = "jdbc:mysql://localhost:3306/db_lab1_priya"; // Change to your DB URL
        String dbUser = "root"; //
        String dbPassword = ""; //

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(query); // Execute the update query

            if (rowsAffected > 0) {
                System.out.println("Update successful. Rows affected: " + rowsAffected);
            } else {
                System.out.println("No rows affected. Check if the ID exists.");
            }
            onHelloButtonClick();
        } catch (SQLException e) {
            System.err.println("Update failed!");
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
        }
    }


    public void oninsertButtonClick(ActionEvent actionEvent) {
        String id = iid.getText();
        String teacher = iteacher.getText();
        String className = iclass.getText();
        String student = istudent.getText();

        String jdbcUrl = "jdbc:mysql://localhost:3306/db_lab1_priya"; // Change to your DB URL
        String dbUser = "root"; //
        String dbPassword = ""; //

        String query = "INSERT INTO tbl_tution (teacher, class, student) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, teacher);
            preparedStatement.setString(2, className);
            preparedStatement.setString(3, student);

            int rowsAffected = preparedStatement.executeUpdate(); // Execute the insert query

            if (rowsAffected > 0) {
                System.out.println("Insert successful. Rows affected: " + rowsAffected);
            } else {
                System.out.println("Insert failed. No rows were affected.");
            }
            onHelloButtonClick(); // Refresh the table or perform any other required actions
        } catch (SQLException e) {
            System.err.println("Insert failed!");
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
        }
    }

    @FXML
    public void onDeleteButtonClick(ActionEvent actionEvent) {
        String id = iid.getText();


        if (id.isEmpty()) {
            System.out.println("ID cannot be empty. Please enter a valid ID.");
            return; // Exit the method if ID is empty
        }

        // Database connection parameters
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_lab1_priya";
        String dbUser = "root";
        String dbPassword = "";

        // SQL delete query
        String query = "DELETE FROM tbl_tution WHERE id = ?";

        // Using try-with-resources to ensure proper resource management
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {


            preparedStatement.setString(1, id);

            int rowsAffected = preparedStatement.executeUpdate();


            if (rowsAffected > 0) {
                System.out.println("Delete successful. Rows affected: " + rowsAffected);
            } else {
                System.out.println("No rows affected. Check if the ID exists.");
            }


            onHelloButtonClick();
        } catch (SQLException e) {
            System.err.println("Delete failed!");
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
        }
    }


    public void onCreate(ActionEvent actionEvent) {
        String id = iid.getText();
        String teacher = iteacher.getText();
        String className = iclass.getText();
        String student = istudent.getText();


    }


    }
