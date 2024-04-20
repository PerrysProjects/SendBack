package net.throwback.objects;

import net.throwback.objects.objectId.ObjectId;

public class InteractiveObject extends WorldObject {
    private boolean isInteractable;
    private int health;
    private String message;

    public InteractiveObject(int x, int y, ObjectId id, boolean isCollidable, boolean isInteractable, String message) {
        super(x, y, id, isCollidable);
        this.isInteractable = isInteractable;
        this.message = message;
    }

    public boolean GetIsInteractable() {
        return isInteractable;
    }

    public String getMessage() {
        return message;
    }
}