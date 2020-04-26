import drawer.FigureDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/***
 * Main class for solving point location problem.
 * Points the polygon to be constructed are read from file.
 * The point you want to check is set with its coordinates (pointToLocateX, pointToLocateY).
 *
 * Path to file must be set properly.
 * File syntax:
 * 1) each point is written as "(x,y)" without qoutes
 * 2) each point is written in a new line
 * 3) decimal separator is point sign '.' (eg 12.2)
 * Correct syntax example: (12.3,0.9)
 *
 * Answer is shown in console and equals "true" if point locates in polygon
 * and "false" if not
 *
 * Graph is shown to ensure you the algorithm works correctly.
 */

public class Main {
    public static void main(String[] args) {
        double pointToLocateX = 180;//180;
        double pointToLocateY = -30;//-30;
        Point2Dimentional pointToLocate = new Point2Dimentional(pointToLocateX, pointToLocateY);

        PointReader pr = new PointReader("C:\\lab6\\points.txt");

        ArrayList<Point2Dimentional> pointList = pr.getPoints();
        ArrayList<Edge2Dimentional> edgeList = new ArrayList<>();

        try{
            for (int i = 1; i < pointList.size(); i++){
                edgeList.add(new Edge2Dimentional(pointList.get(i - 1), pointList.get(i)));
            }
            edgeList.add(new Edge2Dimentional(pointList.get(pointList.size()-1), pointList.get(0)));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Polygon polygon = new Polygon(edgeList);
        boolean locatesInPolygon = Algorithm.pointLocatesInSimplePolygon(polygon, pointToLocate);
        System.out.println(locatesInPolygon);

        //adapter for Edge2Dimentional to Line2D
        ArrayList<Line2D> edgesLine2D = new ArrayList<>();
        for (Edge2Dimentional e : edgeList){
            edgesLine2D.add(new Line2D.Double(e.getBegin().getX(), e.getBegin().getY(), e.getEnd().getX(), e.getEnd().getY()));
        }
        FigureDrawer figureDrawer = new FigureDrawer("Polygon and point", edgesLine2D, true,
                new Point2D.Double(pointToLocateX, pointToLocateY));
    }
}
