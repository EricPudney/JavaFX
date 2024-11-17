package com.example.fxgame;

import areas.Dungeon;
import areas.Location;
import characters.Hero;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;

public class MapPage implements AppAwareController, HeroAwareController, DungeonAwareController{
    private RPGApplication app;

    private Hero hero;

    private Dungeon dungeon;

    private Location location;

    @FXML
    private GridPane dungeonGrid;

    @FXML
    private Text msgtxt;

    @FXML
    private ImageView xMark;

    @FXML
    private ImageView entrance;

    public void setApp(RPGApplication app) {
        this.app = app;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void drawMap() {
        int rows = dungeon.getHeight();
        int cols = dungeon.getWidth();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Button cell = new Button();
                cell.setPrefSize(65, 65);
                Location location = dungeon.getLocation(row, col);
                if (location.explored) {
                    cell.setStyle("-fx-background-color: darkgray;");
                }
                else {
                    cell.setStyle("-fx-background-color: lightgray;");
                }

                ImageView imageView = getImageForLocation(location);

                if (imageView != null) {
                    cell.setGraphic(imageView);
                }

                cell.setOnAction(e -> {
                    try {
                        handleCellClick(location);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                dungeonGrid.add(cell, col, row);
            }
        }
        xMark.setImage(loadImage("/images/x_mark.png"));
        entrance.setImage(loadImage("/images/entrance.png"));
    }

    private void handleCellClick(Location location) throws IOException {
        if (dungeon.isAdjacent(location, this.location) || (location == this.location)) {
            app.enterRoom(location, hero);
        }
        msgtxt.setText("You can't reach that location!");
    }

    private ImageView getImageForLocation(Location location) {
        ImageView image = null;
        if (location.isStart) {
            Image entranceImage = loadImage("/images/entrance.png");
            image = new ImageView(entranceImage);
        }
        else if (location == this.location) {
            Image hereImage = loadImage("/images/x_mark.png");
            image = new ImageView(hereImage);
        }
        return image;
    }

    private Image loadImage(String path) {
        InputStream imageStream = getClass().getResourceAsStream(path);
        if (imageStream != null) {
            return new Image(imageStream);
        } else {
            System.err.println("Image not found: " + path);
            return null;
        }
    }
}
