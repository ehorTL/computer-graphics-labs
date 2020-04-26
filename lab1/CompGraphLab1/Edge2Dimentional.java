import jdk.vm.ci.meta.Constant;

import java.lang.reflect.Type;

public class Edge2Dimentional {
    private Point2Dimentional begin;
    private Point2Dimentional end;

    public Edge2Dimentional(Point2Dimentional begin, Point2Dimentional end) throws Exception {
        if (begin.equals(end)){
            final String message = "Points are equals!";
            throw new Exception(message);
        }
        this.begin = begin;
        this.end = end;
    }

    public TypeOfEdgeWithLineCrossing isCrossedByHorizontalLineWithOrdinate(double y){
        if (Math.abs(y - begin.getY()) < Constants.EPS || Math.abs(y - end.getY()) < Constants.EPS){
            return TypeOfEdgeWithLineCrossing.CROSS_IN_VERTEX;
        }
        else if ((y >= begin.getY() && y <=end.getY()) || (y <= begin.getY() && y >= end.getY())){
            return TypeOfEdgeWithLineCrossing.CROSS;
        }
        else return TypeOfEdgeWithLineCrossing.NO_CROSS;
    }

    public double getXOfCrossingEdgeWithHorizontalLineThroughPoint(double y){
        return (begin.getX() + ((y - begin.getY()) * (end.getX()- begin.getX())) / (end.getY() - begin.getY()));
    }

    public Point2Dimentional getBegin(){
        return begin;
    }

    public Point2Dimentional getEnd(){
        return end;
    }

    /*
    Suppose we have a line segment P1P2 and some point P on a plane.
    P lies on the P1P2 only if 2 conditions satisfied:
    1. P1P and P1P2 are collinear that is (x-x1)(y2-y1)-(y-y1)(x2-x1) = 0.
    2. x1<x<x2 or x2<x<x1
    * */
    public boolean hasPoint(Point2Dimentional point) {
        double x = point.getX();
        double y = point.getY();
        double x1 = begin.getX();
        double x2 = end.getX();
        double y1 = begin.getY();
        double y2 = end.getY();

        if (Math.abs((x-x1)*(y2-y1)-(y-y1)*(x2-x1)) <= Constants.EPS){
            if ((x1 <= x && x <= x2) || (x2 <= x && x <= x1)){
                System.out.println("contains!");
                return true;
            }
        }
        return false;
    }
}





