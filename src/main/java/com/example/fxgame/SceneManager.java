package com.example.fxgame;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class SceneManager {
    private Stage stage;
    private RPGApplication app;

    public SceneManager(Stage stage, RPGApplication app) {
        this.stage = stage;
        this.app = app;
    }

    public <T> T switchScene(String fxmlFile, double width, double height, Consumer<T> controllerSetup) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        T controller = loader.getController();
        ((AppAwareController) controller).setApp(app);

        if (controller instanceof HeroAwareController) {
            ((HeroAwareController) controller).setHero(app.getHero());
        }

        if (controller instanceof DungeonAwareController) {
            ((DungeonAwareController) controller).setDungeon(app.getDungeon());
        }

        // Allow customization of the controller setup
        if (controllerSetup != null) {
            controllerSetup.accept(controller);
        }

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();

        return controller;
    }
}
