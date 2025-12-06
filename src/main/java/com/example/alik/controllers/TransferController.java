package com.example.alik.controllers;

import com.example.alik.ProfileController;
import com.example.alik.database.DBConnector;
import com.example.alik.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class TransferController {
    @FXML
    private Label emailLabel;
    @FXML
    private ChoiceBox<String> recipientDropdown;

    private String selectedRecipient; // Поле для хранения выбранного получателя

    @FXML
    private Label balanceLabel;

    @FXML
    private TextField amountField;
    @FXML
    private TextField emailField;
    @FXML
    private Label perevodJasaldy;
    @FXML
    private Label akwaJok;
    @FXML
    private Label sanEngiz;

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
        if (user != null) {
            balanceLabel.setText(user.getTotalBalance() + " тг");
        }
    }

    public void setUsersList(List<User> userList) {
        ArrayList<User> users = DBConnector.getAllUsers(currentUser);
        if (users != null) {
            for (User user : users) {
                recipientDropdown.getItems().add(user.getEmail()); // Добавляем email в ChoiceBox
            }
        }
    }

    public void initialize() {
        User currentUser = UserSession.getUser();
        if (currentUser != null && (emailLabel != null) && (balanceLabel != null)) {
            emailLabel.setText(currentUser.getEmail());
            balanceLabel.setText(currentUser.getTotalBalance() + " тг");
        }

        // Добавляем слушателя на изменение выбора
        recipientDropdown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedRecipient = newValue; // Сохраняем выбранного пользователя
            System.out.println("Выбранный получатель: " + selectedRecipient);
        });
    }

    @FXML
    private void nazad() {
        try {
            // Получаем текущее окно через любой компонент на сцене
            Stage stage = (Stage) perevodJasaldy.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/alik/profilePage.fxml"));
            Parent root = loader.load();

            ProfileController profileController = loader.getController();
            profileController.setUser(UserSession.getUser());

            stage.setScene(new Scene(root, 500, 500)); // Размер окна
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void transferButton() {
        Integer summaPerevoda = null;
        try {
            summaPerevoda = Integer.parseInt(amountField.getText());
        } catch (Exception e) {
            e.printStackTrace();
            sanEngiz.setText("Введите число!!!");
        }
        if (selectedRecipient == null) {
            System.out.println("Ошибка: Получатель не выбран!");
            return;
        }

        if (summaPerevoda != null) {

            User currentUser = UserSession.getUser();
            User user_to = DBConnector.getUserByEmail(selectedRecipient);
            if (currentUser.getTotalBalance() >= summaPerevoda) {
                if (DBConnector.perevodJasa(currentUser, user_to, summaPerevoda)) {
                    System.out.println("Переводим деньги пользователю: " + selectedRecipient);
                    perevodJasaldy.setText("Перевод успешно выполнен!!!");
                    User new_user = DBConnector.getUserByEmail(currentUser.getEmail());
                    UserSession.setUser(new_user);
                }
            } else {
                akwaJok.setText("Недостаточно средств!!!");
            }
        }
    }



}
