package sample;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

public class Star extends element{
    transient Polygon star;
    private double[] points;
    private boolean present;

    public Star(double[] points) {
        star = new Polygon(points);
        this.points = points;
        present = true; }

    public void setPresent(boolean present) {
        this.present = present; }

    public void move(double x1, double y1){
        star.setTranslateX(x1); star.setTranslateY(y); }

    public void setPoints(double[] points) {
        this.points = points; }

    public Polygon get() {
        return star;
    }

    public void eraseStar(Pane rootJeu){
        rootJeu.getChildren().remove(star);
    }

    public void increaseScore(){

    }

    public boolean hitStar(){
        return true;
    }


    @Override
    public void move(double scroll) {
        star.setTranslateY(star.getTranslateY()+scroll);
    }

    @Override
    public void draw(Pane pane) {
        star = new Polygon(points);
        star.setFill(Color.WHITE);
        if (present) pane.getChildren().addAll(star);
    }

    public boolean present(){
        return present;

    }


    @Override
    public boolean intersects(User user) {
        return false;
    }

    @Override
    public void handleCollision(User user, Pane pane) {

    }
}
