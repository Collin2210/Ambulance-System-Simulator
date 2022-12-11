package Simulation;

import java.util.ArrayList;

public class Region{

    static int numAmbulances = 1;
    ArrayList<Ambulance> ambulances;
    Point dockPosition;
    int regionNumber;

    public Region(Point dockPosition, City city, int regionNumber) {
        ambulances = new ArrayList<>();
        this.dockPosition = new Point(dockPosition.getX(), dockPosition.getY());
        this.regionNumber = regionNumber;

        // create ambulances
        for (int i = 0; i < numAmbulances; i++) {
            String name = "Ambulance " + regionNumber + " " + (i+1);
            ambulances.add(new Ambulance(city.queue, city.sink, city.eventList, name, dockPosition));
        }
    }

    /** Renders regions in the region unusable in case of no shifts for example */
    void turnOff(){
        for(Ambulance a: ambulances)
            a.setStatus('b');
    }
}
