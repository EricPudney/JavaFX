package characters;

import items.Equippable;
import items.Inventory;

import java.util.ArrayList;

public class Hero extends Character {
    public double evasion;
    final Race race;
    final Type type;
    //public Location currentLocation;
    public int gold = 0;
    private int xp = 0;
    public int level = 1;
    public boolean foundTreasure = false;
    public Inventory inventory;
    public final ArrayList<Equippable> equippedItems;

    public Hero(int attack, int health, int shield, double initiative, double dodge, double block, double evasion, Type type, Race race, String name) {
        this.attack = attack;
        this.health = health;
        this.shield = shield;
        this.initiative = initiative;
        this.dodge = dodge;
        this.block = block;
        this.maxHealth = health;
        this.evasion = evasion;
        this.type = type;
        this.race = race;
        this.name = name;
        this.inventory = new Inventory(8);
        this.equippedItems = new ArrayList<>();
    }

    public void addXp(int xp) {
        this.xp += xp;
        if (this.xp / 20 >= level) {
            this.levelUp();
        }
    }

    public void levelUp() {
        this.level += 1;
        this.maxHealth += 1;
        this.health += 1;
        if (this.level % 5 == 0) {
            this.attack += 1;
            this.evasion += 0.05;
            this.block += 0.05;
            this.dodge += 0.05;
        }
        // replace with alert?
        System.out.printf("%s levelled up and is now level %d!\n", this.name, this.level);
    }

    @Override
    public String toString() {
        return "You are " + this.name + " the brave " + this.race + " " + this.type + "!\n" + "Attack: " + this.attack + "; Health: " + this.health + "\nYou have " + this.gold + " gold coins.\nYou are level " + this.level + " and have " + this.xp + " experience points.\n You have the following items equipped: \n" + equippedItems + "\nYou are also carrying: \n" + inventory.toString();
    }
}
