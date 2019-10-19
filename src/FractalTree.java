
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;

public class FractalTree implements IObservable
{

    private static int factor = 10;

    Group body = new Group();

    public FractalTree()
    {
        draw();
    }

    private void draw()
    {
        double initLength = 250;
        Point2D a = new Point2D(Main.width / 2 + Main.xOffset, Main.height + Main.yOffset);
        Point2D b = new Point2D(Main.width / 2 + Main.xOffset, (Main.height + Main.yOffset) - initLength);
        Branch root = new Branch(a, b);
        int limit = (int) Math.pow(2, factor) - 1;
        for (int i = 0; i < limit; i++)
        {
            Branch.branches.get(i).branch();
        }
        for (Branch branch : Branch.branches)
        {
            body.getChildren().add(branch.getNode());
        }
        //
    }

    public void update(double value)
    {
        for (int i = 0; i < Branch.branches.size(); i++)
        {
            Branch temp = Branch.branches.get(i);
            temp.updateR(value);
            temp.updateL(value);
        }
    }

    @Override
    public Node getNode()
    {
        return this.body;
    }
}