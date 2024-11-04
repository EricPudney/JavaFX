package com.example.fxgame;

import areas.Dungeon;
import areas.Location;
import characters.Hero;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;

public class DungeonRoom {

    private RPGApplication app;

    private Hero hero;

    private Location location;

    private Dungeon dungeon;

    @FXML
    private ImageView image;

    @FXML
    private Text head;

    @FXML
    private Text monster;

    @FXML
    private Text item;

    @FXML
    private Text characterInfo;

    @FXML
    private Text messageText;


    public void initialize() {

    }

    private void getImageForLocation() {
        Image locationImage = loadImage(location.getImagePath());
        image.setImage(locationImage);
        head.setText(location.getLocationType());
        monster.setText(location.generateMonsterDescription());
        item.setText(location.generateItemText());
        characterInfo.setText(hero.toString());
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

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setLocation(Location location) {
        this.location = location;
        location.explored = true;
        getImageForLocation();
    }

    public void goNorth() {
        if (location.noWayNorth) {
            messageText.setText("You can't go that way!");
            return;
        }
        messageText.setText("You go north...");
        int[] position = findCurrentLocation(dungeon);
        setLocation(dungeon.getLocations()[position[0] - 1][position[1]]);
    }

    public void goSouth() {
        if (location.noWaySouth) {
            messageText.setText("You can't go that way!");
            return;
        }
        messageText.setText("You go south...");
        int[] position = findCurrentLocation(dungeon);
        setLocation(dungeon.getLocations()[position[0] + 1][position[1]]);
    }

    public void goEast() {
        if (location.noWayEast) {
            messageText.setText("You can't go that way!");
            return;
        }
        messageText.setText("You go east...");
        int[] position = findCurrentLocation(dungeon);
        setLocation(dungeon.getLocations()[position[0]][position[1] + 1]);
    }

    public void goWest() {
        if (location.noWayWest) {
            messageText.setText("You can't go that way!");
            return;
        }
        messageText.setText("You go west...");
        int[] position = findCurrentLocation(dungeon);
        setLocation(dungeon.getLocations()[position[0]][position[1] - 1]);
    }

    public void viewMap() throws IOException {
        app.viewMap(location, hero);
    }

    public int[] findCurrentLocation(Dungeon dungeon) {
        int[] position = {0, 0};
        for (int i = 0; i < dungeon.getLocations().length; i++) {
            for (int j = 0; j < dungeon.getLocations()[i].length; j++) {
                if (dungeon.getLocations()[i][j] == location) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }

    public void setApp(RPGApplication app) {
        this.app = app;
        this.dungeon = app.getDungeon();
    }
}
