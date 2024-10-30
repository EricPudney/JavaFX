package characters;

import java.util.Scanner;


public class Hero extends Character {
    public double evasion;
    final Race race;
    final Type type;
    //public Location currentLocation;
    public int gold = 0;
    private int xp = 0;
    public int level = 1;
    public boolean foundTreasure = false;
    //public Inventory inventory;
    //private final ArrayList<Equippable> equippedItems;

    public static Scanner scanner = new Scanner(System.in);

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
        //this.inventory = new Inventory(8);
        //this.equippedItems = new ArrayList<>();
    }

    public void addXp(int xp) {
        this.xp += xp;
        if (this.xp / 20 >= level) {
            //this.levelUp();
        }
    }

    @Override
    public String toString() {
        return "You are " + this.name + " the brave " + this.race + " " + this.type + "!\n" + "Attack: " + this.attack + "; Health: " + this.health + "\nYou have " + this.gold + " gold coins.\nYou are level " + this.level + " and have " + this.xp + " experience points.\n You have the following items equipped: "/* + equippedItems*/;
    }
}
