package net.throwback.objects;

import net.throwback.objects.objectId.ObjectId;

public class InteractiveObject extends WorldObject {
    private final boolean interactable;
    private final int health;
    private final String message;

    public InteractiveObject(int x, int y, ObjectId id, boolean interactable, int health, String message) {
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