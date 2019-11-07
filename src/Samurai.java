import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Samurai implements IObservable
{

    ImageView bandana1IV;
    ImageView bandana2IV;
    ImageView bandana3IV;

    Group body = new Group();

    int count = 0;
    Timeline anim = new Timeline(new KeyFrame(Duration.millis(192), e -> {
        if (count == 0)
        {
            bandana3IV.setVisible(false);
            bandana1IV.setVisible(true);
            count = 1;
        } else if (count == 1)
        {
            bandana1IV.setVisible(false);
            bandana2IV.setVisible(true);
            count = 2;
        } else if (count == 2)
        {
            bandana2IV.setVisible(false);
            bandana3IV.setVisible(true);
            count = 0;
        }
    }));

    public Samurai()
    {
        draw();
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
        bandana1IV = new ImageView(Main.bandana1);
        bandana1IV.setFitWidth(200);
        bandana1IV.setFitHeight(200);
        bandana1IV.setScaleX(-1);
        bandana1IV.setX(90);
        bandana1IV.setY(-50);
        //
        bandana2IV = new ImageView(Main.bandana2);
        bandana2IV.setFitWidth(200);
        bandana2IV.setFitHeight(200);
        bandana2IV.setScaleX(-1);
        bandana2IV.setX(90);
        bandana2IV.setY(-50);
        bandana2IV.setVisible(false);
        //
        bandana3IV = new ImageView(Main.bandana3);
        bandana3IV.setFitWidth(200);
        bandana3IV.setFitHeight(200);
        bandana3IV.setScaleX(-1);
        bandana3IV.setX(90);
        bandana3IV.setY(-50);
        bandana3IV.setVisible(false);
        //
        this.body.getChildren().addAll(bandana1IV, bandana2IV, bandana3IV, samuraiIV);
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
