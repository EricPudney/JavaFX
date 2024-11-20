package characters;

import java.util.Random;

public class Monster extends Character {

        private static final String[] adj = {"Hairy ", "Fearsome ", "Ugly ", "Ravenous ", "Woeful ", "Savage "};
        private static final String[] noun = {"Goblin", "Ogre", "Hobgoblin", "Spider", "Gnome", "Centaur"};

        private static final Random rng = new Random();

        public Monster(String name, int damage, int health, double initiative){
            this.name = name;
            this.attack = damage;
            this.health = health;
            this.initiative = initiative;
        }

        public String toString() {
            return this.name.concat(": attack " + this.attack + ", health " + this.health);
        }

        public static Monster generateMonster() {
            int adjIndex = rng.nextInt(adj.length - 1);
            int nounIndex = rng.nextInt(noun.length - 1);
            String name = adj[adjIndex].concat(noun[nounIndex]);
            int damage = rng.nextInt(5) + 1;
            int health = rng.nextInt(5) + 1;
            double initiative = rng.nextDouble(0.1, 0.45);
            return new Monster(name, damage, health, initiative);
        }
}
