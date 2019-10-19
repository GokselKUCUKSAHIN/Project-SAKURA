import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Branch
{

    public static ArrayList<Branch> branches = new ArrayList<>();
    private Point2D begin;
    private Point2D end;
    private Line node;
    private Line shadow;
    private double prevAng = 0;
    private double angle = 0;
    private double length = 0;
    private Point2D r;
    private Point2D l;
    private Branch right;
    private Branch left;
    private double xoff = 12;
    public static double yoff = 1;
    private boolean done = false;
    private double thickness = 8;  //6
    //
    private boolean endOfTree = true; // is this end of tree
    private Point2D flowerPot = new Point2D(0, 0);

    public Branch(Point2D begin, Point2D end)
    {
        prevAng = 0;
        this.begin = begin;
        this.end = end;
        node = new Line(begin.getX(), begin.getY(), end.getX(), end.getY());
        shadow = new Line(begin.getX(), begin.getY(), end.getX(), end.getY());
        //node.setStrokeWidth(2.1);
        node.setStrokeWidth(this.thickness);
        shadow.setStrokeWidth(this.thickness * 2.5);
        node.setStroke(Color.SNOW); // snow
        shadow.setStroke(Color.valueOf("2E302FC1"));
        angle = Utils.calculateAngle(begin, end);
        length = Utils.distance(begin, end);
        branches.add(this);
    }

    public Node getNode()
    {
        return this.node;
    }

    public Node getShadow()
    {
        return this.shadow;
    }

    public void branch()
    {
        r = Utils.endPoint(end, this.angle - Main.angleConstant, this.length * 0.667);
        l = Utils.endPoint(end, this.angle + Main.angleConstant, this.length * 0.667);
        right = new Branch(this.end, r);
        left = new Branch(this.end, l);
        right.prevAng = this.angle;
        left.prevAng = this.angle;
        right.xoff = this.xoff += 0.1;
        left.xoff = this.xoff += 0.1;
        right.setThickness(this.thickness * 0.8);
        left.setThickness(this.thickness * 0.8);
        endOfTree = false;
    }

    public boolean isEndOfTree()
    {
        return this.endOfTree;
    }

    public double getLength()
    {
        return this.length;
    }

    public void setBegin(Point2D begin)
    {
        this.begin = begin;
        this.node.setStartX(begin.getX());
        this.node.setStartY(begin.getY());
        this.shadow.setStartX(begin.getX());
        this.shadow.setStartY(begin.getY());
    }

    public void setEnd(Point2D end)
    {
        this.end = end;
        this.angle = Utils.calculateAngle(this.begin, this.end);
        this.node.setEndX(end.getX());
        this.node.setEndY(end.getY());
        this.shadow.setEndX(end.getX());
        this.shadow.setEndY(end.getY());
    }

    public void setThickness(double thickness)
    {
        this.thickness = thickness;
        this.node.setStrokeWidth(thickness);
        this.shadow.setStrokeWidth(thickness * 2.5);
    }

    public void updateR(double updAngle)
    {
        if (right != null)
        {
            double ang = this.right.prevAng - updAngle;
            this.right.setBegin(this.end);
            Point2D endR = Utils.endPoint(this.end, ang, right.getLength());
            this.right.setEnd(endR);
            this.right.prevAng = this.angle;
        }
    }

    public void updateL(double updAngle)
    {
        if (left != null)
        {
            double ang = this.left.prevAng + updAngle;
            this.left.setBegin(this.end);
            Point2D endL = Utils.endPoint(this.end, ang, left.getLength());
            this.left.setEnd(endL);
            this.left.prevAng = this.angle;
        }
        if (endOfTree)
        {
            this.flowerPot = this.end;
        }
    }

    public Point2D getFlowerPot()
    {
        return this.flowerPot;
    }
}