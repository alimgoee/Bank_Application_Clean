package com.example.alik.controllers;
import com.example.alik.HelloApplication;
import com.example.alik.controllers.UserSession;
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

import java.util.List;

public class RegisterController {

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField balanceField;
    @FXML
    private Label errorLabel;

    @FXML
    protected void getRegisterButtonAction() {
        int login = Integer.parseInt(loginField.getText());
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        int balance = Integer.parseInt(balanceField.getText());

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setTotalBalance(balance);
        user.setLogin(login);

        if (DBConnector.ondaiAdamBarMA(user) == false){
            if (DBConnector.addNewUser(user)){
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/alik/hello-view.fxml"));
                    Parent root = fxmlLoader.load();
                    // Получаем текущее окно (Stage)
                    Stage stage = (Stage) emailField.getScene().getWindow();
                    stage.setScene(new Scene(root, 500, 500)); // Размер окна
                    stage.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else{
            errorLabel.setText("Вы не смогли зарегаться!!!");
        }
    }

    @FXML
    protected void nazad() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/alik/hello-view.fxml"));
            Parent root = fxmlLoader.load();

            // Получаем текущее окно (Stage)
            Stage stage = (Stage) balanceField.getScene().getWindow();
            stage.setScene(new Scene(root, 500, 500)); // Размер окна
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
