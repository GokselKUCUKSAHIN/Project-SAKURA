
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application
{

    public static ObservableList<Node> child;
    //
    private static final String title = "JellyBeanci THE LAND OF RISING SUN!";
    public static final int width = 1200;
    public static final int height = 800;

    public static double angleConstant = 30;
    public static double xOffset = -200;
    public static double yOffset = -50;

    //private static Color backcolor = Color.rgb(51, 51, 51);
    private static Color backcolor = Color.SNOW;

    private static Timeline update;

    @Override
    public void start(Stage stage) throws Exception
    {
        Pane root = new Pane();
        child = root.getChildren();
        //
        Sakura sakura = new Sakura();


        SunRise sunRise = new SunRise();
        Rectangle ground = new Rectangle(width, height * 0.30);

        ground.setLayoutY(height * 0.7);
        ground.setFill(Color.GREEN);

        FractalTree tree = new FractalTree();

        Circle test = new Circle(0, 0, 15, Color.YELLOW);
        Branch b1 = Branch.branches.get(Branch.branches.size() - 1);



        root.setOnMouseMoved(e -> {
            double angle = Utils.map(e.getSceneX(), 0, width, -90, 0);
            tree.update(angle);
        });

        child.addAll(sunRise.getNode(), ground, tree.getNode(), sakura.getNode(), test);
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
        update = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            //60 fps
            //System.out.println("loop test");
            Point2D p1 = b1.getFlowerPot();
            test.setCenterX(p1.getX());
            test.setCenterY(p1.getY());

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
