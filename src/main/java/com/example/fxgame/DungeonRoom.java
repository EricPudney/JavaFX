package com.example.fxgame;

import areas.Location;
import characters.Hero;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class DungeonRoom {

    private RPGApplication app;

    private Hero hero;

    private Location location;

    @FXML
    private ImageView image;


    public void initialize() {
    }

    private void getImageForLocation() {
        Image locationImage = loadImage(location.getImagePath());
        image.setImage(locationImage);
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
        getImageForLocation();
    }

    public void setApp(RPGApplication app) {
        this.app = app;
    }
}
