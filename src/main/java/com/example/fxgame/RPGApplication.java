package com.example.fxgame;

import areas.Dungeon;
import areas.Location;
import characters.Hero;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RPGApplication extends Application {

    private Stage stage;
    private final double v = 800;
    private final double v1 = 600;
    private Hero hero;
    private Dungeon dungeon;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setTitle("Dungeon Bash");
        showStartPage();
    }

    public void showStartPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-page.fxml"));
        Parent startPage = loader.load();
        RPGController controller = loader.getController();
        controller.setApp(this);

        Scene startPageScene = new Scene(startPage, v, v1);
        stage.setScene(startPageScene);
        stage.show();
    }

    public void startCharacterCreation() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("character-creation.fxml"));
        Parent characterCreation = loader.load();
        CharacterCreation controller = loader.getController();
        controller.setApp(this);

        Scene characterCreationScene = new Scene(characterCreation, v, v1);
        stage.setScene(characterCreationScene);
        stage.show();
    }

    public void startDungeonRun(Hero hero) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dungeon-run.fxml"));
        Parent dungeonRun = loader.load();
        DungeonRun controller = loader.getController();
        controller.setApp(this);
        controller.setHero(hero);

        Scene dungeonRunScene = new Scene(dungeonRun, v, v1);
        stage.setScene(dungeonRunScene);
        stage.show();
    }

    public void enterRoom(Location location, Hero hero) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dungeon-room.fxml"));
        Parent dungeonRoom = loader.load();
        DungeonRoom controller = loader.getController();
        controller.setApp(this);
        controller.setHero(hero);
        controller.setLocation(location);
        this.setDungeon(dungeon);

        Scene dungeonRoomScene = new Scene(dungeonRoom, v, v1);
        stage.setScene(dungeonRoomScene);
        stage.show();
    }

    public void viewMap(Location location, Hero hero) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("map-page.fxml"));
        Parent mapPage = loader.load();
        MapPage controller = loader.getController();
        controller.setApp(this);
        controller.setHero(hero);
        controller.setLocation(location);
        controller.setDungeon(dungeon);
        controller.drawMap();

        Scene mapPageScene = new Scene(mapPage, v, v1);
        stage.setScene(mapPageScene);
        stage.show();
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