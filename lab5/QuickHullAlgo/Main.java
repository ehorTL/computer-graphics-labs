import drawer.FigureDrawer;
import pointreader.PointReader;
import quickhull.AlgorithmQuickHall2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        PointReader pointReader = new PointReader("C:\\lab6\\pointsQuickHull.txt");
        ArrayList<Point2D> points = pointReader.getPoints();

        ArrayList<Point2D> result = AlgorithmQuickHall2D.quickHullAlgoMain(points);
        for (Point2D p : result){
            System.out.println(p);
        }

        FigureDrawer figureDrawer = new FigureDrawer("Convex hull", result, points,true);
    }
}
