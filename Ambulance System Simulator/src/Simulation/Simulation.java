/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

public class Simulation {

    public static final double MAX_SIM_TIME = 24 * 60; // 24h

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
                "exportedData.csv");
    }
}
