package areas;

import items.HealingPotion;
import items.Inventory;
import items.Item;

public class Shop {
    int gold;
    Inventory stock;

    public Shop(int gold) {
        this.gold = gold;
        this.stock = new Inventory();
        generateStock();
        // no max size for shop inventory
    }

    private void generateStock() {
        stock.add(Item.generateItem());
        stock.add(Item.generateMagicItem());
        stock.add(new HealingPotion());
        // bags not yet implemented
        // stock.add(new Bag(5));
    }
}
