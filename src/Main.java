
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
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
import javafx.util.Duration;

import javax.management.ImmutableDescriptor;
import java.io.FileInputStream;
import java.util.ArrayList;


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
    String imgBackURL = "img\\back.png";
    //
    ImageView imageViewFront;
    Image imgFront;
    String imgFrontURL = "img\\front.png";
    //
    private static double perlinOffset = Utils.getRandom(123456);


    //private static Color backcolor = Color.rgb(51, 51, 51);
    private static Color backcolor = Color.SNOW;

    private static Timeline update;

    @Override
    public void start(Stage stage) throws Exception
    {
        Pane root = new Pane();
        child = root.getChildren();

        //Back End
        imgBack = new Image(new FileInputStream(imgBackURL));
        imageViewBack = new ImageView(imgBack);
        imageViewBack.setFitWidth(width);
        imageViewBack.setFitHeight(height);

        //Front End
        imgFront = new Image(new FileInputStream(imgFrontURL));
        imageViewFront = new ImageView(imgFront);
        imageViewFront.setFitWidth(width);
        imageViewFront.setFitHeight(height);


        SunRise sunRise = new SunRise();

        FractalTree tree = new FractalTree();

        child.addAll(sunRise.getNode(), imageViewBack, tree.getNode());

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
        child.add(imageViewFront);

        /*root.setOnMouseMoved(e -> {
            double angle = Utils.map(e.getSceneX(), 0, width, -90, 0);
            tree.update(angle);
            for (Sakura sakura : sakuras)
            {
                sakura.update();
            }
        });*/

        //
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
            }
        });
        //
        update = new Timeline(new KeyFrame(Duration.millis(32), e -> {
            //60 fps
            double angle = SimpleNoise.noise(perlinOffset, 0, -90, 0, true);
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
