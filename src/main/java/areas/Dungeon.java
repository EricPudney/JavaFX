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
                locations[i][j].setxPosition(i);
                locations[i][j].setyPosition(j);
            }
        }
        locations[height - 1][width/2].isStart = true;

        // places the treasure somewhere in the dungeon
        int x = rng.nextInt(this.width);
        int y = rng.nextInt(this.height - 1);
        locations[y][x].items.add(Item.generateTreasure(width, height));
    }

    public boolean isAdjacent(Location loc1, Location loc2){
        int[] coords1 = findCoordinates(loc1);
        int[] coords2 = findCoordinates(loc2);
        int coords1x = coords1[0];
        int coords1y = coords1[1];
        int coords2x = coords2[0];
        int coords2y = coords2[1];
        return (coords1x == coords2x && (coords1y == coords2y - 1 || coords1y == coords2y + 1)) ||
                (coords1y == coords2y && (coords1x == coords2x - 1 || coords1x == coords2x + 1));
    }

    public Location[][] getLocations(){
        return locations;
    }

    public Location getLocation(int row, int col) {
        return locations[row][col];
    }

    public int[] findCoordinates(Location loc) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (locations[i][j] == loc) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}