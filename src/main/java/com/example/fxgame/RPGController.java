package com.example.fxgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class RPGController {

    private RPGApplication app;

    public void setApp(RPGApplication app) {
        this.app = app;
    }

    @FXML
    public void clickToPlay(ActionEvent actionEvent) throws IOException {
        app.startCharacterCreation();
    }
}