
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import javax.management.ImmutableDescriptor;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;


public class Main extends Application
{

    public static ObservableList<Node> child;
    //
    private static final String title = "JellyBeanci THE LAND OF RISING SUN!";
    public static final int width = 1200;
    public static final int height = 800;

    public static double angleConstant = 30;
    public static double xOffset = -180;
    public static double yOffset = 0;
    //
    ImageView imageViewBack;
    Image imgBack;
    //String imgBackURL = "img\\back.png";
    //
    ImageView imageViewFront;
    Image imgFront;
    //String imgFrontURL = "img\\front.png";
    //
    public static Image sakura;
    //
    public static Image samurai;
    public static Image bandana1;
    public static Image bandana2;
    public static Image bandana3;
    public static Image bandana4;
    public static Image bandana5;
    public static Image bandana6;
    public static Image bandana7;
    public static Image bandana8;
    public static Image bandana9;
    public static Image bandana10;

    private boolean egg = false;

    private static double perlinOffset = Utils.getRandom(123456);

    public static ExecutorService executorService;

    //private static Color backcolor = Color.rgb(51, 51, 51);
    private static Color backcolor = Color.SNOW;

    private static Timeline update;

    @Override
    public void start(Stage stage) throws Exception
    {
        Pane root = new Pane();
        child = root.getChildren();
        //Back End
        imgBack = new Image(String.valueOf(this.getClass().getResource("/back.png")));
        //imgBack = new Image(new FileInputStream(imgBackURL));
        imageViewBack = new ImageView(imgBack);
        imageViewBack.setFitWidth(width);
        imageViewBack.setFitHeight(height);
        //Front End
        imgFront = new Image(String.valueOf(this.getClass().getResource("/front.png")));
        //
        imageViewFront = new ImageView(imgFront);
        imageViewFront.setFitWidth(width);
        imageViewFront.setFitHeight(height);
        //
        sakura = new Image(String.valueOf(this.getClass().getResource("/sakura.png")));
        //
        samurai = new Image(String.valueOf(this.getClass().getResource("/samurai/samurai.png")));
        bandana1 = new Image(String.valueOf(this.getClass().getResource("/samurai/bandana1.png")));
        bandana2 = new Image(String.valueOf(this.getClass().getResource("/samurai/bandana2.png")));
        bandana3 = new Image(String.valueOf(this.getClass().getResource("/samurai/bandana3.png")));
        bandana4 = new Image(String.valueOf(this.getClass().getResource("/samurai/bandana4.png")));
        bandana5 = new Image(String.valueOf(this.getClass().getResource("/samurai/bandana5.png")));
        bandana6 = new Image(String.valueOf(this.getClass().getResource("/samurai/bandana6.png")));
        bandana7 = new Image(String.valueOf(this.getClass().getResource("/samurai/bandana7.png")));
        bandana8 = new Image(String.valueOf(this.getClass().getResource("/samurai/bandana8.png")));
        bandana9 = new Image(String.valueOf(this.getClass().getResource("/samurai/bandana9.png")));
        bandana9 = new Image(String.valueOf(this.getClass().getResource("/samurai/bandana10.png")));
        //
        Samurai samurai = new Samurai();
        samurai.setLocation(width - 350, 560);
        samurai.getNode().setVisible(false);
        //
        SunRise sunRise = new SunRise();
        //
        FractalTree tree = new FractalTree();
        //
        child.addAll(sunRise.getNode(), imageViewBack, samurai.getNode(), tree.getNode());

        // End of Tree Branches
        ArrayList<Branch> endOfTrees = new ArrayList<>();
        for (Branch branch : Branch.branches)
        {
            if (branch.isEndOfTree())
            {
                endOfTrees.add(branch);
            }
        }
        // Sakura List
        ArrayList<Sakura> sakuras = new ArrayList<>();
        for (Branch branch : endOfTrees)
        {
            Sakura temp = new Sakura();
            temp.setRoot(branch);
            sakuras.add(temp);
            child.add(temp.getNode());
        }

        child.addAll(imageViewFront); // t,test);

        root.setOnKeyPressed(e -> {
            switch (e.getCode())
            {
                case F1:
                {
                    //PLAY
                    update.play();
                    break;
                }
                case F2:
                {
                    //PAUSE
                    update.pause();
                    break;
                }
                case F3:
                {
                    //Show Child Count
                    System.out.println("Child Count: " + child.size());
                    break;
                }
                case F4:
                {
                    // switch Graphics
                    for (Sakura sakura : sakuras)
                    {
                        sakura.switchGraphics();
                    }
                    break;
                }
                case F9:
                {
                    if (!egg)
                    {
                        samurai.getNode().setVisible(true);
                        samurai.playAnim();
                    } else
                    {
                        samurai.getNode().setVisible(false);
                        samurai.pauseAnim();
                    }
                    egg = !egg;
                    break;
                }
            }
        });
        //
        update = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            //60 fps
            double angle = SimpleNoise.noise(perlinOffset, 0, -80, -5, true);
            tree.update(angle);
            for (Sakura sakura : sakuras)
            {
                sakura.update();
            }
            perlinOffset += 0.0025;
        }));
        update.setCycleCount(Timeline.INDEFINITE);
        update.setRate(1);
        update.setAutoReverse(false);
        //update.play(); //uncomment for play when start
        //
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(new Scene(root, width - 10, height - 10, backcolor));
        stage.show();
        root.requestFocus();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}