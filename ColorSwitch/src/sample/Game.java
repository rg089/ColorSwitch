package sample;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Game implements Serializable {
    private User user;
    private Ball ball;
    private int HEIGHT, WIDTH;
    private double currentScroll, totalScroll;
    private obstacleArray obstArray;
    private boolean gameOver = false;
    private ArrayList<element> boxes = new ArrayList<>();
    private double handY = 660;
    private boolean cheat;
    public Game(String name, int HEIGHT, int WIDTH, int dist){
        this.HEIGHT = HEIGHT; this.WIDTH = WIDTH;
        user = new User(name, HEIGHT, dist);
        obstArray = new obstacleArray(this);
        currentScroll = 0; totalScroll= 0;
        ball = user.getBall();
        this.cheat = false;
        }

    public obstacleArray getObstArray(){
        return obstArray; }

    public ArrayList<element> getBoxes(){
        return boxes; }

    public void draw(Pane root){
        obstArray.getObstArray().forEach(e -> e.draw(root));
        boxes.forEach(e -> e.draw(root));
        ball.draw(root);
    }

    public void createObstacles(Pane root, ArrayList<obj> objs){
        obstArray.addObstacle(this, true, root, objs); }

    public void scrollHand(double scroll){
        handY += scroll; }

    public double getHandY() {
        return handY; }

    public User getUser() {
        return user; }

    public void Loose(){

    }
    public void GameOver(){
        gameOver = true;

    }
    public void menuPause(){

    }

    public boolean getCheat() {
        return cheat;
    }

    public void setCheat(boolean cheat) {
        this.cheat = cheat;
    }

    public void addMCB(MagicColourBox box, Pane root){
        boxes.add(box);
        box.draw(root); }
    public void addMSSB(MagicShapeSwitchingBox box, Pane root){
        boxes.add(box);
        box.draw(root); }

    public ArrayList<obj> getObjects() {
        ArrayList<obj> objs = new ArrayList<>();
        objs.addAll(obstArray.getObstArray());
        objs.addAll(boxes);
        return objs; }

    private void checkGameover(){

    }

}