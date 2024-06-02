package edu.thomas.mvc;

import java.util.Observable;
import android.graphics.Bitmap;

public class IncidentModel extends Observable implements IModel {
    Bitmap bitmap;

    public IncidentModel() {
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        setChanged();
        notifyObservers();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
