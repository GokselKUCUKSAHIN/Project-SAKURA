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

    Group body = new Group();
    static Rotate rotate = new Rotate();
    Branch root;

    static
    {
        rotate.setAngle(72);
        rotate.setPivotX(0);
        rotate.setPivotY(0);
    }

    public Sakura()
    {
        body.setLayoutX(Main.width / 2);
        body.setLayoutY(Main.height / 2);
        draw();
        body.setScaleX(15);
        body.setScaleY(15);
    }

    private void draw()
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

    public void setRoot(Branch branch)
    {
        this.root = branch;
    }
    public void update()
    {
    }

    @Override
    public Node getNode()
    {
        return this.body;
    }
}
