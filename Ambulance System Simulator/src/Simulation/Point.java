package Simulation;

import java.util.Random;

class Hexagon {
    public static Point randomPositionInHexagon() {
        Random random = new Random();
        double a = 2.5;
        double b = 2.5;
        while (true) {
            double x = random.nextDouble() * 2 * a - a;
            double y = random.nextDouble() * 2 * b - b;
            if (x * x / (a * a) + y * y / (b * b) <= 1) {
                return new Point(x, y);
            }
        }
    }
}

public class Point {
    // The x and y coordinates of the point
    private double x, y;

    // Constructor to initialize the x and y coordinates
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Constructor to initialize the x and y coordinates of a random point in a
    // region
    public Point(Region region) {
        Point r = Hexagon.randomPositionInHexagon();
        this.x = region.dockPosition.getX() + r.getX();
        this.y = region.dockPosition.getY() + r.getY();
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

    public void getCoordsPrinted() {
        System.out.println("Point: (" + x + ", " + y + ")");
    }
}