package Simulation;

import java.util.ArrayList;

public class City {

    ArrayList<Region> regions;

    Queue queue;
    ProductAcceptor sink;
    CEventList eventList;


    static final Point hospitalPosition = new Point(0,0);

    static final double radius = 5;
    static final double side = radius;
    static final double height = side * Math.sqrt(3) / 2;

    public City(Queue queue, ProductAcceptor sink, CEventList eventList) {
        regions = new ArrayList<>();
        this.queue = queue;
        this.sink = sink;
        this.eventList = eventList;

        // create regions
        for(Point dock : getDockPositions())
            regions.add(new Region(dock, this));
    }

    public static Point[] getDockPositions(){
        double
                oX = hospitalPosition.getX(),
                oY = hospitalPosition.getY();

        double[][] coords = new double[][]{
                {oX, oY},
                {oX + radius + side / 2, oY + height},
                {oX + radius + side / 2, oY - height},
                {oX + 0, oY - 2 * height},
                {oX - (radius + side / 2), oY - height},
                {oX - (radius + side /2), oY + height},
                {oX + 0, oY + 2 * height}
        };

        Point[] points = new Point[7];

        for (int i = 0; i < 7; i++) {
            points[i] = new Point(coords[i][0], coords[i][1]);
        }

        return points;
    }
}
