public class Point2Dimentional {
    private double x;
    private double y;

    public Point2Dimentional(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point2Dimentional(){}

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this){
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        Point2Dimentional p2 = (Point2Dimentional) obj;
        if (Math.abs(this.x - p2.x) < Constants.EPS &&  Math.abs(this.y - p2.y) < Constants.EPS){
            return true;
        }
        else return false;
    }

    @Override
    public String toString() {
        return ("(" + x + "; " + y + ")");
    }
}
