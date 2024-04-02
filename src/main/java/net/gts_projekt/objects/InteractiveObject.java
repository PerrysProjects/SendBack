package net.gts_projekt.objects;

import net.gts_projekt.objects.objectId.ObjectId;

import java.awt.Graphics;

public class InteractiveObject extends GameObject {
    protected boolean isInteractable;
    protected String message;


    public InteractiveObject(int x, int y, int width, int height, ObjectId id, boolean isInteractable, ObjectId.Image image, String message) {
        super(x, y, width, height, id, image);
        this.isInteractable = isInteractable;
        this.message = message;
    }

    public boolean GetIsInteractable() {
        return isInteractable;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public void render(Graphics g) {

    }
}