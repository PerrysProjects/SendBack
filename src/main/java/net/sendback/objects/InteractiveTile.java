package net.sendback.objects;

import net.sendback.objects.ids.TileIDs;

public class InteractiveTile extends WorldTile {
    private final boolean interactable;
    private final int health;
    private final String message;

    public InteractiveTile(int x, int y, TileIDs id, boolean interactable, int health, String message) {
        super(x, y, id);
        this.interactable = interactable;
        this.health = health;
        this.message = message;
    }

    public boolean isInteractable() {
        return interactable;
    }

    public int getHealth() {
        return health;
    }

    public String getMessage() {
        return message;
    }
}