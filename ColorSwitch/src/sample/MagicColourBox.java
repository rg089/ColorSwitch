package sample;

import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

import java.nio.file.Paths;
import java.util.Random;

public class MagicColourBox extends element{
    private double radius;
    private transient Arc[] arcs;
    private boolean present = true;

    public MagicColourBox(double x1, double y1) {
        x = x1; y = y1; radius = 20; }

    public void eraseMagicColourBox(Pane pane){
        pane.getChildren().removeAll(arcs); }

    @Override
    public void draw(Pane rootJeu){
        Color[] colors = new Color[]{Color.AQUAMARINE, Color.ORANGERED, Color.INDIGO, Color.YELLOW};
        arcs = new Arc[4];
        for (int i=0; i<4; i++){
            arcs[i] = new Arc(x, y, radius, radius, 90*i, 90);
            arcs[i].setStroke(colors[i]);
            arcs[i].setFill(colors[i]);
            arcs[i].setType(ArcType.ROUND); }
        if (present) rootJeu.getChildren().addAll(arcs); }


    private int randomColour(){
        return new Random().nextInt(4);
    }

    public void colourSwitch(Ball ball){
        int newcolor = randomColour();
        while (newcolor == ball.getColor()) newcolor = randomColour();
        ball.setColor(newcolor); }


    @Override
    public void move(double scroll) {
        y+=scroll;
        for (Arc arc: arcs){
            arc.setCenterY(arc.getCenterY()+scroll); } }

    @Override
    public boolean intersects(User user) {
        double yBall = user.getBall().getY();
        if ((present) && (yBall>=y-radius) && (yBall-10<=y+radius)){
            present = false;
            return true; }
        return false; }

    private void playSound(String name, double volume, int priority){
        AudioClip sound = new AudioClip(Paths.get("resources/sounds/"+name+".wav").toUri().toString());
        sound.setVolume(volume); sound.setPriority(priority);
        sound.play(); }

    @Override
    public void handleCollision(User user, Pane pane) {
        boolean hit = intersects(user);
        if (hit){
            playSound("colorswitch", 0.25, 1);
            user.hitColorBox();
            eraseMagicColourBox(pane);
            colourSwitch(user.getBall()); } }
}