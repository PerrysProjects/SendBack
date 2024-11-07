package net.sendback.worlds.generator;

import net.sendback.objects.ids.TileIDs;

public enum GeneratorPresets {
    FOREST(new TileIDs[]{TileIDs.GRASS, TileIDs.STONE},
            new TileIDs[]{TileIDs.TREE},
            TileIDs.TREE,
            new PerlinGenerator(),
            20);

    private final TileIDs[] floorTileIDs;
    private final TileIDs[] worldTileIDs;
    private final TileIDs borderTileID;

    private final Generator generator;
    private int density;

    GeneratorPresets(TileIDs[] floorTileIDs, TileIDs[] worldTileIDs, TileIDs borderTileID, Generator generator, int density) {
        this.floorTileIDs = floorTileIDs;
        this.worldTileIDs = worldTileIDs;
        this.borderTileID = borderTileID;

        this.generator = generator;
        this.density = density;
        generator.setUpGenerator(floorTileIDs, worldTileIDs, borderTileID, density);
    }

    public TileIDs[] getFloorTileIDs() {
        return floorTileIDs;
    }

    public TileIDs[] getWorldTileIDs() {
        return worldTileIDs;
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
