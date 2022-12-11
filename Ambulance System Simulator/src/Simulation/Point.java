package Simulation;

import java.util.Random;

class Hexagon {
    public static Point randomPositionInHexagon() {
        Point[] docksRegions = City.getDockPositions();

        // pick a random region
        Random r = new Random();
        int high = docksRegions.length;
        int result = r.nextInt(high);
        Point dockResultRegion = docksRegions[result];

        // generate a random point within the hexagonal region
        double a = 2.5;
        double b = 2.5;
        while (true) {
            double x = r.nextDouble() * 2 * a - a;
            double y = r.nextDouble() * 2 * b - b;
            if (x * x / (a * a) + y * y / (b * b) <= 1) {
                Point k = new Point(x + dockResultRegion.getX(), y + dockResultRegion.getY());
                return k;
            }
        }
    }

    public static int PositionToRegion(double x, double y) {
        Point[] docksRegions = City.getDockPositions();
        Point point = new Point(x, y);

        int winningRegion = 0;
        double winningDistance = 10;
        for (int i = 0; i < docksRegions.length; i++) {
            double distance = docksRegions[i].manhattanDistance(point);
            if (distance < winningDistance) {
                winningDistance = distance;
                winningRegion = i;
            }
        }
        return winningRegion;
    }
}

public class Point {
    // The x and y coordinates of the point
    private double x, y;
    private int region;

    // Constructor to initialize the x and y coordinates
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Constructor to initialize the x and y coordinates of a random point in a
    // region
    public Point() {

        Point r = Hexagon.randomPositionInHexagon();
        this.x = r.getX();
        this.y = r.getY();
        this.region = Hexagon.PositionToRegion(this.x, this.y);

    }

    // Constructor to initialize the x and y coordinates of a random point in a
    // region
    public Point(double[] position) {
        this.x = position[0];
        this.y = position[1];
        this.region = Hexagon.PositionToRegion(this.x, this.y);

    }

    // Method to calculate the Manhattan distance between this point and another
    // point
    public double manhattanDistance(Point other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    public Point addPoint(Point other) {
        return new Point(x + other.getX(), y + other.getY());
    }

    public Point SubstractPoint(Point other) {
        return new Point(x - other.getX(), y - other.getY());
    }

    // Getters for the x and y coordinates
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getRegion() {
        return region;
    }

    public void getCoordsPrinted(int result) {
        System.out.println("Point: (" + x + ", " + y + ") in region: " + result);
    }

    public void getCoordsPrinted() {
        System.out.println("Point: (" + x + ", " + y + ") in Region: " + region);
    }
}