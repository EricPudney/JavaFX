package com.example.fxgame;

import areas.Dungeon;
import areas.Location;
import characters.Hero;
import items.Inventory;
import items.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;

public class DungeonRoom implements AppAwareController, HeroAwareController {

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


    public void setApp(RPGApplication app) {
        this.app = app;
        this.dungeon = app.getDungeon();
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

    public boolean monsterCheck() {
        if (location.getEnemy() != null && location.getEnemy().isAlive) {
            messageText.setText(String.format("An enemy %s blocks your way!", location.getEnemy().name));
            return true;
        }
        return false;
    }

    public void goNorth() {
        move(Direction.north);
    }

    public void goSouth() {
        move(Direction.south);
    }

    public void goEast() {
        move(Direction.east);
    }

    public void goWest() {
        move(Direction.west);
    }

    public void move(Direction direction) {
        if (monsterCheck()) {
            return;
        }
        if (location.noWayWest && direction == Direction.west ||
                location.noWayEast && direction == Direction.east ||
                location.noWayNorth && direction == Direction.north ||
                location.noWaySouth && direction == Direction.south) {
            messageText.setText("You can't go that way!");
        } else {
            messageText.setText("You go " + direction + "...");
            int[] position = findCurrentLocation(dungeon);
            switch (direction) {
                case north:
                    setLocation(dungeon.getLocations()[position[0] - 1][position[1]]);
                    break;
                case south:
                    setLocation(dungeon.getLocations()[position[0] + 1][position[1]]);
                    break;
                case east:
                    setLocation(dungeon.getLocations()[position[0]][position[1] + 1]);
                    break;
                case west:
                    setLocation(dungeon.getLocations()[position[0]][position[1] - 1]);
                    break;
            }
        }
    }

    public void viewMap() throws IOException {
        if (monsterCheck()) {
            messageText.setText("You can't check the map while facing an enemy!");
            return;
        }
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

    public void viewInventory(ActionEvent actionEvent) throws IOException {
        app.viewInventory(hero.inventory, location);
    }

    public void takeItem(ActionEvent actionEvent) {
        Inventory locationItems = location.items;
        if (locationItems.isEmpty()) {
            messageText.setText("There is nothing here to take!");
            return;
        }
        if (locationItems.size() == 1) {
            Item item = locationItems.removeFirst();
            if (hero.inventory.addToInventory(item)) {
                messageText.setText(String.format("You added the %s to your inventory.\n", item.name));
                if (item.name.equals("Treasure")) {
                    hero.foundTreasure = true;
                }
            }
            else {
                locationItems.add(item);
            }
        }
//        else {
//            Item item = locationItems.selectFromInventory("take");
//            if (this.inventory.addToInventory(item)) {
//                locationItems.remove(item);
//                System.out.printf("You added the %s to your inventory.\n", item.name);
//                if (item.name.equals("Treasure")) {
//                    foundTreasure = true;
//                }
//            }
//            else {
//                locationItems.add(item);
//            }
        characterInfo.setText(hero.toString());
        item.setText(location.generateItemText());
    }

    public void evade(ActionEvent actionEvent) {
        double rng = Math.random();
        if (hero.evasion > rng) {
            location.setEnemyToNull();
            hero.addXp(2);
            messageText.setText("You have successfully evaded the monster.");
        }
        else {
            messageText.setText(String.format("You failed to evade the %s!\n", location.getEnemy().name));
            messageText.setText(messageText.getText() + location.getEnemy().attack(hero));
        }
    }


    enum Direction {
        north, south, east, west
    }
}
