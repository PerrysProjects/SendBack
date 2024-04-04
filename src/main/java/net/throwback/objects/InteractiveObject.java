package net.throwback.objects;

import net.throwback.objects.objectId.ObjectId;

public class InteractiveObject extends GameObject {
    protected boolean isInteractable;
    protected String message;

    public InteractiveObject(int x, int y, int width, int height, ObjectId id, boolean isInteractable, String message) {
        super(x, y, width, height, id);
        this.isInteractable = isInteractable;
        this.message = message;
    }

    public boolean GetIsInteractable() {
        return isInteractable;
    }

    public String getMessage() {
        return message;
    }

    /* @Override
    public void render(Graphics g) {

    }*/
}