package areas;

import characters.Monster;
import items.Inventory;
import items.Item;

import static areas.Dungeon.rng;

public class Location {
    private Monster enemy;
    public Inventory items;
    private final LocationType locationType;
    private final String imagePath;
    //String description;
    public boolean isStart;
    public boolean explored;
    public boolean noWayNorth;
    public boolean noWaySouth;
    public boolean noWayEast;
    public boolean noWayWest;


//    static String[] texts = {"You stand in a dim and murky room. Green stuff oozes from the decaying bricks in the wall. ",
//    "By pale candlelight you make out the oblong shape of the dank and musty room. ",
//    "You are in a large chamber, its splendour now entirely faded. Pale gunk drips from the ceiling. ",
//    "You find yourself in a room that seems more like a cave and reeks of old cheese. ",
//    "A pale red light suffuses this chamber, which is entirely devoid of furniture. ",
//    "Ivy covers the walls of this room. ",
//    "An eerie whistling in the air turns your blood cold. ",
//    "The dungeon seems even darker and gloomier here, and somewhere in the distance you hear an eerie cackling. "};

    public Location(boolean noWaySouth, boolean noWayNorth, boolean noWayEast, boolean noWayWest) {
        this.items = new Inventory();
        this.explored = false;
        this.noWayNorth = noWayNorth;
        this.noWaySouth = noWaySouth;
        this.noWayEast = noWayEast;
        this.noWayWest = noWayWest;
        int locType = rng.nextInt(1, 3);
        this.locationType = switch (locType) {
            case 1 -> LocationType.corridor;
            case 2 -> LocationType.cave;
            default -> LocationType.room;
        };
        int locIndex = rng.nextInt(1, 4);
        this.imagePath = "/images/rooms/" + locationType + locIndex + ".png";

        double prob = rng.nextDouble();
        if (prob < 0.01) {
            enemy = Monster.generateMonster();
        }
        else if (prob > 0.01) {
          items.add(Item.generateItem());
        }

    }

//    public String describeLocation() {
//        String text = "";
//        if (enemy != null && !enemy.isAlive) {
//            text = String.format("There is a dead %s here.\n", enemy.name);
//        }
//        if (enemy != null && enemy.isAlive) {
//            text = "You have encountered a monster! " + enemy + "\n";
//            return description + text;
//        }
//        else if (!items.isEmpty()) {
//            text = "You have found the following items: \n" + items + "\n";
//        }
//        if (noWaySouth) {
//            text = text.concat("There is no way south from here.\n");
//        }
//        if (noWayNorth) {
//            text = text.concat("There is no way north from here.\n");
//        }
//        if (noWayEast) {
//            text = text.concat("There is no way east from here.\n");
//        }
//        if (noWayWest) {
//            text = text.concat("There is no way west from here.\n");
//        }
//        return description + text;
//    }

    public String getImagePath() {
        return imagePath;
    }

    public String getLocationType() {
        return locationType.toString();
    }

    public Monster getEnemy() {
        return enemy;
    }

    public String generateMonsterDescription() {
        if (enemy != null) {
            return "In the " + locationType + " stands a " + enemy;
        }
        return "The area seems to be uninhabited.";
    }

    public String generateItemText() {
        if (items.isEmpty()) {
            return "You see nothing else of interest here.";
        }
        return "You have found the following item(s): \n" + items;
    }

    enum LocationType {
        cave,
        room,
        corridor
    }
}