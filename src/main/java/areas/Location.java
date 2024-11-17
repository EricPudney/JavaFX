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
    private int xPosition;
    private int yPosition;
    public boolean isStart;
    public boolean explored;
    public boolean noWayNorth;
    public boolean noWaySouth;
    public boolean noWayEast;
    public boolean noWayWest;

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
        if (prob < 0.88) {
            enemy = Monster.generateMonster();
        }
        else if (prob > 0.99) {
          items.add(Item.generateMagicItem());
        }
        else if (prob > 0.95) {
            items.add(Item.generateItem());
        }
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getLocationType() {
        return locationType.toString();
    }

    public Monster getEnemy() {
        return enemy;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public String generateMonsterDescription() {
        if (enemy != null) {
            return "In the " + locationType + " stands a " + enemy + "!";
        }
        return "The area seems to be uninhabited.";
    }

    public String generateItemText() {
        if (items.isEmpty()) {
            return "You see nothing else of interest here.";
        }
        return "You have found the following item(s): \n" + items;
    }

    public void setEnemyToNull() {
        this.enemy = null;
    }

    enum LocationType {
        cave,
        room,
        corridor
    }
}