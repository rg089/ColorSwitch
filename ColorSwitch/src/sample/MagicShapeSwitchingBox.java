package sample;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

import java.nio.file.Paths;
import java.util.Random;


public class MagicShapeSwitchingBox extends element{
    private boolean present = true;
    private transient ImageView im;

    public MagicShapeSwitchingBox(double x1, double y1) {
        x = x1; y = y1- 35; }

    public void eraseMagicShapeSwitchingBox(Pane pane){
        pane.getChildren().removeAll(im); }

    @Override
    public void draw(Pane root){
        Image image = new Image("file:resources/images/shapeChange.png");
        im = new ImageView(image);
        im.setX(x-40); im.setY(y); im.setFitWidth(80);im.setFitHeight(75);
        im.setEffect(new Glow(0.6));
        DropShadow borderGlow= new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.GHOSTWHITE);
        borderGlow.setWidth(30);
        borderGlow.setHeight(30);
        im.setEffect(borderGlow);
        if (present) root.getChildren().add(im); }

    @Override
    public void move(double scroll) {
        y+=scroll; im.setY(y);}

    @Override
    public boolean intersects(User user) {
        double yBall = user.getBall().getY();
        if ((present) && (yBall - 10 >=y) && (yBall-10<=y+im.getFitHeight())){
            present = false;
            return true; }
        return false; }

    private void playSound(String name, double volume, int priority){
        AudioClip sound = new AudioClip(Paths.get("resources/sounds/"+name+".wav").toUri().toString());
        sound.setVolume(volume); sound.setPriority(priority);
        sound.play(); }

    private int getType(User user){
        int type = new Random().nextInt(3);
        while (user.getBall().getType()==type){
            type = new Random().nextInt(3); }
        return type; }

    @Override
    public void handleCollision(User user, Pane pane) {
        boolean hit = intersects(user);
        if (hit){
            playSound("colorswitch", 0.25, 1);
            user.hitColorBox();
            eraseMagicShapeSwitchingBox(pane);
            int type = getType(user);
            user.getBall().setType(type);
            user.getBall().draw(pane); } }
}