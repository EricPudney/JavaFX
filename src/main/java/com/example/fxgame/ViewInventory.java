package com.example.fxgame;

import areas.Location;
import characters.Hero;
import com.example.fxgame.components.InventoryView;
import com.example.fxgame.components.ItemActions;
import items.Equippable;
import items.Inventory;
import items.Item;
import items.Usable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewInventory implements AppAwareController, InventoryView.ItemHolder {
    @FXML
    private GridPane inventoryGrid;

    @FXML
    private HBox buttonBox;

    @FXML
    private Text msgtxt;

    private RPGApplication app;

    private Inventory inventory;

    private Item selectedItem;

    private Location location;

    private InventoryView view = new InventoryView();

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setApp(RPGApplication app) {
        this.app = app;
    }


    public void renderInventory(Inventory inventory) {
        List<ItemActions> actions = new ArrayList<>();
        actions.add(ItemActions.DROP);
        actions.add(ItemActions.USE);
        actions.add(ItemActions.EQUIP);
        actions.add(ItemActions.BACK);
        view.renderInventory(inventoryGrid, buttonBox, inventory, this, actions);
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
        app.enterRoom(location);
    }

    @Override
    public void setSelectedItem(Item item) {
        this.selectedItem = item;
    }

    @Override
    public Item getSelectedItem() {
        return selectedItem;
    }

    public void sellItem(ActionEvent actionEvent) {
        msgtxt.setText("Item sold!");
    }
}
