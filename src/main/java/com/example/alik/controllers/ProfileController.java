package com.example.alik;

import com.example.alik.controllers.TransferController;
import com.example.alik.controllers.UserSession;
import com.example.alik.database.DBConnector;
import com.example.alik.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ProfileController {
    @FXML
    private TextField emailField;

    @FXML
    private Label emailLabel;

    @FXML
    private Label balanceLabel;
    @FXML private
    ChoiceBox<String> recipientDropdown;

    private User currentUser;
    @FXML
    public void setUser(User user) {
        this.currentUser = user;
        if (user != null) {
            balanceLabel.setText(user.getTotalBalance() + " тг");
        }
    }
    public void initialize() {
        User currentUser = UserSession.getUser();
        if (currentUser != null) {
            emailLabel.setText(currentUser.getEmail());
            balanceLabel.setText(currentUser.getTotalBalance() + " тг");
        }
    }

    public void setUsersList(ArrayList<User> users) {
        if (users != null) {
            for (User user : users) {
                recipientDropdown.getItems().add(user.getEmail()); // Добавляем email в ChoiceBox
            }
        }
    }

    @FXML
    protected void goPerevod() {
        // Логика перехода на страницу перевода
        try {
            User currentUser = UserSession.getUser();

            emailLabel.setText(currentUser.getEmail());
            balanceLabel.setText(currentUser.getTotalBalance() + " тг");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/alik/perevod.fxml"));
            Parent root = fxmlLoader.load();
            System.out.println(currentUser.toString());

            TransferController transferController = fxmlLoader.getController();
            transferController.setUser(UserSession.getUser());

            List<User> userList = DBConnector.getAllUsers(currentUser);  // Метод получения списка пользователей
            transferController.setUsersList(userList);

            // Получаем текущее окно (Stage)
            Stage stage = (Stage) balanceLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 500, 500)); // Размер окна
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void goMyBank() {
        // Логика перехода в "Мой банк"
    }

    @FXML
    protected void goShop() {
        // Логика перехода в магазин
    }
}
