package com.example.alik.controllers;

import com.example.alik.client.ChatClient;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ChatController {
    @FXML
    private ListView<String> chatArea;
    @FXML
    private TextField messageField;

    private ChatClient client;

    @FXML
    public void initialize() {
        client = new ChatClient("localhost", 12345, message ->
                chatArea.getItems().add(message)
        );
    }

    @FXML
    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            client.sendMessage("Клиент: " + message);
            chatArea.getItems().add("Вы: " + message);
            messageField.clear();
        }
    }
}
