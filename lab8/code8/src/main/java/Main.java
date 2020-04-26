import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws FileNotFoundException {
        try (Scanner sc = new Scanner(System.in)) {
            ArrayList<Point> points = PolygonConvexHull.inputDatas("input.txt");

            ArrayList<Point> convexHull = PolygonConvexHull.build(points);
            System.out.println("THE POINTS OF HULL: ");
            for (int i = 0; i < convexHull.size(); i++){
                System.out.println(convexHull.get(i).x + " " + convexHull.get(i).y);
            }
        }
    }
}