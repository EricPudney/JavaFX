package com.example.fxgame;

import areas.Dungeon;
import areas.Location;
import characters.Hero;
import items.Inventory;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Alerts;

import java.io.IOException;

public class RPGApplication extends Application {

    private SceneManager sceneManager;
    private final double v = 800;
    private final double v1 = 600;
    private Hero hero;
    private Dungeon dungeon;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Dungeon Bash");
        this.sceneManager = new SceneManager(stage, this);
        showStartPage();
    }

    public void showStartPage() throws IOException {
        sceneManager.switchScene("start-page.fxml", v, v1, null);
    }

    public void startCharacterCreation() throws IOException {
        sceneManager.switchScene("character-creation.fxml", v, v1, null);
    }

    public void startDungeonRun() throws IOException {
        sceneManager.switchScene("dungeon-run.fxml", v, v1, null);
    }

    public void enterRoom(Location location) throws IOException {
        sceneManager.switchScene("dungeon-room.fxml", v, v1, controller -> {
            DungeonRoom dungeonRoom = (DungeonRoom) controller;
            try {
                dungeonRoom.setLocation(location);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void viewMap(Location location) throws IOException {
        sceneManager.switchScene("map-page.fxml", v, v1, controller -> {
            MapPage mapPage = (MapPage) controller;
            mapPage.setLocation(location);
            mapPage.drawMap();
        });
    }

    public void viewInventory(Inventory inventory, Location location) throws IOException {
        sceneManager.switchScene("view-inventory.fxml", v, v1, controller -> {
            ViewInventory viewInventory = (ViewInventory) controller;
            viewInventory.setInventory(inventory);
            viewInventory.setLocation(location);
            viewInventory.renderInventory(inventory);
        });
    }

    public void endGame() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            try {
                sceneManager.switchScene("endgame.fxml", v, v1, null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        pause.play();
    }

    public void exitDungeon() throws IOException {
        Alerts.showAlert("Dungeon run complete!", "You made it! You head to town to resupply before your next adventure.", Alert.AlertType.INFORMATION);
        sceneManager.switchScene("shop.fxml", v, v1, null);
    }

    public static void main(String[] args) {
        launch();
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }


}