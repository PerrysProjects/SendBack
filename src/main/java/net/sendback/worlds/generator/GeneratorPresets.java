package net.sendback.worlds.generator;

import net.sendback.objects.ids.TileIDs;

public enum GeneratorPresets {
    FOREST(new TileIDs[]{TileIDs.GRASS, TileIDs.STONE},
            new TileIDs[]{TileIDs.TREE},
            new TileIDs[]{},
            TileIDs.TREE,
            new PerlinGenerator(),
            20),
    LAB(new TileIDs[]{TileIDs.FLOOR_LEFT, TileIDs.FLOOR_RIGHT},
            new TileIDs[]{TileIDs.GRAYWALL, TileIDs.WALL_WALL, TileIDs.LAB_WALL, TileIDs.REDBUTTON_UP, TileIDs.REDBUTTON_DOWN,
                    TileIDs.YELLOWBUTTON_UP, TileIDs.YELLOWBUTTON_DOWN, TileIDs.GREENBUTTON_UP, TileIDs.GREENBUTTON_DOWNL,
                    TileIDs.BLUEBUTTON_UP, TileIDs.BLUEBUTTON_DOWN, TileIDs.PURPLEBUTTON_UP, TileIDs.PRUPLEBUTTON_DOWN,
                    TileIDs.PIPEWALL_UP_1, TileIDs.PIPEWALL_DOWN_1, TileIDs.PIPEWALL_UP_2, TileIDs.PIPEWALL_DOWN_2,
                    TileIDs.PIPEWALL_UP_3, TileIDs.PIPEWALL_DOWN_3, TileIDs.PIPEWALL_UP_4, TileIDs.PIPEWALL_DOWN_4,
                    TileIDs.PIPEWALL_UP_5, TileIDs.PIPEWALL_DOWN_5},
            new TileIDs[]{TileIDs.TIME_MACHINE},
            TileIDs.GRAYWALL,
            new LabGenerator(),
            0);

    private final TileIDs[] floorTileIDs;
    private final TileIDs[] worldTileIDs;
    private final TileIDs[] interactiveTileIDs;
    private final TileIDs borderTileID;

    private final Generator generator;
    private int density;

    GeneratorPresets(TileIDs[] floorTileIDs, TileIDs[] worldTileIDs, TileIDs[] interactiveTileIDs, TileIDs borderTileID, Generator generator, int density) {
        this.floorTileIDs = floorTileIDs;
        this.worldTileIDs = worldTileIDs;
        this.interactiveTileIDs = interactiveTileIDs;
        this.borderTileID = borderTileID;

        this.generator = generator;
        this.density = density;
        generator.setUpGenerator(floorTileIDs, worldTileIDs, interactiveTileIDs, borderTileID, density);
    }

    public TileIDs[] getFloorTileIDs() {
        return floorTileIDs;
    }

    public TileIDs[] getWorldTileIDs() {
        return worldTileIDs;
    }

    public TileIDs[] getInteractiveTileIDs() {
        return interactiveTileIDs;
    }

    public TileIDs getBorderTileID() {
        return borderTileID;
    }

    public Generator getGenerator() {
        return generator;
    }

    public int getDensity() {
        return density;
    }
}
