package areas;

import items.Item;

import java.util.Random;

public class Dungeon {
    private final Location[][] locations;
    private final int height;
    private final int width;
    protected static Random rng = new Random();

    
    public Dungeon(int height, int width){
        this.width = width;
        this.height = height;
        locations = new Location[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boolean noWaySouth = i == height - 1;
                boolean noWayNorth = i == 0;
                boolean noWayEast = j == width - 1;
                boolean noWayWest = j == 0;
                locations[i][j] = new Location(noWaySouth, noWayNorth, noWayEast, noWayWest);
            }
        }
        locations[height - 1][width/2].isStart = true;

        // places the treasure somewhere in the dungeon
        int x = rng.nextInt(this.width);
        int y = rng.nextInt(this.height - 1);
        locations[y][x].items.add(Item.generateTreasure(width, height));
    }

    public Location[][] getLocations(){
        return locations;
    }

    public Location getLocation(int row, int col) {
        return locations[row][col];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}