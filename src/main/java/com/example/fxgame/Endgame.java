package com.example.fxgame;

import javafx.event.ActionEvent;

import java.io.IOException;

public class Endgame implements AppAwareController {

    private RPGApplication app;

    public void newGame(ActionEvent actionEvent) throws IOException {
        app.startCharacterCreation();
    }

    @Override
    public void setApp(RPGApplication app) {
        this.app = app;
    }
}
