package com.example.fxgame;

import areas.Location;
import characters.Hero;
import items.Equippable;
import items.Inventory;
import items.Item;
import items.Usable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ViewInventory implements AppAwareController{
    @FXML
    private GridPane inventoryGrid;

    @FXML
    private Text msgtxt;

    private RPGApplication app;

    private Inventory inventory;

    private Item selectedItem;

    private Location location;

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setApp(RPGApplication app) {
        this.app = app;
    }


    public void renderInventory(Inventory inventory) {
        inventoryGrid.getChildren().clear();

        // Create a ToggleGroup for the radio buttons
        ToggleGroup toggleGroup = new ToggleGroup();

        // Create header labels for the table
        Label headerNo = new Label("No");
        Label headerItem = new Label("Item");
        Label headerDescription = new Label("Description");
        Label headerPrice = new Label("Price");
        Label headerSelect = new Label("Select");

        // Add headers to the first row
        inventoryGrid.add(headerNo, 0, 0);
        inventoryGrid.add(headerItem, 1, 0);
        inventoryGrid.add(headerDescription, 2, 0);
        inventoryGrid.add(headerPrice, 3, 0);
        inventoryGrid.add(headerSelect, 4, 0);

        int i = 1;
        for (Item item : inventory) {
            Label no = new Label(String.valueOf(i));

            Label itemLabel = new Label(item.name);

            Label description = new Label(item.description);
            Label price = new Label(String.valueOf(item.value));

            // Create a radio button for selection and add it to the toggle group
            RadioButton selectButton = new RadioButton();
            selectButton.setToggleGroup(toggleGroup);

            // Add an event listener to update selectedItem when this button is selected
            selectButton.selectedProperty().addListener((observable, oldValue, isSelected) -> {
                if (isSelected) {
                    selectedItem = item;
                }
            });

            // Add the components to the grid in the current row
            inventoryGrid.add(no, 0, i);
            inventoryGrid.add(itemLabel, 1, i);
            inventoryGrid.add(description, 2, i);
            inventoryGrid.add(price, 3, i);
            inventoryGrid.add(selectButton, 4, i);
            i++;
        }
    }

    public boolean selectedItemCheck() {
        if (selectedItem == null) {
            msgtxt.setText("No item selected");
            return false;
        }
        return true;
    }

    public void dropItem() {
        if (selectedItemCheck()) {
            inventory.remove(selectedItem);
            location.items.add(selectedItem);
        }
        renderInventory(inventory);
    }

    public void useItem() {
        if (selectedItemCheck()) {
            if (!(selectedItem instanceof Usable)) {
                msgtxt.setText("That item cannot be used!");
                return;
            }
            if (((Usable) selectedItem).useItem(app.getHero())) {
                inventory.remove(selectedItem);
                msgtxt.setText("You used the " + selectedItem.name);
            }
            renderInventory(inventory);
        }
    }

    public void equipItem() {
        if (selectedItemCheck()) {
            Hero player = app.getHero();
            if (!(selectedItem instanceof Equippable)) {
                msgtxt.setText("That item cannot be equipped!");
            }
            else if (player.equippedItems.size() >= 5) {
                msgtxt.setText("You cannot equip any more items!");
            }
            else {
                for (Equippable item : player.equippedItems) {
                    if (item.getClass() == selectedItem.getClass()) {
                        msgtxt.setText(String.format("You have already equipped a %s!\n", item.getClass().getSimpleName().toLowerCase()));
                        return;
                    }
                }
                inventory.remove(selectedItem);
                player.equippedItems.add((Equippable) selectedItem);
                ((Equippable) selectedItem).equip(player);
            }
        }
        renderInventory(inventory);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void back(ActionEvent actionEvent) throws IOException {
        app.enterRoom(location, app.getHero());
    }
}
