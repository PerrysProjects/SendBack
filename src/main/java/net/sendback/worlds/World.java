package net.sendback.worlds;

import net.sendback.objects.FloorTile;
import net.sendback.objects.InteractiveTile;
import net.sendback.objects.WorldTile;
import net.sendback.objects.entity.Player;
import net.sendback.util.Session;
import net.sendback.worlds.generator.Generator;
import net.sendback.worlds.generator.GeneratorPresets;

public class World {
    private final String name;

    private final int width;
    private final int height;

    private final int seed;
    private final Generator generator;

    private final FloorTile[][] floorTileGrid;
    private final WorldTile[][] worldTileGrid;
    private final InteractiveTile[][] interactiveTileGrid;
    private final WorldTile borderTile;

    private final Session session;
    private final Player player;

    public World(String name, int width, int height, int seed, GeneratorPresets preset, Session session) {
        this.name = name;

        this.width = width;
        this.height = height;

        this.seed = seed;
        this.generator = preset.getGenerator();
        generator.setUpWorld(width, height, seed);

        floorTileGrid = new FloorTile[width][height];
        worldTileGrid = new WorldTile[width][height];
        interactiveTileGrid = new InteractiveTile[width][width];
        this.borderTile = generator.getBorderTile();

        this.session = session;
        player = session.getPlayer();

        generate();
    }

    public void generate() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                floorTileGrid[x][y] = generator.getFloorTile(x, y);
                worldTileGrid[x][y] = generator.getWorldTile(x, y);
                interactiveTileGrid[x][y] = generator.getInteractiveTile(x, y);
            }
        }
    }

    public void update() {
        if(player.isInteracting()) {
            if(interactiveTileGrid[(int) player.getX()][(int) (player.getY() - 1)] != null) {
                System.out.println("in");
                session.setCurrentWorld(session.getWorlds()[1]);
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public FloorTile[][] getFloorTileGrid() {
        return floorTileGrid;
    }

    public WorldTile[][] getWorldTileGrid() {
        return worldTileGrid;
    }

    public InteractiveTile[][] getInteractiveTileGrid() {
        return interactiveTileGrid;
    }

    public WorldTile getBorderTile() {
        return borderTile;
    }

    public int getSeed() {
        return seed;
    }
}
