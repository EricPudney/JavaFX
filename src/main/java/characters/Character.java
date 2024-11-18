package characters;

import java.util.Random;

public abstract class Character {
    public int attack;
    public int maxHealth;
    public int health;
    public int shield;
    public double initiative;
    public double dodge;
    public double block;
    public String name;
    public boolean isAlive = true;
    private final Random rng = new Random();

    public String attack(Character target) {
        String returnString = "";
        int damageAdjust = rng.nextInt(-1, 1);
        int damageReduction = 0;
        double dodgeChance = rng.nextDouble();
        double blockChance = rng.nextDouble();
        if (dodgeChance < target.dodge) {
            return String.format("%s dodged the attack of %s!\n", target.name, this.name);
        }
        if (blockChance < target.block) {
            returnString = returnString.concat(String.format("%s blocked the attack of %s, reducing its damage by %d!\n", target.name, this.name, target.shield));
            damageReduction += target.shield;
        }
        int damage = this.attack + damageAdjust - damageReduction;
        if (damage < 1) {
            damage = 1;
        }
        returnString = returnString.concat(String.format("%s attacks %s, doing %d damage!\n", this.name, target.name, damage));
        target.health -= damage;
        if (target.health <= 0) {
            target.isAlive = false;
            returnString = returnString.concat(String.format("%s killed %s!\n", this.name, target.name));
        }
        return returnString;
    }
    /*
    public void encounter(Minion enemy) {
        if (enemy.initiative > this.initiative) {
            System.out.printf("The %s is too fast for you, and attacks!\n" , enemy.name);
            enemy.attack(this);
        }
        System.out.printf("%s: attack %d, health %d. Fight or run?\n", enemy.name, enemy.attack, enemy.health);
        boolean evaded = false;

        while (!evaded && enemy.isAlive && this.isAlive) {
            Commands[] commands = {Commands.c, Commands.h, Commands.x, Commands.a};
            Actions action = Decision.makeDecision("What do you want to do? Press a to attack or x to try to escape.", commands);
            switch (action) {
                case characterInfo:
                    System.out.println(this);
                    break;
                case help:
                    Main.printHelpText();
                    break;
                case evade:
                    double rng = Math.random();
                    if (this.evasion > rng) {
                        evaded = true;
                        currentLocation.enemy = null;
                        this.addXp(2);
                        System.out.println("You have successfully evaded the monster.");
                    }
                    else {
                        System.out.printf("You failed to evade the %s!\n", enemy.name);
                        enemy.attack(this);
                    }
                    break;
                case attack:
                    this.attack(enemy);
                    if (enemy.isAlive) {
                        enemy.attack(this);
                    }
                    if (!enemy.isAlive) {
                        this.addXp(5);
                    }
                    break;
                case null, default:
                    break;
            }
        }
    }


    */

    public enum Race {
        human,
        dwarf,
        elf
    }

    public enum Type {
        warrior,
        ranger,
        mage
    }
}