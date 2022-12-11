package Simulation;

import java.util.ArrayList;

public class City {

    ArrayList<Region> regions;

    Queue queue;
    ProductAcceptor sink;
    CEventList eventList;


    static final double[] hospitalPosition = new double[]{0,0};

    static final double radius = 5;
    static final double side = radius;
    static final double height = side * Math.sqrt(3) / 2;

    public City(Queue queue, ProductAcceptor sink, CEventList eventList) {
        regions = new ArrayList<>();
        this.queue = queue;
        this.sink = sink;
        this.eventList = eventList;

        // create regions
        for(double[] dock : getDockPositions())
            regions.add(new Region(dock));
    }

    private double[][] getDockPositions(){
        double
                oX = hospitalPosition[0],
                oY = hospitalPosition[1];

        return new double[][]{
                {oX, oY},
                {oX + radius + side / 2, oY + height},
                {oX + radius + side / 2, oY - height},
                {oX + 0, oY - 2 * height},
                {oX - (radius + side / 2), oY - height},
                {oX - (radius + side /2), oY + height},
                {oX + 0, oY + 2 * height}
        };
    }

    class Region{

        static final int numAmbulances = 5;
        ArrayList<Ambulance> ambulances;
        double[] dockPosition;

        public Region(double[] dockPosition) {
            ambulances = new ArrayList<>();
            this.dockPosition = dockPosition;

            // create ambulances
            for (int i = 0; i < numAmbulances; i++) {
                String name = "Ambulance " + regions.indexOf(this) + " " + i;
                ambulances.add(new Ambulance(queue, sink, eventList, name));
            }

        }

        /** Renders regions in the region unusable in case of no shifts for example */
        void turnOff(){
            for(Ambulance a: ambulances)
                a.setStatus('b');
        }
    }
}
