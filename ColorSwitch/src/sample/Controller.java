package sample;


import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Controller {
    private Game game;
    public static Stage primaryStage;
    private final int WIDTH;
    private final int HEIGHT;
    private boolean newUser;

    public Controller() {
        WIDTH =500; HEIGHT = 700;
        int jump = 200;
        this.game = new Game("Unknown", HEIGHT, WIDTH, jump);
        try {
            File file = new File("saved/Unknown");
            if(file.exists()){
                for (File f : file.listFiles()){
                    if (!f.getName().equals("info.txt")) f.delete();
                    else {
                        f.delete();
                        f.createNewFile();} } }
            else file.mkdirs(); }
        catch (Exception e){
            System.out.println("Error in creating Unknown Folder"); } }

    private void playSound(String name, double volume, int priority){
        AudioClip sound = new AudioClip(Paths.get("resources/sounds/"+name+".wav").toUri().toString());
        sound.setVolume(volume); sound.setPriority(priority);
        sound.play(); }


    public void spaceTyped() {
        playSound("jump", 0.15, 0); }

    public void serialize(String name) throws IOException {
        File f = new File(name+".txt");
        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(game); }

    public void deserialize(String name) {
        try {
            FileInputStream fis = new FileInputStream(name + ".txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            game = (Game) ois.readObject(); }
        catch (Exception e) {
            e.printStackTrace(); } }

    private void load(String s){
        try {
            Pane root = FXMLLoader.load(getClass().getResource(s+".fxml"));
            primaryStage.setScene(new Scene(root)); }
        catch (IOException e) {
            System.out.println("FXML not found!"); } }

    private Font loadFont(int size){
        return Font.loadFont("file:resources/fonts/stencil.ttf", size); }

    private void getText(Pane root){
        Color[] colour = new Color[]{Color.RED, Color.YELLOWGREEN, Color.LIGHTSKYBLUE};
        Font font = loadFont(85);
        int[] pos = new int[] {15, 137, 253, 165, 215, 295, 325, 375, 430};
        String[] text = new String[] {"C", "L", "R", "S", "W", "I", "T", "C", "H"};
        Text[] texts = new Text[9];
        for (int i=0; i<9; i++){
            if (i<3) {
                texts[i] = new Text(pos[i], 90, text[i]); texts[i].setFont(font); texts[i].setFill(Color.WHITE); }
            else{
                texts[i] = new Text(pos[i], 185, text[i]); texts[i].setFont(font);
                if (i%2!=0) texts[i].setFill(Color.WHITE);
                else texts[i].setFill(colour[(i-4)/2]); } }
        root.getChildren().addAll(texts); }

    private ArrayList<String> getSavedNames(){
        File f = new File("saved");
        ArrayList<String> arr = new ArrayList<>();
        for (File e : Objects.requireNonNull(f.listFiles())){
            if (e.isDirectory()) arr.add(e.getName()); }
        return  arr; }

    private void drawInfinity(Pane root){
        SVGPath svg = new SVGPath();
        svg.setFill(Color.TRANSPARENT);
        svg.setContent("M 787.49,150 C 787.49,203.36 755.56,247.27 712.27,269.5 S 622.17,290.34 582.67,279.16 508.78,246.56 480,223.91 424.93,174.93 400,150 348.85,98.79 320,76.09 256.91,32.03 217.33,20.84 130.62,8.48 87.73,30.5 12.51,96.64 12.51,150 44.44,247.27 87.73,269.5 177.83,290.34 217.33,279.16 291.22,246.56 320,223.91 375.07,174.93 400,150 451.15,98.79 480,76.09 543.09,32.03 582.67,20.84 669.38,8.48 712.27,30.5 787.49,96.64 787.49,150 z");
        double originalWidth = svg.prefWidth(-1);
        double originalHeight = svg.prefHeight(originalWidth);
        double scaleX = 110 / originalWidth;
        double scaleY = 60 / originalHeight;
        svg.setScaleX(scaleX); svg.setScaleY(scaleY);
        svg.setStrokeWidth(80.0); svg.setStroke(Color.WHITE);
        svg.setLayoutX(-150); svg.setLayoutY(255);
        root.getChildren().add(svg); }


    private void addButtons(Pane root) {
        String[] bt = new String[]{"newGame", "ResumeGame", "help", "Exit"};
        Button button1= new Button(); button1.setMinSize(110,85);
        Button button2= new Button(); button2.setMinSize(80,75);
        Button button3= new Button(); button3.setMinSize(80,85);
        Button button4= new Button(); button4.setMinSize(80,80);
        Button button5= new Button(); button5.setMinSize(80,80);
        Button[] buttons = new Button[] {button1, button2, button3, button4, button5};
        for (int i=0; i<5; i++){
            buttons[i].setOpacity(0);
            int finalI = i;
            if (i==0) buttons[i].setOnAction((event) -> {playSound("button", 0.2, 0); newGame();});
            else if(i == 1) buttons[i].setOnAction((event) -> {playSound("button", 0.2, 0); displayResumeGame();});
            else if(i == 4) buttons[i].setOnAction((event) -> {playSound("button", 0.2, 0); loginPage(root);});
            else if (i!=3) buttons[i].setOnAction((event) -> {playSound("button", 0.2, 0); load(bt[finalI]);});
            else buttons[i].setOnAction((event) -> primaryStage.close()) ;}
        button1.setLayoutX(190);button1.setLayoutY(360);
        button2.setLayoutX(160);button2.setLayoutY(605);
        button3.setLayoutX(280); button3.setLayoutY(595);
        button4.setLayoutX(400);button4.setLayoutY(600);
        button5.setLayoutX(25);button5.setLayoutY(605);
        root.getChildren().addAll(buttons); }

    private ImageView addImage(Pane root, String name, int x, int y, int width, int height){
        Image image = new Image("file:resources/images/"+name);
        ImageView im = new ImageView(image);
        im.setX(x); im.setY(y); im.setFitWidth(width);im.setFitHeight(height);
        root.getChildren().add(im);
        return im;}

    private Pane loadPane() {
        Pane root = new Pane();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY, Insets.EMPTY)));
        return root; }

    private void display(Pane root) {
        Scene sc = new Scene(root, 500,700);
        primaryStage.setScene(sc);
        primaryStage.show(); }

    private void loginPage(Pane root){
        Pane pane = loadPane();
        root.setOpacity(0.3);
        BorderPane borderPane = new BorderPane();
        borderPane.setLayoutX(50);borderPane.setLayoutY(315);
        borderPane.setMinSize(400,180);
        borderPane.setStyle("-fx-background-color: black ");
        Text text1 = new Text(80, 470, "Enter Username"); Font font1 = loadFont(30); text1.setFont(font1); text1.setFill(Color.WHITE);
        addImage(pane, "cancel.png", 444, 281, 30, 28);
        Button button1= new Button(); button1.setMinSize(30,28);
        button1.setLayoutX(444);button1.setLayoutY(281);
        button1.setOnAction((event) -> {playSound("button", 0.2, 0); mainMenu();}); button1.setOpacity(0);
        TextField tf= new TextField();
        tf.setPromptText("Username");
        tf.setLayoutX(83); tf.setLayoutY(350);tf.setMinSize(335,48);
        HashSet<String> hs = new HashSet<>(getSavedNames());
        TextFields.bindAutoCompletion(tf, hs);
        tf.setOnKeyPressed(event -> {
            if ((event.getCode()==KeyCode.ENTER) || (event.getCode()==KeyCode.BACK_SPACE)) {
                hs.add(tf.getText());
                TextFields.bindAutoCompletion(tf, hs); } });
        Button button2= new Button(); button2.setMinSize(63,68);
        button2.setLayoutX(219);button2.setLayoutY(432);
        button2.setOnAction((event) -> {
            playSound("button", 0.2, 0);
            File file1 = new File("saved/"+tf.getText()+"/info.txt");
            if (tf.getText().length() != 0){
                if ((!tf.getText().equals(game.getUser().getName())) && (!file1.exists())){
                    newUser=true;}
                game.getUser().setName(tf.getText());
            File file = new File("saved/"+tf.getText());
            if(!file.exists()) file.mkdirs();
            else {
                if (file1.exists()){
                    ArrayList<String> arr = readFile("saved/"+tf.getText()+"/info.txt");
                    String s = arr.get(arr.size() - 1).split("\t\t\t")[0];
                    if (!s.equals("Game No.")){
                        int y= Integer.parseInt(s);
                        game.getUser().setSavedGames(y); }}}
            mainMenu();}});
        button2.setOpacity(0);
        pane.getChildren().addAll(root, borderPane, button1, tf);
        addImage(pane, "submit.png", 219, 432, 63, 68);
        pane.getChildren().add(button2);
        display(pane);}


    public void mainMenu() {
          Pane root = loadPane();
          primaryStage.setTitle("Colour Switch Game");
          getText(root);
          singleCircle c = new singleCircle(250, 255, 555, 16, 120, false, false);
          singleCircle c0 = new singleCircle(250, 280,530, 16, 100, false, true);
          singleCircle c1= new singleCircle(250, 305,505, 16, 80, false, false);
          singleCircle c2= new singleCircle(103, 30,95, 7, 150, false, false);
          singleCircle c3= new singleCircle(222, 30,95, 7, 150, false, true);
          Plus plus = new Plus(90, 120, 220, 10, 0, 120, false, true);
          Square sq = new Square(380, 30, 100, 7, false, 120, true);
          ArrayList<Obstacles> arr = new ArrayList<>(List.of(c, c0, c1, c2, c3, plus, sq));
          arr.forEach(el -> el.draw(root));
          drawInfinity(root);
          addImage(root, "qn.jpg", 250, 590, 140, 90);
          addImage(root, "res1.png", 120, 560, 160, 170);
          addImage(root,"back3.png", 370, 560, 140, 160);
          addImage(root,"login.png", 10, 610, 110, 110);
          addButtons(root);
          AnimationTimer timer = new AnimationTimer() {
              private long lastTime = System.nanoTime();
              private final long startTime = System.nanoTime();
              @Override
              public void handle(long currentTime) {
                  double t = (currentTime - lastTime) / 1000000000.0;
                  arr.forEach(el -> el.rotation(t));
                  lastTime = currentTime; }};
          timer.start();
          display(root);}


    public void displayExitMenu() {
        Pane root = loadPane();
        Font font = loadFont(60);
        Text text = new Text(30, 70, "Do you want to \nsave the game?");
        text.setFont(font); text.setFill(Color.WHITE);
        addImage(root, "save1.png", 175, 255, 140, 140);
        addImage(root,"cancel.png", 160, 485, 170, 170);
        Button b = new Button(); b.setLayoutX(175); b.setLayoutY(255); b.setMinWidth(140); b.setMinHeight(140);
        b.setOnAction(event -> {playSound("button", 0.2, 0); saveGame(false);}); b.setOpacity(0);
        Button b1 = new Button(); b1.setLayoutX(160); b1.setLayoutY(485); b1.setMinWidth(170); b1.setMinHeight(170);
        b1.setOnAction(event -> {playSound("button", 0.2, 0); mainMenu();}); b1.setOpacity(0);
        root.getChildren().addAll(text, b, b1);
        display(root); }

    private ArrayList<String> readFile(String s){
        ArrayList<String> arr = new ArrayList<>();
        arr.add("Game No.\t\t\tTime\t\t\tScore");
        try{
            FileReader reader = new FileReader(s);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                arr.add(line); }
            reader.close();}
        catch (Exception ignored){ }
        return arr; }


    public void displayResumeGame()  {
        Pane pane= loadPane();
        Text text1 = new Text(62, 70, "Resume Game"); Font font1 = loadFont(60); text1.setFont(font1); text1.setFill(Color.RED);
        Text text2 = new Text(88, 150, "Welcome "+game.getUser().getName()); Font font2 = loadFont(36); text2.setFont(font2); text2.setFill(Color.WHEAT);
        addImage(pane,"back3.png", 370, 560, 140, 160);
        Button button4= new Button(); button4.setMinSize(80,80);button4.setLayoutX(400);button4.setLayoutY(600);button4.setOpacity(0);
        button4.setOnAction((event)-> mainMenu());
        try {
            playSound("button", 0.2, 0);
            final String[] num = {""};
            ArrayList<String> arr= readFile("saved/"+game.getUser().getName()+"/info.txt");
            ListView<String> listView = new ListView<>(FXCollections.observableArrayList(arr));
            listView.setPrefSize(400, 250);listView.setLayoutX(50);listView.setLayoutY(300);
            listView.setOnMouseClicked((eve)-> {
                if((listView.getSelectionModel().getSelectedItem() != null) && (listView.getSelectionModel().getSelectedItem().charAt(0) != 'G')) {
                    num[0] =listView.getSelectionModel().getSelectedItem().split("\t\t\t")[0];
                deserialize("saved/"+game.getUser().getName()+"/"+ num[0]);
                String s = arr.get(arr.size()-1).split("\t\t\t")[0];
                game.getUser().setSavedGames(Integer.parseInt(s));
                enterGame(false, 0);}});
            pane.getChildren().addAll(listView,text1,text2,button4);
            Scene sc = new Scene(pane, 500,700);
            sc.getStylesheets().add("file:resources/styles/listStyle.css");
            primaryStage.setScene(sc);
            primaryStage.show(); }

        catch (Exception e){
            System.out.println("Click at right Position\n"); } }

    public void saveGame(boolean continued){
        try{
            game.getUser().incrementSaved();
            FileWriter writer = new FileWriter("saved/"+game.getUser().getName()+"/info.txt", true);
            writer.write(game.getUser().getSavedGames()+"\t\t\t"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm:ss"))+"\t\t\t"+ game.getUser().getScore()+"\n");
            writer.close();
            serialize("saved/"+game.getUser().getName()+"/"+game.getUser().getSavedGames());
        if(!continued) mainMenu();}
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in Serializing."); } }

    void displayPauseMenu(Scene scene, AnimationTimer timer){
        try {
            Pane root = FXMLLoader.load(getClass().getResource("pauseMenu.fxml"));
            Button b = new Button(); b.setOpacity(0);
            b.setLayoutX(180); b.setLayoutY(258); b.setMinWidth(145); b.setMinHeight(150);
            b.setOnAction(event -> {playSound("button", 0.2, 0); resumeGame(scene, timer);});
            Button b1 = new Button(); b1.setOpacity(0);
            b1.setLayoutX(180); b1.setLayoutY(500); b1.setMinWidth(140); b1.setMinHeight(130);
            b1.setOnAction(event -> {playSound("button", 0.2, 0); displayExitMenu();});
            root.getChildren().addAll(b, b1);
            primaryStage.setScene(new Scene(root)); }
        catch (IOException e) {
            System.out.println("FXML not found!"); } }

    void resumeGame(Scene scene, AnimationTimer timer){
        timer.start();
        primaryStage.setScene(scene); }

    private double[] cord(double x, double y, double sr1, double sr2){
        double[] points = new double[20];
        for (int i=0; i<20; i+=4){
            int t = i/4;
            points[i] = x - sr1*Math.cos(Math.toRadians(90+(72*t)));
            points[i+1] = y - sr1*Math.sin(Math.toRadians(90+(72*t)));
            points[i+2] = x -sr2*Math.cos(Math.toRadians(126+(72*t)));
            points[i+3] = y - sr2*Math.sin(Math.toRadians(126+(72*t))); }
        return points; }

    private void revive(Scene scene, Pane pane, double pos) {
        if(game.getUser().getScore() >= 5){
            playSound("revive", 0.2, 0);
            deserialize("saved/temp");
            game.getUser().setScore(game.getUser().getScore()-5);
            enterGame(true, pos); }
        else {
            playSound("error", 0.2, 0);
            Text text1 = new Text(80, 470, "Minimum 5 Stars Needed"); Font font1 = loadFont(30); text1.setFont(font1); text1.setFill(Color.WHITE);
            pane.getChildren().add(text1);
            new AnimationTimer() {
                private long lastTime = System.nanoTime();
                private long startTime = System.nanoTime();
                @Override
                public void handle(long currentTime) {
                    double t = (currentTime - startTime) / 1000000000.0;
                    if(t> 2) {pane.getChildren().remove(text1); stop();} }}.start(); } }

    void exit(Scene scene, double pos){
        try {
            playSound("victory", 0.4, 0);
            serialize("saved/temp");
            Pane root = FXMLLoader.load(getClass().getResource("GameOver.fxml"));
            ArrayList<ImageView> ImArr= new ArrayList<>();
            Text text;
            Text text1 = new Text(17, 90, "Game Over!!"); Font font1 = loadFont(80); text1.setFont(font1); text1.setFill(Color.RED);
            ImArr.addAll(List.of(addImage(root, "heart.png", 140,384,223,188 ), addImage(root, "star.png", 206,503,24,24 ), addImage(root, "star.png", 206,528,24,24 ), addImage(root, "star.png", 226,523,18,18 ), addImage(root, "star.png", 221,546,18,24 ),
                    addImage(root, "star.png", 244,542,12,11 ), addImage(root, "star.png", 246,558,11,11 ), addImage(root, "star.png", 261,526,18,18 ), addImage(root, "star.png", 278,510,24,24 ), addImage(root, "star.png", 289,535,33,24 )));
            Star st = new Star(cord(250, 250, 90, 50)); st.draw(root); st.get().setFill(Color.YELLOW);
            Button btn = new Button();btn.setMinSize(223,180);btn.setLayoutY(390);btn.setLayoutX(140);btn.setOpacity(0);
            btn.setOnAction((event) -> {revive(scene, root, pos);});
            if(game.getUser().getScore() > 9) {
                text = new Text(215, 270, String.valueOf(game.getUser().getScore())); Font font = loadFont(65); text.setFont(font); }
            else {
                text = new Text(230, 270, String.valueOf(game.getUser().getScore())); Font font = loadFont(65); text.setFont(font); }
            text.setFill(Color.INDIGO);
            Button btn1 = new Button();btn1.setMinSize(97,93);btn1.setLayoutY(576);btn1.setLayoutX(14);btn1.setOpacity(0);
            Button btn2 = new Button();btn2.setMinSize(86,86);btn2.setLayoutY(584);btn2.setLayoutX(395);btn2.setOpacity(0);
            btn1.setOnAction((event) -> {playSound("button", 0.2, 0);  mainMenu();});
            btn2.setOnAction((event) -> {playSound("button", 0.2, 0); newGame();});
            Arc arc = new Arc(250, 470, 125, 125, 90, 360); arc.setStroke(Color.LIGHTBLUE); arc.setFill(Color.TRANSPARENT); arc.setType(ArcType.OPEN);
            root.getChildren().addAll(text1, text,arc, btn, btn1, btn2);
            AnimationTimer timer1 = new AnimationTimer() {
                private long lastTime = System.nanoTime();
                private long startTime = System.nanoTime();
                @Override
                public void handle(long currentTime) {
                    double t = (currentTime - startTime) / 1000000000.0;
                    ImArr.forEach(el -> el.setOpacity(1- (0.2 * t)));
                    arc.setLength(360-72*t);
                    if(t> 5) {root.getChildren().removeAll(btn, arc); stop();} }};
            timer1.start();
            primaryStage.setScene(new Scene(root)); }
        catch (IOException e) {
            System.out.println("FXML not found!"); } }

    private void ballBurst(Circle[] circles, int[] angle, double t, double[] speed){
        double radius = circles[0].getRadius();
        for (int i = 0; i<circles.length; i++){
            double xCenter = circles[i].getCenterX(); double yCenter = circles[i].getCenterY();
            if ((xCenter>= 500-radius) || (xCenter<=radius)){
                speed[i] = 170 + new Random().nextInt(200);
                angle[i] = 180 - angle[i]; }
            if ((yCenter>=700-radius) || (yCenter<=radius)){
                angle[i] = 360 - angle[i]; }
            circles[i].setCenterX(circles[i].getCenterX() + speed[i]*t*Math.cos(Math.toRadians(angle[i])));
            circles[i].setCenterY(circles[i].getCenterY() + speed[i]*t*Math.sin(Math.toRadians(angle[i]))); } }

    private void gameOver(Pane root, Scene scene, double pos, Ball ball){
        final String content = "Game Over";
        double y = ball.getY();
        playSound("breakball", 0.25, 0);
        root.getChildren().remove(ball.getCircle());
        final Text texts = new Text(120, 200, "");
        Font font = loadFont(45); texts.setFont(font);
        texts.setFill(Color.WHITE);
        Color[] colors = new Color[]{Color.AQUAMARINE, Color.ORANGERED, Color.INDIGO, Color.YELLOW};
        int balls = 50;
        Circle[] circles = new Circle[balls]; int[] angle = new int[balls]; double[] speed = new double[balls];
        for (int i=0; i<circles.length; i++){
            speed[i] = 400 + new Random().nextInt(300);
            angle[i] = new Random().nextInt(360);
            circles[i] = new Circle(7, colors[new Random().nextInt(4)]);
            circles[i].setRadius(3+new Random().nextInt(4));
            circles[i].setCenterY(y); circles[i].setCenterX(250);}
        root.getChildren().addAll(circles); root.getChildren().add(texts);
        double time = 2;
        final Animation animation = new Transition() {
            private double lastFrac;
            {setCycleDuration(Duration.millis(time*1000)); }
            @Override
            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                double t = (frac - lastFrac)*time;
                ballBurst(circles, angle, t, speed);
                texts.setText(content.substring(0, n));
                if (frac>0.999) {root.getChildren().removeAll(circles); root.getChildren().remove(texts);}
                lastFrac = frac;}};
        animation.setOnFinished(actionEvent -> {exit(scene, pos);});
        animation.play(); }

    void enterGame(boolean revived, double pos){
        final boolean[] started = {false};
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        final String[] input = {""};
        Text text = new Text(5, 40, "0"); Font font = loadFont(40); text.setFont(font); text.setFill(Color.WHITE);
        Text texts = new Text(425, 40, "0"); texts.setFont(font); texts.setFill(Color.WHITE);
        Star st = new Star(cord(52, 28, 18, 9));
        Diamond d = new Diamond(20, 0, 27, 0, 470); d.glow();
        User user = game.getUser(); Ball ball = user.getBall();
        ArrayList<Obstacles> obstacles = game.getObstArray().getObstArray();
        ArrayList<element> boxes = game.getBoxes();

        root.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY, Insets.EMPTY)));

        game.draw(root);

        if (revived) {
            int lastBox = game.getUser().getLastColorBox();
            double lastY = game.getBoxes().get(lastBox).getY();
            if (lastY > 650)  game.getObjects().forEach(obj -> obj.move(500-lastY));
            if(pos > 0) game.getUser().getBall().setY(game.getBoxes().get(game.getUser().getLastColorBox()).getY());
            else game.getUser().getBall().setY(630); }

        root.getChildren().addAll(text, texts);
        st.draw(root); st.get().setFill(Color.YELLOW); d.draw(root);
        ImageView im = addImage(root, "select1.png", 233, (int) game.getHandY(), 40, 40);

        ArrayList<obj> objects = game.getObjects();
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = System.nanoTime();
            private long startTime = System.nanoTime();
            private double scroll = 0; private double totalScroll = 0;
            private int boxes1 = user.getLastColorBox();
            @Override
            public void start() {
                lastTime = System.nanoTime();
                super.start(); }

            @Override
            public void handle(long currentTime) {
                double t = (currentTime - lastTime) / 1e9;
                double timeSinceStart = (currentTime - startTime)/1e9;

                ball.move(t, started[0]);

                double ballY = ball.getY();

                if (ballY<=HEIGHT/2){
                    scroll = HEIGHT/2 - ballY; }
                else if (ballY>=HEIGHT-10){
                    scroll = HEIGHT-10-ballY; }

                totalScroll += scroll;

                if ((started[0]) && (ballY>=HEIGHT) && (totalScroll<=0)){
                    stop(); gameOver(root, scene, -1, ball);}

                for (obj object: objects){
                    object.move(scroll); }

                game.scrollHand(scroll);
                im.setY(game.getHandY());

                scroll = 0;

                for (Obstacles o: obstacles){
                    if (o.hitObstacle(ball, timeSinceStart)){
                        if (!user.isInvincible()){
                            stop();
                            gameOver(root, scene, o.getyBottom(), ball);}}
                    if (o.getClass() == Diamond.class){
                        o.handleCollision(user, root); }
                    o.rotation(t);
                    o.starCollision(user, root); }

                if (user.getLastColorBox()>boxes1){
                    user.loseInvincibility();
                    game.createObstacles(root, objects);
                    boxes1 = user.getLastColorBox(); }

                for (element box: boxes){
                    box.handleCollision(user, root); }

                if (user.getScore()>=10) st.get().setTranslateX(17);
                text.setText(""+ user.getScore());
                texts.setText(""+ user.getDiamonds());

                lastTime = currentTime; }};
        timer.start();

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                spaceTyped();
                ball.jump();
                started[0] = true; }
            else if (event.getCode() == KeyCode.ESCAPE) {
                timer.stop();
                displayPauseMenu(scene, timer); }
            else if (event.getCode() == KeyCode.S) {
                saveGame(true); }
            else if (event.getCode() == KeyCode.TAB) {
                if ((user.getDiamonds()>0) && (!user.isInvincible())){
                    user.setInvincible(true);
                    user.loseDiamond(); } }
            else {
                input[0] += event.getCode().getName();
                if ((!game.getCheat()) && (input[0].contains("BOT"))){
                    showCheat(root, ball.getY());
                        game.setCheat(true);  } } });
        primaryStage.setScene(scene); }

    void newGame(){
        String s = game.getUser().getName(); int saved = game.getUser().getSavedGames();
        game = new Game(s, 700, 500, 200);
        if (!newUser){
            game.getUser().setSavedGames(saved);}
        if (newUser){
            newUser=false; }
        enterGame(false, 0);
    }

    private void showCheat(Pane root, double y){
        Text text = new Text(95, y, "CHEATCODE APPLIED!");
        text.setFill(Color.DEEPPINK); Font font = loadFont(28); text.setFont(font);
        root.getChildren().add(text);
        int up = 10;
        final Animation animation = new Transition() {
            {setCycleDuration(Duration.seconds(1)); }
            @Override
            protected void interpolate(double frac) {
                text.setY(y-40-frac*up); }};
        animation.setOnFinished(event -> root.getChildren().remove(text));
        animation.play(); }
}