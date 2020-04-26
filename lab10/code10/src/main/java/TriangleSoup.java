import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TriangleSoup {

    private List<Triangle2D> triangleSoup;

    public TriangleSoup() {
        this.triangleSoup = new ArrayList<Triangle2D>();
    }

    public void add(Triangle2D triangle) {
        this.triangleSoup.add(triangle);
    }

    public void remove(Triangle2D triangle) {
        this.triangleSoup.remove(triangle);
    }

    public List<Triangle2D> getTriangles() {
        return this.triangleSoup;
    }

    /**
     * Returns the triangle from this triangle soup that contains the specified
     * point or null if no triangle from the triangle soup contains the point.
     */
    public Triangle2D findContainingTriangle(Vector2D point) {
        for (Triangle2D triangle : triangleSoup) {
            if (triangle.contains(point)) {
                return triangle;
            }
        }
        return null;
    }

    /**
     * Returns the neighbor triangle of the specified triangle sharing the same
     * edge as specified. If no neighbor sharing the same edge exists null is
     * returned.
     */
    public Triangle2D findNeighbour(Triangle2D triangle, Edge2D edge) {
        for (Triangle2D triangleFromSoup : triangleSoup) {
            if (triangleFromSoup.isNeighbour(edge) && triangleFromSoup != triangle) {
                return triangleFromSoup;
            }
        }
        return null;
    }

    /**
     * Returns one of the possible triangles sharing the specified edge.
     * To find the other triangle that shares this edge use
     * the findNeighbour(triangle, edge) method.
     */
    public Triangle2D findOneTriangleSharing(Edge2D edge) {
        for (Triangle2D triangle : triangleSoup) {
            if (triangle.isNeighbour(edge)) {
                return triangle;
            }
        }
        return null;
    }

    /**
     * Returns the edge from the triangle soup nearest to the specified point.
     */
    public Edge2D findNearestEdge(Vector2D point) {
        List<EdgeDistancePack> edgeList = new ArrayList<EdgeDistancePack>();

        for (Triangle2D triangle : triangleSoup) {
            edgeList.add(triangle.findNearestEdge(point));
        }

        EdgeDistancePack[] edgeDistancePacks = new EdgeDistancePack[edgeList.size()];
        edgeList.toArray(edgeDistancePacks);

        Arrays.sort(edgeDistancePacks);
        return edgeDistancePacks[0].edge;
    }

    /**
     * Removes all triangles from this triangle soup that contain the specified vertex.
     */
    public void removeTrianglesUsing(Vector2D vertex) {
        List<Triangle2D> trianglesToBeRemoved = new ArrayList<Triangle2D>();

        for (Triangle2D triangle : triangleSoup) {
            if (triangle.hasVertex(vertex)) {
                trianglesToBeRemoved.add(triangle);
            }
        }
        triangleSoup.removeAll(trianglesToBeRemoved);
    }

}