import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Samurai implements IObservable
{

    ImageView[] frames = new ImageView[10]; //10 frames of Bandana

    Group body = new Group();

    int count = 0;
    Timeline anim = new Timeline(new KeyFrame(Duration.millis(125), e -> {
        int current = (count + 1) % frames.length;
        int prev = (count) % frames.length;
        //
        frames[current].setVisible(true);
        frames[prev].setVisible(false);
        count++;
    }));

    public Samurai()
    {
        draw();
        //
        anim.setAutoReverse(false);
        anim.setCycleCount(Timeline.INDEFINITE);
        anim.setRate(1);
        anim.pause();
    }

    private void draw()
    {
        ImageView samuraiIV = new ImageView(Main.samurai);
        samuraiIV.setFitHeight(250);
        samuraiIV.setFitWidth(250);
        samuraiIV.setScaleX(-1);
        samuraiIV.setX(0);
        samuraiIV.setY(0);
        //
        frames[0] = new ImageView(Main.bandana1);
        frames[1] = new ImageView(Main.bandana2);
        frames[2] = new ImageView(Main.bandana3);
        frames[3] = new ImageView(Main.bandana4);
        frames[4] = new ImageView(Main.bandana5);
        frames[5] = new ImageView(Main.bandana6);
        frames[6] = new ImageView(Main.bandana7);
        frames[7] = new ImageView(Main.bandana8);
        frames[8] = new ImageView(Main.bandana9);
        frames[9] = new ImageView(Main.bandana10);
        //
        frames[1].setVisible(false);
        frames[2].setVisible(false);
        frames[3].setVisible(false);
        frames[4].setVisible(false);
        frames[5].setVisible(false);
        frames[6].setVisible(false);
        frames[7].setVisible(false);
        frames[8].setVisible(false);
        frames[9].setVisible(false);
        for (ImageView imageView : frames)
        {
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            imageView.setScaleX(-1);
            imageView.setX(90);
            imageView.setY(-50);
        }
        //
        this.body.getChildren().addAll(frames[0], frames[1], frames[2], frames[3], frames[4], frames[5], frames[6], frames[7], frames[8], frames[9], samuraiIV);
    }

    public void setLocation(double x, double y)
    {
        this.body.setLayoutX(x);
        this.body.setLayoutY(y);
    }

    public void playAnim()
    {
        this.anim.play();
    }

    public void pauseAnim()
    {
        this.anim.pause();
    }

    @Override
    public Node getNode()
    {
        return body;
    }
} 