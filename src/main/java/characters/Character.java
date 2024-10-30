package characters;

import areas.Location;

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
    public Location currentLocation;

    /*public void attack(Character target) {
        int damageAdjust = Main.rng.nextInt(-1, 2);
        int damageReduction = 0;
        double dodgeChance = Main.rng.nextDouble();
        double blockChance = Main.rng.nextDouble();
        if (dodgeChance < target.dodge) {
            System.out.printf("%s dodged the attack of %s!\n", target.name, this.name);
            return;
        }
        if (blockChance < target.block) {
            System.out.printf("%s blocked the attack of %s, reducing its damage by %d!\n", target.name, this.name, target.shield);
            damageReduction += target.shield;
        }
        int damage = this.attack + damageAdjust - damageReduction;
        if (damage < 1) {
            damage = 1;
        }
        System.out.printf("%s attacks %s, doing %d damage!\n", this.name, target.name, damage);
        target.health -= damage;
        if (target.health <= 0) {
            target.isAlive = false;
            System.out.printf("%s killed %s!\n", this.name, target.name);
        }
    }*/

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