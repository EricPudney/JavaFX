package com.example.fxgame;

import areas.Dungeon;
import areas.Location;
import characters.Hero;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class DungeonRun implements AppAwareController, HeroAwareController {
    private RPGApplication app;

    private Hero hero;

    public void setApp(RPGApplication app) {
        this.app = app;
    }

    private Dungeon dungeon;

    static Random rng = new Random();

    @FXML
    private GridPane dungeonGrid;

    @FXML
    Label width;

    @FXML
    Label height;


    public void initialize() {
        int height = 3 + rng.nextInt(1, 3);
        int width = 3 + rng.nextInt(1, 3);
        dungeon = new Dungeon(height, width);
    }

    private void populateDungeonGrid() {
        int rows = dungeon.getHeight();
        int cols = dungeon.getWidth();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Button cell = new Button();
                cell.setPrefSize(65, 65);

                Location location = dungeon.getLocation(row, col);
                cell.setStyle("-fx-background-color: lightgray;");

                if (location.isStart) {
                    InputStream imageStream = getClass().getResourceAsStream("/images/entrance.png");
                    if (imageStream != null) {
                        Image entranceImage = new Image(imageStream);
                        ImageView imageView = new ImageView(entranceImage);
                        cell.setGraphic(imageView);
                    } else {
                        throw new RuntimeException();
                    }
                    cell.setOnAction(e -> {
                        try {
                            handleCellClick(location);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                }
                dungeonGrid.add(cell, col, row);
            }
        }
        width.setText("width: " + cols);
        height.setText("height: " + rows);
    }

    private void handleCellClick(Location location) throws IOException {
        app.setDungeon(dungeon);
        app.enterRoom(location, hero);
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        populateDungeonGrid();
    }
}
