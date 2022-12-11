package Simulation;

import java.util.ArrayList;

public class City {

    ArrayList<Region> regions;

    Queue queue;
    ProductAcceptor sink;
    CEventList eventList;

    static final Point hospitalPosition = new Point(0, 0);

    static final double radius = 5;
    static final double side = radius;
    static final double height = side * Math.sqrt(3) / 2;

    public City(Queue queue, ProductAcceptor sink, CEventList eventList) {
        regions = new ArrayList<>();
        this.queue = queue;
        this.sink = sink;
        this.eventList = eventList;

        // create regions
        for (Point dock : getDockPositions())
            regions.add(new Region(dock, this));
    }

    public static Point[] getDockPositions() {
        double oX = hospitalPosition.getX(),
                oY = hospitalPosition.getY();

        return new Point[] {
                new Point(oX, oY),
                new Point(oX + radius + side / 2, oY + height),
                new Point(oX + radius + side / 2, oY - height),
                new Point(oX + 0, oY - 2 * height),
                new Point(oX - (radius + side / 2), oY - height),
                new Point(oX - (radius + side / 2), oY + height),
                new Point(oX + 0, oY + 2 * height)
        };
    }
}
