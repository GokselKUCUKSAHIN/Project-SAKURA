import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;

public class SunRise implements IObservable
{

    Group body = new Group();
    public Timeline animation;

    public SunRise()
    {
        // Positioning
        body.setLayoutX(Main.width * 0.8);
        body.setLayoutY(Main.height * 0.6);

        draw();

        // Scaling
        body.setScaleX(10);
        body.setScaleY(10);

        // Ratation Animation (Rotate Transition can be use too)
        animation = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            body.setRotate(body.getRotate() + 0.04);
        }));
        animation.setRate(1);
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setAutoReverse(false);
        animation.play(); //while developing
    }

    public void draw()
    {
        Circle sun = new Circle(0, 0, 10, Color.valueOf("FF3A02")); //FF3A02
        sun.setStrokeWidth(0.6);
        sun.setStroke(Color.rgb(0, 0, 0, 0.8));
        //sun.setStroke(Color.valueOf("FFD73A").darker());
        ArrayList<Arc> arcs = new ArrayList<>();

        for (int i = 0; i < 360; i += 20)
        {
            arcs.add(new Arc(0, 0, 125, 125, i, 10));
        }
        for (Arc arc : arcs)
        {
            arc.setType(ArcType.ROUND);
            arc.setFill(Color.valueOf("FF3A02").brighter()); //FF3A02
            body.getChildren().add(arc);
        }
        body.getChildren().add(sun);
    }

    @Override
    public Node getNode()
    {
        return this.body;
    }

    public void play()
    {
        animation.play();
    }

    public void pause()
    {
        animation.pause();
    }
}