package Simulation;

import java.util.ArrayList;

public class Region{

    static final int numAmbulances = 5;
    ArrayList<Ambulance> ambulances;
    Point dockPosition;

    public Region(Point dockPosition, City city) {
        ambulances = new ArrayList<>();
        this.dockPosition = new Point(dockPosition.getX(), dockPosition.getY());

        // create ambulances
        for (int i = 0; i < numAmbulances; i++) {
            String name = "Ambulance " + city.regions.indexOf(this) + " " + i;
            ambulances.add(new Ambulance(city.queue, city.sink, city.eventList, name));
        }

    }

    /** Renders regions in the region unusable in case of no shifts for example */
    void turnOff(){
        for(Ambulance a: ambulances)
            a.setStatus('b');
    }
}
