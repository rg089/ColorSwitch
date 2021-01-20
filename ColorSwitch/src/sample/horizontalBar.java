package sample;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class horizontalBar extends Obstacles{
    private transient Rectangle[] rects;
    private double dist, WIDTH, starDist;

    public horizontalBar(double yTop, double WIDTH, double yBottom, double speed, double starDist, boolean hasStar){
        this.yTop = yTop; this.yBottom = yBottom; this.hasStar = hasStar;
        double thick = yBottom - starDist - 20 - yTop;
        x = WIDTH/2; this.y = yTop + 20; this.WIDTH = WIDTH;
        this.hasStar = hasStar;
        star = new Star(computeStar());
        this.speed = speed; //Distance travelled in a second
        this.starDist = starDist; this.thick = thick;}


    @Override
    public double rotationSpeed() {
        return 0;
    }

    @Override
    public double time() {
        return 0;
    }

    @Override
    public double place() {
        return 0;
    }

    public double getColour() {
        return colour; }

    @Override
    public void draw(Pane pane) {
        Color[] colors = new Color[]{Color.AQUAMARINE, Color.ORANGERED, Color.INDIGO, Color.YELLOW};
        rects = new Rectangle[8];
        for (int i=0; i<8; i++){
            rects[i] = new Rectangle(-WIDTH+(WIDTH/4)*i, yTop + 20 + starDist, WIDTH/4, thick);
            rects[i].setFill(colors[i%4]); }
        g = new Group(rects);
        star.setPoints(computeStar());
        pane.getChildren().add(g);
        if (hasStar) star.draw(pane);}


    @Override
    public void rotation(double t) {
        for (int i=0; i<8; i++){
            rects[i].setX(rects[i].getX()+speed*t);}
        if (rects[0].getX()>=0){
            for (int i=0; i<8; i++){
                rects[i].setX(-WIDTH+(WIDTH/4)*i);} }}

    @Override
    public boolean hitObstacle(Ball ball, double t) {
        Shape c = ball.getCircle();
        boolean collision = false;
        for (int i=0; i<8; i++){
            Shape intersect = Shape.intersect(rects[i], c);
            if ((intersect.getBoundsInLocal().getWidth() != -1) && ((i%4)!=ball.getColor())){
                collision = true; } }
        return collision;
    }


    public void setColour(double colour) {
        this.colour = colour;
    }


}
