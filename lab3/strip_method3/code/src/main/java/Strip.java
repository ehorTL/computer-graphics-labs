import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import static java.lang.Integer.signum;


class CustomComparator implements Comparator<Integer> {

    static int indexTop, indexBottom;
    static Point pointTop, pointBottom;
    static ArrayList<Point> vertex, allEdges;

    private double getPointCross(Point a, Point b, int line) {
        return a.x + 1.0 * (line - a.y) * (b.x - a.x) / (b.y - a.y);
    }

    private double getPointCross(Integer j, int line, ArrayList<Point> vertex, ArrayList<Point> allEdges) {
        return getPointCross(vertex.get(allEdges.get(j).x), vertex.get(allEdges.get(j).y), line);
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        if (getPointCross(o1, pointBottom.y, vertex, allEdges) > getPointCross(o2, pointBottom.y, vertex, allEdges) ||
                getPointCross(o1, pointTop.y, vertex, allEdges) > getPointCross(o2, pointTop.y, vertex, allEdges))
            return 1;
        return 0;
    }
}

public class Strip {
    int indexTop, indexBottom;
    Point pointTop, pointBottom;
    ArrayList<Integer> edges = new ArrayList<>();

    Strip(int indexBottom, int indexTop, ArrayList<Point> vertex) {
        this.indexBottom = indexBottom;
        this.indexTop = indexTop;
        pointBottom = vertex.get(indexBottom);
        pointTop = vertex.get(indexTop);
    }

    private double getPointCross(Point a, Point b, int line) {
        return a.x + 1.0 * (line - a.y) * (b.x - a.x) / (b.y - a.y);
    }

    private double getPointCross(int j, int line, ArrayList<Point> vertex, ArrayList<Point> allEdges) {
        return getPointCross(vertex.get(allEdges.get(edges.get(j)).x), vertex.get(allEdges.get(edges.get(j)).y), line);
    }

    void sort(ArrayList<Point> vertex, ArrayList<Point> allEdges) {
        CustomComparator.indexBottom = indexBottom;
        CustomComparator.indexTop = indexTop;
        CustomComparator.pointBottom = pointBottom;
        CustomComparator.pointTop = pointTop;
        CustomComparator.vertex = vertex;
        CustomComparator.allEdges = allEdges;
        edges.sort(new CustomComparator());
    }

    /**
     * -1 bottom
     *  0 in
     *  1 top
    */
    int contain(Point p) {
        if (p.y < pointBottom.y) return -1;
        if (p.y < pointTop.y) return 0;
        return 1;
    }

    int pointFromLine(Point p, Point a, Point b) {
        if (a.y > b.y) return pointFromLine(p, b, a);
        int D = (p.x - a.x) * (b.y - a.y) - (p.y - a.y) * (b.x - a.x);
        return signum(D);
    }

    Point getEdges(Point p, ArrayList<Point> vertex, ArrayList<Point> allEdges) {
        if (pointFromLine(p, vertex.get(allEdges.get(edges.get(0)).x), vertex.get(allEdges.get(edges.get(0)).y)) == -1
                || pointFromLine(p, vertex.get(allEdges.get(edges.get(edges.size() - 1)).x), vertex.get(allEdges.get(edges.get(edges.size() - 1)).y)) == 1) {
            return new Point(-1, -1);
        }

        int top = edges.size() - 1;
        int bottom = 0;
        int mid = (top + bottom) / 2;
        int tempComputation = pointFromLine(p, vertex.get(allEdges.get(edges.get(mid)).x), vertex.get(allEdges.get(edges.get(mid)).y));
        while (tempComputation != 0 && top - bottom != 1) {
            if (tempComputation == -1) {
                top = mid;
                mid = (top + bottom) / 2;
            } else if (tempComputation == 1) {
                if (bottom == mid) {
                    mid = top;
                } else {
                    bottom = mid;
                    mid = (top + bottom) / 2;
                }
            } else {
                return new Point(edges.get(mid), edges.get(mid));
            }
            tempComputation = pointFromLine(p, vertex.get(allEdges.get(edges.get(mid)).x), vertex.get(allEdges.get(edges.get(mid)).y));
        }
        if (tempComputation==0){
            return  new Point(edges.get(mid), edges.get(mid));
        }
        return new Point(edges.get(mid), edges.get(mid + 1));

    }
}
