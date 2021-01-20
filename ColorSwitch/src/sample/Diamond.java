package sample;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.effect.Glow;

import java.nio.file.Paths;


public class Diamond extends Obstacles {
    private transient Rectangle d;
    private double length, radius, Y, timeSinceChange, X;
    private boolean present;

    public Diamond(double side, double radius, double Y, double speed, double X) {
        length = side; this.speed = speed; this.radius = radius; this.Y = Y; this.X=X;
        this.x = X-length/2; this.y = Y-radius-side/2;
        d= new Rectangle(x,y, side, side);
        d.setFill(Color.DEEPPINK);
        d.setEffect(new Glow(1));
        d.setRotate(45);
        present = true; }

    public void setPresent(boolean present) {
        this.present = present; }

    public void glow(){
        d.setEffect(new Glow(1)); }


    public Rectangle getD() {
        return d; }

    @Override
    public boolean intersects(User user) {
        Shape intersect = Shape.intersect(user.getBall().getCircle(), d);
        return (intersect.getBoundsInLocal().getWidth() != -1) && (d.getOpacity() != 0);
    }

    @Override
    public void handleCollision(User user, Pane pane) {
        if (present && intersects(user)){
            playSound("diamond", 0.4, 1);
            present = false;
            user.hitDiamond();
            pane.getChildren().remove(d); }
 }

    private void playSound(String name, double volume, int priority){
        AudioClip sound = new AudioClip(Paths.get("resources/sounds/"+name+".wav").toUri().toString());
        sound.setVolume(volume); sound.setPriority(priority);
        sound.play(); }

    @Override
    public void rotation(double t) {
        timeSinceChange += t;
        angle = (angle+speed*t)%360;
        if (timeSinceChange > 0.3){
            d.setOpacity(1-d.getOpacity());
            timeSinceChange = 0; }
        x = X + radius*Math.sin(Math.toRadians(angle)) - length/2;
        y = Y - radius*Math.cos(Math.toRadians(angle)) - length/2;
        d.setX(x); d.setY(y);
    }

    @Override
    public void move(double scroll) {
        Y+=scroll; y+=scroll; }

    @Override
    public void draw(Pane pane) {
        DropShadow borderGlow= new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.CYAN);
        borderGlow.setWidth(70);
        borderGlow.setHeight(70);
        d = new Rectangle(x,y,length,length);
        d.setFill(Color.LIGHTBLUE);
        d.setEffect(new Glow(1));
        d.setEffect(borderGlow);
        d.setRotate(45);
        if (present) pane.getChildren().add(d); }

    @Override
    public boolean hitObstacle(Ball ball, double t) {
        return false;
    }

    @Override
    public double rotationSpeed() {
        return 0;
    }



    @Override
    public double time() {
        return 0; }

    @Override
    public double place() {
        return 0; }

}

