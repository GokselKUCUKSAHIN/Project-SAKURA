import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class Sakura implements IObservable
{

    private Group body = new Group();
    private static Rotate rotate = new Rotate();
    private Branch root;
    private double size = 1;
    private boolean isSnapOff = false;

    private double yvel = 0;
    private double yacc = 0.01;
    private double xvel = 0;
    private double xacc = 0;
    private double wind = Utils.getRandom(12345);
    private boolean graphics = false;

    static
    {
        rotate.setAngle(72);
        rotate.setPivotX(0);
        rotate.setPivotY(0);
    }

    public Sakura()
    {
        body.setLayoutX(-30);
        body.setLayoutY(-30);
        draw();
        size = Utils.getRandom(1.3, 1.9);
        body.setScaleX(size);
        body.setScaleY(size);
        body.setRotate(Utils.getRandom(360));
    }


    private void draw()
    {
        Circle center = new Circle(0, 0, 4);
        center.setFill(Color.HOTPINK);
        center.setStroke(Color.DEEPPINK);
        center.setStrokeWidth(0.5);
        this.body.getChildren().addAll(center);
    }
    private void draw2()
    {
        ArrayList<Arc> arcs = new ArrayList<>();
        Circle center = new Circle(0, 0, 2.5);
        center.setFill(Color.HOTPINK);
        for (int i = 0; i < 5; i++)
        {
            arcs.add(new Arc(0, 0, 3, 6, 0, 138));
        }
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < i; j++)
            {
                arcs.get(j).getTransforms().add(rotate);
            }
            arcs.get(i).setType(ArcType.CHORD);
            arcs.get(i).setFill(Color.PINK);
            arcs.get(i).setStroke(Color.DEEPPINK);
            arcs.get(i).setStrokeWidth(0.3);
            arcs.get(i).setStrokeType(StrokeType.INSIDE);
        }
        this.body.getChildren().addAll(center);
        for (Arc arc : arcs)
        {
            body.getChildren().add(arc);
        }
    }

    public void switchGraphics()
    {
        body.getChildren().clear();
        graphics = !graphics;
        if(graphics)
        {
            draw2();
        }
        else
        {
            draw();
        }
    }
    public void setRoot(Branch branch)
    {
        this.root = branch;
    }

    public void update()
    {
        if (!isSnapOff)
        {
            Point2D pnt = root.getFlowerPot();
            this.body.setLayoutX(pnt.getX());
            this.body.setLayoutY(pnt.getY());
            if (size > 2.3)
            {
                isSnapOff = true;
            } else
            {
                size += 0.001;
            }
        } else
        {
            this.body.setLayoutY(body.getLayoutY() + yvel);
            this.body.setLayoutX(body.getLayoutX() + xvel);
            xacc = SimpleNoise.noise(wind, 0, -0.013, 0.025, true);
            yvel += yacc;
            xvel += xacc;
            wind += 0.005;
            if (this.body.getLayoutY() > Main.height + 2)
            {
                isSnapOff = false;
                size = Utils.getRandom(1.3, 1.9);
                body.setScaleX(size);
                body.setScaleY(size);
                yvel = 0;
                xvel = 0;
            }
        }
    }

    @Override
    public Node getNode()
    {
        return this.body;
    }
}
