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

import java.io.InputStream;
import java.util.Random;

public class DungeonRun {
    private RPGApplication app;

    Hero hero;

    public void setApp(RPGApplication app) {
        this.app = app;
    }

    @FXML
    private GridPane dungeonGrid;

    @FXML
    Label width;

    @FXML
    Label height;

    private Dungeon dungeon;

    static Random rng = new Random();

    public void initialize() {
        int height = 3 + rng.nextInt(1, 3);
        int width = 3 + rng.nextInt(1, 3);
        dungeon = new Dungeon(height, width);
    }

    private void populateDungeonGrid() {
        int rows = dungeon.getWidth();
        int cols = dungeon.getHeight();

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                // Create a Pane or Button for each dungeon cell
                Button cell = new Button();
                cell.setPrefSize(50, 50);

                // Add some style to differentiate different rooms
                cell.setStyle("-fx-background-color: lightgray;");

                // Add image based on Location type (e.g., player, enemy, treasure)
                Location location = dungeon.getLocation(col, row);
                ImageView imageView = getImageForLocation(location);

                if (imageView != null) {
                    cell.setGraphic(imageView); // Set the image in the button
                }

                // Set an action to handle interactions with the cell
                int finalRow = row;
                int finalCol = col;
                cell.setOnAction(e -> handleCellClick(finalCol, finalRow));

                // Add the cell to the grid at the correct row/column position
                dungeonGrid.add(cell, col, row);
            }
            width.setText("width: " + rows);
            height.setText("height: " + cols);
        }
    }

    private ImageView getImageForLocation(Location location) {
        ImageView image = null;
        if (hero.currentLocation == location) {
            Image entranceImage = loadImage("/images/entrance.png");
            if (entranceImage != null) {
                image = new ImageView(entranceImage);
            }
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


    // Handle a cell click and show information about the location
    private void handleCellClick(int row, int col) {
        Location location = dungeon.getLocation(row, col);
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        hero.currentLocation = dungeon.getLocation(Math.round((float) dungeon.getWidth() /2) - 1, 0);
        populateDungeonGrid();
    }
}
