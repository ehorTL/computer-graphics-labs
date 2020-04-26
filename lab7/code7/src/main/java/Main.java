import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static void main(String args[]) throws FileNotFoundException {
        try (Scanner sc = new Scanner(System.in)) {
            ArrayList<Point> points = Preparat.inputDatas("input.txt");

            ArrayList<Point> p = Preparat.preperat(points);
            System.out.println("\n\nTHE POINTS IN THE HULL:");
            for (int i = 0; i < p.size(); i++) {
                System.out.println(p.get(i).x + " " + p.get(i).y);
            }
        }
    }
}