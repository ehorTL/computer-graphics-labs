public class EdgeDistancePack implements Comparable<EdgeDistancePack> {

    public Edge2D edge;
    public double distance;

    /**
     * The distance of the edge to some point
     */
    public EdgeDistancePack(Edge2D edge, double distance) {
        this.edge = edge;
        this.distance = distance;
    }

    @Override
    public int compareTo(EdgeDistancePack o) {
        return Double.compare(this.distance, o.distance);
    }
}