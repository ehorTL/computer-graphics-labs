import java.util.ArrayList;
import java.util.List;
import datastructures.*;

public class Main {

    public static void main(String[] args) {
        Point[] points = {
                new Point(1, -10),
                new Point(7, -8),
                new Point(-2, -5),
                new Point(2, -3),
                new Point(5, 0),
                new Point(3, 3),
                new Point(5, 4),
                new Point(-4, 6),
                new Point(2, 8)};

        boolean[][] matrix = {
                {false, true, true, true, true, false, false, false, false},
                {true, false, false, false, true, false, false, false, false},
                {true, false, false, true, false, true, false, true, false},
                {true, false, true, false, true, true, false, false, false},
                {true, true, false, true, false, true, true, false, false},
                {false, false, true, true, true, false, true, false, true},
                {false, false, false, false, true, true, false, false, true},
                {false, false, true, false, false, false, false, false, true},
                {false, false, false, false, false, true, true, true, false} };

        Graph g = new Graph(points, matrix);
        List<GEdge> area = new ArrayList<>();
        try {
            area = g.algo(new Point(1, 1));
        } catch (OutOfGraphException e) {
            System.out.println("Point is out of the graph");
        }
        for(GEdge e : area) {
            System.out.println("(" + e.getAx() + ", " + e.getAy() + "), " + "(" + e.getBx() + ", " + e.getBy() + ")");
        }
    }
}
