package net.sendback.objects;

import net.sendback.objects.ids.TileIDs;

public class InteractiveTile extends WorldTile {
    private boolean interacting;
    private boolean interactable;
    private int health;

    private final String message;

    public InteractiveTile(int x, int y, TileIDs id, boolean interactable, int health, String message) {
        super(x, y, id);
        this.interactable = interactable;
        this.health = health;
        this.message = message;
    }

    public InteractiveTile(int x, int y, TileIDs id) {
        super(x, y, id);
        this.interactable = true;
        this.health = 1;
        this.message = "";
    }

    public void interact() {
        interacting = !interacting;
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