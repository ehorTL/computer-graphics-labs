import java.util.ArrayList;

public class Polygon {
    private ArrayList<Edge2Dimentional> edgeList;

    public Polygon(){
        edgeList = new ArrayList<Edge2Dimentional>();
    }

    public Polygon(ArrayList<Edge2Dimentional> edges){
        if (edges != null){
            edgeList = edges;
        }
        else {
            edgeList = new ArrayList<Edge2Dimentional>();
        }
    }

    public ArrayList<Edge2Dimentional> getEdges(){
        return edgeList;
    }
}
