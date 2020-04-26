import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws NotEnoughPointsException {
        ArrayList<Vector2D> points = new ArrayList<Vector2D>();
        Random gen = new Random();
        int N = 20;
        for (int i = 0; i < N; i++){
            double x = gen.nextDouble();
            double y = gen.nextDouble();
            points.add(new Vector2D(x,y));
        }

        DelaunayTriangulator delaunayTriangulator = new DelaunayTriangulator(points);
        delaunayTriangulator.triangulate();
        List<Triangle2D> triangles = delaunayTriangulator.getTriangles();

        StdDraw.setPenRadius(.01);
        for (Vector2D p: points) {
            StdDraw.point(p.x, p.y);
        }
        StdDraw.setPenRadius(.002);
        for (Triangle2D t: triangles) {
            StdDraw.line(t.a.x, t.a.y, t.b.x, t.b.y);
            StdDraw.line(t.b.x, t.b.y, t.c.x, t.c.y);
            StdDraw.line(t.c.x, t.c.y, t.a.x, t.a.y);
        }
    }
}
