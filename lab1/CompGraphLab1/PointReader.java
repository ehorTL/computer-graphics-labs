import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/*
(x,y) - point
x,y - point
*/

public class PointReader {
    private ArrayList<Point2Dimentional> pointList;

    public PointReader(String filepath){
        pointList = new ArrayList<>();
        String []xy;
        try{
            Scanner scanner = new Scanner(new File(filepath));
            while(scanner.hasNext()){
                String s = scanner.nextLine();
                xy = s.split("[)(,]");
                pointList.add(new Point2Dimentional(Double.parseDouble(xy[1]), Double.parseDouble(xy[2])));
                System.out.println(new Point2Dimentional(Double.parseDouble(xy[1]), Double.parseDouble(xy[2])));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Point2Dimentional> getPoints(){
        return pointList;
    }
}
