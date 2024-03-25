package net.gts_projekt.objects;

import net.gts_projekt.ObjectId.ObjectId;
import java.awt.Graphics;
import java.util.LinkedList;

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

    @Override
    public void tick(LinkedList<GameObject> object) {

    }


    @Override
    public void render(Graphics g) {

    }
}