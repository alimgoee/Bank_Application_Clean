package com.example.alik.controllers;

import com.example.alik.database.DBConnector;
import com.example.alik.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Label errorLoginPasswordText;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Label emailLabel;  // Должно совпадать с fx:id в FXML

    @FXML
    private Label balanceLabel;

    int height = 500;
    int width = 500;
    private User currentUser = null;
    @FXML
    protected void onLoginButtonClick() {
        try {
            String email = emailField.getText();
            String password = passwordField.getText();
            currentUser = DBConnector.getCurrentUser(email, password);

            if (currentUser != null) {
                System.out.println(currentUser.toString());
                UserSession.setUser(currentUser); // Сохраняем пользователя

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/alik/profilePage.fxml"));
                Parent root = fxmlLoader.load();

                // Получаем текущее окно (Stage)
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root, 500, 500)); // Размер окна
                stage.show();
            }else{
                errorLoginPasswordText.setText("Invalid email or password!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void goRegister() {
       try {
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/alik/register.fxml"));
           Parent root = fxmlLoader.load();

           // Получаем текущее окно (Stage)
           Stage stage = (Stage) emailField.getScene().getWindow();
           stage.setScene(new Scene(root, 500, 600)); // Размер окна
           stage.show();
       }catch (Exception e) {
           e.printStackTrace();
       }
    }

    @FXML
    public void initialize() {
        User user = UserSession.getUser();
        if (user != null) {
            emailLabel.setText(user.getEmail());
            balanceLabel.setText(user.getTotalBalance() + " тг");
        }
    }
}