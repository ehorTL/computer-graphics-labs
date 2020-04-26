import java.util.ArrayList;

/**
Main class for solving point location problem.
 */

public class Algorithm {
    /**
     * @param p simple polygon, defined with its edges
     * @param z point you want to define whether it locates in polygon or not
     * @return does point locates in polygon
     */
    public static boolean pointLocatesInSimplePolygon(Polygon p, Point2Dimentional z){
        int leftCrossings = 0;
        boolean[] checkedEdges = new boolean[p.getEdges().size()];

        for (int i = 0; i < p.getEdges().size(); i++){
            Edge2Dimentional currentEdge = p.getEdges().get(i);
            if (checkedEdges[i]){
                continue;
            }

            System.out.println(i);

            //case 1: point is on the line segment
            if (currentEdge.hasPoint(z)){
                leftCrossings = 1;
                break;
            }

            //case 2: horizontal line crosses polygon's edge and its not a vertex
            else if (currentEdge.isCrossedByHorizontalLineWithOrdinate(z.getY()) == TypeOfEdgeWithLineCrossing.CROSS
                && z.getX() > currentEdge.getXOfCrossingEdgeWithHorizontalLineThroughPoint(z.getY())){
                leftCrossings++;
            }

            //case 3: no cross or cross from right - do nothing

            //case 4: degenerate case - line crosses polygon vertex
            else if(currentEdge.isCrossedByHorizontalLineWithOrdinate(z.getY()) == TypeOfEdgeWithLineCrossing.CROSS_IN_VERTEX
                && currentEdge.getXOfCrossingEdgeWithHorizontalLineThroughPoint(z.getY()) <= z.getX()){

                //find adjacent edge
                Edge2Dimentional edgeAdjacent = null;
                int edgeAdjacentIndex = 0;
                boolean previous = false;

                if (Math.abs(currentEdge.getBegin().getY() - z.getX()) < Constants.EPS){
                    //get previous edge
                    edgeAdjacentIndex = (i > 0) ? (i-1) : (p.getEdges().size() - 1);
                    edgeAdjacent = p.getEdges().get(edgeAdjacentIndex);
                    previous = true;
                }
                else {
                    //get next edge
                    edgeAdjacentIndex = (i < (p.getEdges().size() - 1)) ? (i+1) : 0;
                    edgeAdjacent = p.getEdges().get(edgeAdjacentIndex);
                }

                Point2Dimentional vertex = null;
                if (previous){
                    vertex = currentEdge.getBegin();
                }
                else{
                    vertex = currentEdge.getEnd();
                }

                //rotate line by setting crosspoint translation
                //if line crosses both edges then +2 to left croses value ~ the same as +0 so do nothing
                //if line crosses only one edge now then +1 to left crosses

                /*
                * Consider a point (vertex.x, vertex.y - EPS)
                * Draw line through out point Z and this point.
                * This line crosses an edge only if ends of edges lie in different planes
                * formed by line.
                * Line equation: F(x,y) = (y1-y2)x + (x2-x1)y + (x1y2 - x2y1) = 0
                * Points lie in different plains only if F(x,y) values has opposite signs.
                 */

                if (edgeCrossesLine(currentEdge, z, new Point2Dimentional(vertex.getX(), vertex.getY() - Constants.EPS)) &&
                        edgeCrossesLine(edgeAdjacent, z, new Point2Dimentional(vertex.getX(), vertex.getY() - Constants.EPS))){
                    leftCrossings += 2; //case must be filtered but no changes have to be done
                }
                else if(edgeCrossesLine(currentEdge, z, new Point2Dimentional(vertex.getX(), vertex.getY() - Constants.EPS)) ||
                        edgeCrossesLine(edgeAdjacent, z, new Point2Dimentional(vertex.getX(), vertex.getY() - Constants.EPS))){
                    leftCrossings += 1;
                }

                //and never more check this edges
                checkedEdges[edgeAdjacentIndex] = true;
            }

            checkedEdges[i] = true;
        }

        if (leftCrossings%2 == 1){
            return true;
        }
        else return false;
    }

    /*
    * line is defined by two points b and e.
    */
    public static boolean edgeCrossesLine(Edge2Dimentional edge, Point2Dimentional b, Point2Dimentional e){
        double bSign = Math.signum((b.getY() - e.getY()) * edge.getBegin().getX() + (e.getX() - b.getX()) * edge.getBegin().getY() +
                (b.getX() * e.getY() - e.getX() * b.getY()));
        double eSign = Math.signum((b.getY() - e.getY()) * edge.getEnd().getX() + (e.getX() - b.getX()) * edge.getEnd().getY() +
                (b.getX() * e.getY() - e.getX() * b.getY()));

        if(bSign * eSign <= 0) {
            return true;
        }
        return false;
    }
}
