package com.example.fxgame.components;

import items.Inventory;
import items.Item;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.function.Consumer;

public class InventoryView {

    public void renderInventory(GridPane inventoryGrid, HBox buttonBox, Inventory inventory, ItemHolder selectedItemHolder, List<ItemActions> actions
    ) {
        inventoryGrid.getChildren().clear();

        ToggleGroup toggleGroup = new ToggleGroup();

        // Create headers
        inventoryGrid.add(new Label("No"), 0, 0);
        inventoryGrid.add(new Label("Item"), 1, 0);
        inventoryGrid.add(new Label("Description"), 2, 0);
        inventoryGrid.add(new Label("Price"), 3, 0);
        inventoryGrid.add(new Label("Select"), 4, 0);

        int row = 1;
        for (Item item : inventory) {
            Label no = new Label(String.valueOf(row));
            Label itemLabel = new Label(item.name);
            Label description = new Label(item.description);
            Label price = new Label(String.valueOf(item.value));
            RadioButton selectButton = new RadioButton();
            selectButton.setToggleGroup(toggleGroup);

            selectButton.selectedProperty().addListener((observable, oldValue, isSelected) -> {
                if (isSelected) {
                    selectedItemHolder.setSelectedItem(item);
                }
            });

            inventoryGrid.add(no, 0, row);
            inventoryGrid.add(itemLabel, 1, row);
            inventoryGrid.add(description, 2, row);
            inventoryGrid.add(price, 3, row);
            inventoryGrid.add(selectButton, 4, row);

            row++;
        }
        for (ItemActions action : actions) {
            ObservableList<Node> buttons = buttonBox.getChildren();
            for (Node button : buttons) {
                if (button instanceof Button && ((Button) button).getText().equalsIgnoreCase(action.toString())) {
                    button.setManaged(true);
                }
            }
        }
    }

//    public static class ButtonConfig {
//        private final String label;
//        private final Consumer<Item> action;
//
//        public ButtonConfig(String label, Consumer<Item> action) {
//            this.label = label;
//            this.action = action;
//        }
//
//        public String getLabel() {
//            return label;
//        }
//
//        public Consumer<Item> getAction() {
//            return action;
//        }
//    }

    public interface ItemHolder {
        void setSelectedItem(Item item);

        Item getSelectedItem();
    }
}
