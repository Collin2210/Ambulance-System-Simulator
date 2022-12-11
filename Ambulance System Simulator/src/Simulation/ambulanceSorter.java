package Simulation;

import java.util.Comparator;

public class ambulanceSorter implements Comparator<Ambulance> {

    Point patientPosition;

    public ambulanceSorter(Point patientPos) {
        patientPosition = patientPos;
    }

    @Override
    public int compare(Ambulance a1, Ambulance a2) {
        double distToA1 = patientPosition.manhattanDistance(a1.getCurrentPosition());
        double distToA2 = patientPosition.manhattanDistance(a2.getCurrentPosition());
        return (int) (distToA1 - distToA2);
    }

}
