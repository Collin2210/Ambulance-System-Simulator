/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

import java.io.IOException;
import java.util.ArrayList;

public class Simulation {

    public static final double MAX_SIM_TIME = 24 * 60; // 24h
    public static ArrayList<String> prints = new ArrayList<>();

    /**
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        for (int numOfAmbulances = 1; numOfAmbulances < 6; numOfAmbulances++) {
            System.out.println("\n= = = = = =\nNumber of ambulances: " + numOfAmbulances);
            String csvName = "data" + numOfAmbulances;
            Region.numAmbulances = numOfAmbulances;
            runSimulation(csvName);
        }

    }

    public static void runSimulation(String csvName) throws IOException {
        // An eventlist
        CEventList eventList = new CEventList();

        // A queue for the machine
        Queue queue = new Queue();

        // A source for each priority level
        /**
         * Priority level bytes:
         * A1 = 1,
         * A2 = 2,
         * B = 3
         */

        new Source(queue, eventList, "Source A1", (byte) 1);
        new Source(queue, eventList, "Source A2", (byte) 2);
        new Source(queue, eventList, "Source B", (byte) 3);

        // A sink
        Sink sink = new Sink("Sink 1");

        // The machines aka the ambulances
        new City(queue, sink, eventList);

        // start the eventlist
        eventList.start(MAX_SIM_TIME); // 2000 is maximum time

        exportToCSV.exportArraysToCSV(
                sink.getEvents(),
                sink.getTimes(),
                sink.getNumbers(),
                sink.getStations(),
                csvName + ".csv");

        exportToCSV.getWaitingTimes(sink);

        AmbulanceScheduler.scheduleAmbulances(7);
    }
}
