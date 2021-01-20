package sample;

import javafx.scene.layout.Pane;

public abstract class element extends obj{

    public abstract boolean intersects(User user);
    public abstract void handleCollision(User user, Pane pane);
}
