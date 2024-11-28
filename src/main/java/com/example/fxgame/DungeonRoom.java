package com.example.fxgame;

import areas.Dungeon;
import areas.Location;
import characters.Hero;
import characters.Monster;
import com.example.fxgame.components.InventoryView;
import com.example.fxgame.components.ItemActions;
import items.Equippable;
import items.Inventory;
import items.Item;
import items.Usable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DungeonRoom implements AppAwareController, HeroAwareController, InventoryView.ItemHolder {

    private RPGApplication app;
    private Hero hero;
    private Location location;
    private Dungeon dungeon;
    private final InventoryView inventoryView = new InventoryView();
    private Item selectedItem;

    @FXML
    private HBox actionButtons;

    @FXML
    private GridPane inventoryGrid;

    @FXML
    private HBox buttonBox;

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
    private ScrollableTextArea messageText;


    private void getImageForLocation() {
        Image locationImage = loadImage(location.getImagePath());
        image.setImage(locationImage);
        head.setText(location.getLocationType());
        generateText();
    }

    private void generateText() {
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

    public boolean monsterCheck() {
        if (location.getEnemy() != null && location.getEnemy().isAlive) {
            messageText.appendText(String.format("An enemy %s blocks your way!", location.getEnemy().name));
            return true;
        }
        return false;
    }

    public void goNorth() throws IOException {
        move(Direction.north);
    }

    public void goSouth() throws IOException {
        move(Direction.south);
    }

    public void goEast() throws IOException {
        move(Direction.east);
    }

    public void goWest() throws IOException {
        move(Direction.west);
    }

    public void move(Direction direction) throws IOException {
        if (monsterCheck()) {
            return;
        }
        if (location.noWayWest && direction == Direction.west ||
                location.noWayEast && direction == Direction.east ||
                location.noWayNorth && direction == Direction.north ||
                location.noWaySouth && direction == Direction.south) {
            messageText.appendText("You can't go that way!");
        } else {
            messageText.appendText("You go " + direction + "...");
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
            messageText.appendText("You can't check the map while facing an enemy!");
            return;
        }
        app.viewMap(location);
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

    public void viewInventory() {
        inventoryGrid.setManaged(true);
        buttonBox.setManaged(true);
        actionButtons.setManaged(false);
        actionButtons.setVisible(false);
        List<ItemActions> actions = new ArrayList<>();
        actions.add(ItemActions.DROP);
        actions.add(ItemActions.USE);
        actions.add(ItemActions.EQUIP);
        actions.add(ItemActions.BACK);
        inventoryView.renderInventory(inventoryGrid, buttonBox, hero.inventory, this, actions);
    }

    public void takeItem(ActionEvent actionEvent) {
        Inventory locationItems = location.items;
        if (locationItems.isEmpty()) {
            messageText.appendText("There is nothing here to take!");
            return;
        }
        if (locationItems.size() == 1) {
            Item item = locationItems.removeFirst();
            if (hero.inventory.addToInventory(item)) {
                messageText.appendText(String.format("You added the %s to your inventory.\n", item.name));
                if (item.name.equals("Treasure")) {
                    hero.foundTreasure = true;
                    messageText.appendText("You found the treasure! Return to the entrance to leave this dungeon.");
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
        generateText();
    }

    public void evade(ActionEvent actionEvent) {
        if (location.getEnemy() == null) {
            messageText.appendText("There is nothing to evade!");
            return;
        }
        double rng = Math.random();
        if (hero.evasion > rng) {
            location.setEnemyToNull();
            hero.addXp(2);
            messageText.appendText("You have successfully evaded the monster.");
        }
        else {
            messageText.appendText(String.format("You failed to evade the %s!\n", location.getEnemy().name));
            messageText.appendText(location.getEnemy().attack(hero));
        }
        generateText();
        if (!hero.isAlive) {
            app.endGame();
        }
    }

    public void attack(ActionEvent actionEvent) {
        Monster enemy = location.getEnemy();
        if (enemy == null) {
            messageText.appendText("There is nothing to attack!");
        }
        else {
            generateText();
            if (enemy.initiative > hero.initiative) {
                messageText.appendText(String.format("The %s is too fast for you, and attacks!\n" , enemy.name) + enemy.attack(hero));
                if (!hero.isAlive) {
                    app.endGame();
                }
                messageText.appendText(String.format("%s attacks the %s!\n", hero.name, enemy.name) + hero.attack(enemy));
                generateText();
            }
            else {
                messageText.appendText(String.format("%s attacks the %s!\n", hero.name, enemy.name) + hero.attack(enemy));
                if (!enemy.isAlive) {
                    generateText();
                    return;
                }
                messageText.appendText(String.format("The %s attacks back!\n" , enemy.name) + enemy.attack(hero));
                generateText();
            }
        }
    }

    public boolean selectedItemCheck() {
        if (selectedItem == null) {
            messageText.appendText("No item selected");
            return false;
        }
        return true;
    }

    public void dropItem() {
        if (selectedItemCheck()) {
            hero.inventory.remove(selectedItem);
            location.items.add(selectedItem);
            messageText.appendText("You dropped the " + selectedItem.name);
        }
        viewInventory();
        generateText();
    }

    public void useItem() {
        if (selectedItemCheck()) {
            if (!(selectedItem instanceof Usable)) {
                messageText.appendText("That item cannot be used!");
                return;
            }
            if (((Usable) selectedItem).useItem(app.getHero())) {
                hero.inventory.remove(selectedItem);
                messageText.appendText("You used the " + selectedItem.name);
            }
            viewInventory();
            generateText();
        }
    }

    public void equipItem() {
        if (selectedItemCheck()) {
            Hero player = app.getHero();
            if (!(selectedItem instanceof Equippable)) {
                messageText.appendText("That item cannot be equipped!");
            }
            else if (player.equippedItems.size() >= 5) {
                messageText.appendText("You cannot equip any more items!");
            }
            else {
                for (Equippable item : player.equippedItems) {
                    if (item.getClass() == selectedItem.getClass()) {
                        messageText.appendText(String.format("You have already equipped a %s!\n", item.getClass().getSimpleName().toLowerCase()));
                        return;
                    }
                }
                hero.inventory.remove(selectedItem);
                player.equippedItems.add((Equippable) selectedItem);
                ((Equippable) selectedItem).equip(player);
            }
        }
        viewInventory();
        generateText();
    }

    // seems clunky but it works
    public void back(ActionEvent actionEvent) {
        inventoryGrid.setManaged(false);
        buttonBox.setManaged(false);
        inventoryGrid.setVisible(false);
        buttonBox.setVisible(false);
        actionButtons.setManaged(true);
        actionButtons.setVisible(true);
        for (Node child : actionButtons.getChildren()) {
            child.setVisible(true);
            child.setManaged(true);
        }
        generateText();
    }

    public void setTextArea() {
        if (messageText == null) {
            this.messageText = new ScrollableTextArea();
        }
    }

    public void setApp(RPGApplication app) {
        this.app = app;
        this.dungeon = app.getDungeon();
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setLocation(Location location) throws IOException {
        this.location = location;
        location.explored = true;
        if (this.location.isStart && hero.foundTreasure) {
            app.exitDungeon();
        }
        getImageForLocation();
    }

    @Override
    public void setSelectedItem(Item item) {
        this.selectedItem = item;
    }


    enum Direction {
        north, south, east, west
    }
}
