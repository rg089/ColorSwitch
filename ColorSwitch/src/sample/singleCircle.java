package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.HashMap;


public class singleCircle extends Obstacles{

    private transient Arc[] arcs;
    private double r1, r2;

    public singleCircle(double xCenter, double yTop, double yBottom, double stroke, double speed, boolean hasStar, boolean rotationClockwise){
        this.yTop = yTop; this.yBottom = yBottom; x = xCenter; y = (yTop+yBottom)/2;
        r1 = yBottom-y; r2 = stroke;
        this.speed = speed;
        star = new Star(computeStar());
        clockwiseRotation = rotationClockwise;
        this.hasStar = hasStar; }

    public double getSpeed() {
        return speed; }

    public void setSpeed(double speed) {
        this.speed = speed; }

    public Star getStar() {
        return star; }



    @Override
    public double rotationSpeed() {
        return 0; }


    @Override
    public double time() {
        return 0; }

    @Override
    public double place() {
        return 0; }

    @Override
    public void move(double scroll) {
        y+=scroll; yTop+=scroll; yBottom+=scroll;
        if (hasStar) star.move(scroll);
        for (Arc arc: arcs){
            arc.setCenterY(arc.getCenterY()+scroll); } }


    @Override
    public void draw(Pane rootJeu)  {
        Color[] colors = new Color[]{Color.AQUAMARINE, Color.ORANGERED, Color.INDIGO, Color.YELLOW};
        if (!clockwiseRotation) colors = new Color[]{ Color.ORANGERED, Color.AQUAMARINE, Color.YELLOW,Color.INDIGO};
        arcs = new Arc[4];
        for (int i=0; i<4; i++){
            arcs[i] = new Arc(x, y, r1, r1, 360-angle-90*i, 90);
            arcs[i].setStroke(colors[i]);
            arcs[i].setFill(Color.TRANSPARENT);
            arcs[i].setStrokeWidth(r2);
            arcs[i].setType(ArcType.OPEN); }
        if (!clockwiseRotation){
            for (int i=0; i<4; i++){
                arcs[i].setStartAngle(angle+(90*((4-i)%4))); } }
        star.setPoints(computeStar());
        rootJeu.getChildren().addAll(arcs);
        if (hasStar) star.draw(rootJeu);}


    public void rotation(double timediff){
        angle = (angle+speed*timediff)%360;
        for (int i=0; i<4; i++){
            if (clockwiseRotation) arcs[i].setStartAngle(arcs[i].getStartAngle()-speed*timediff);
            else arcs[i].setStartAngle(arcs[i].getStartAngle()+speed*timediff);
        } }

    @Override
    public boolean hitObstacle(Ball ball, double t) {
        HashMap<Integer, Integer> bottomFromTop = new HashMap<>();
        bottomFromTop.put(0,2); bottomFromTop.put(1,3); bottomFromTop.put(2,0); bottomFromTop.put(3,1);
        double yball = ball.getY();
        int rotated = (int)(angle%360);
        int topColor = 3 - rotated/90;
        int bottomColor = bottomFromTop.get(topColor);
        if (!clockwiseRotation) {topColor = ((4-rotated/90)%4+ 1) %4; bottomColor = bottomFromTop.get(topColor);}
        if ((yball-10>=y+r1) && (yball-10<=y+r1+r2)){
            return ball.getColor() != bottomColor; }
        else if ((yball+10>=y+r1) && (yball+10<=y+r1+r2)){
            return ball.getColor() != bottomColor; }
        else if ((yball-10<=y-r1) && (yball-10>=y-r1-r2)){
            return ball.getColor() != topColor; }
        else if ((yball+10<=y-r1) && (yball+10>=y-r1-r2)){
            return ball.getColor() != topColor; }
        return false;
    }
}
