package Simulation;

import java.util.ArrayList;
import java.util.Arrays;

public class AmbulanceScheduler {
    // The number of hours in a day
    private static final int NUM_HOURS = 24;

    public static double lambdaT(double t, double k) {
        return k * (3 - (2 * Math.sin((Math.PI + t) / 5)) / (6 * Math.PI));
    }

    // The expected number of patients who will need to be picked up at each hour of
    // the day
    public static ArrayList<Double> calculateDemand(ArrayList<Double> rates, double k) {
        // Calculate the expected demand for each hour
        ArrayList<Double> demand = new ArrayList<>();

        for (int i = 0; i < NUM_HOURS; i++) {
            demand.add(lambdaT(rates.get(i), k));
        }

        return demand;
    }

    // A new method that calls the calculateDemand method
    public static void scheduleAmbulances(double k) {
        // Create an ArrayList of rates
        ArrayList<Double> rates = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0,
                12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0));

        // Call the calculateDemand method
        ArrayList<Double> demand = calculateDemand(rates, k);

        // System.out.println("Demand for each hour:");
        // Print the demand for each hour
        double counter = 0;
        for (int i = 0; i < NUM_HOURS; i++) {
            // System.out.println("Hour " + i + ": " + demand.get(i));
            counter += demand.get(i);
        }
        // System.out.println("\nTotal demand: " + counter);

        double shift_1 = 0; // from 7h to 13h
        double shift_2 = 0; // from 13h to 17h
        double shift_3 = 0; // from 17h to 23h
        double shift_4 = 0; // from 23h to 7h

        for (int i = 0; i < NUM_HOURS; i++) {
            if (i < 8)
                shift_1 += demand.get(i);
            else if (i < 12)
                shift_2 += demand.get(i);
            else if (i < 16)
                shift_3 += demand.get(i);
            else
                shift_4 += demand.get(i);
        }
        // System.out.println(
        //         "\nAmount of patient served per shift:\nShift 1: " + shift_1 + "\nShift 2: " + shift_2 + "\nShift 3: "
        //                 + shift_3 + "\nShift 4: " + shift_4);
    }
}
