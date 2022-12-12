package Simulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class exportToCSV {
    public static void exportArraysToCSV(String[] stringArray, double[] doubleArray, int[] js, String[] strings,
            String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            // column names
            writer.write("Event");
            writer.write(",");
            writer.write("Time");
            writer.write(",");
            writer.write("Number");
            writer.write(",");
            writer.write("Station");
            writer.newLine();

            for (int i = 0; i < stringArray.length; i++) {
                writer.write(stringArray[i]);
                writer.write(",");
                writer.write(String.valueOf(doubleArray[i]));
                writer.write(",");
                writer.write(String.valueOf(js[i]));
                writer.write(",");
                writer.write(strings[i]);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getWaitingTimes(Sink sink) {
        final DecimalFormat df = new DecimalFormat("0.00");

        // Shifts 8-4-4-8
        // int[] shift_times = new int[] { 0, 60 * 8, 60 * 8 + 60 * 4, 60 * 8 + 60 * 4 +
        // 60 * 4,
        // 60 * 8 + 60 * 4 + 60 * 4 + 60 * 8 };

        // Shifts 4-4-4-4-4-4
        int[] shift_times = new int[] { 0, 60 * 4, (60 * 4) * 2, (60 * 4) * 3, (60 * 4) * 4, (60 * 4) * 5,
                (60 * 4) * 6 };

        // create the datasets according to the shifts and their creation time
        for (int i = 0; i < shift_times.length - 1; i++) {
            System.out.println();
            ArrayList<Double> dataset_waiting_time = new ArrayList<>();
            ArrayList<Integer> dataset_priority = new ArrayList<>();

            // Loop over whole dataset and create the waiting time dataset
            for (int j = 0; j < sink.getTimes().length; j++) {
                if (shift_times[i] < sink.getTimes()[j] && sink.getTimes()[j] < shift_times[i + 1]) {
                    // Third check if creation time was in the shift
                    if (sink.getEvents()[j].equals("Creation")) {

                        // Add the priority to the dataset according to the creation time
                        switch (sink.getStations()[j]) { // Creation
                            case "Source A1" -> dataset_priority.add(1);
                            case "Source A2" -> dataset_priority.add(2);
                            case "Source B" -> dataset_priority.add(3);
                        }

                        // Now we have the position j of the creation time that is in the shift
                        // waiting time is always 3 down
                        dataset_waiting_time.add(sink.getTimes()[j + 3]);
                    }
                }
            }

            System.out
                    .println("Shift " + (i + 1) + " : \nAverage waiting time A1: " + getAVGWaitingTime(
                            dataset_waiting_time, dataset_priority, 1));
            System.out
                    .println("Average waiting time A2: " + getAVGWaitingTime(
                            dataset_waiting_time, dataset_priority, 2));
            System.out
                    .println(
                            "Average waiting time B: " + getAVGWaitingTime(
                                    dataset_waiting_time, dataset_priority, 3));
            System.out.println(
                    "Percentage A1 priority: " + df.format(
                            getPercentage(dataset_waiting_time, dataset_priority) * 100) + "%");
        }
        System.out.println("\n");
    }

    public static double getAverage(ArrayList<Double> l) {
        double sum = 0;
        for (double d : l)
            sum += d;
        return sum / l.size();
    }

    public static double getAVGWaitingTime(ArrayList<Double> times, ArrayList<Integer> priority, int typepriority) {
        ArrayList<Double> A1Times = new ArrayList<Double>();

        for (int i = 0; i < times.size(); i++) {
            if (priority.get(i) == typepriority) {
                A1Times.add(times.get(i));
            }
        }
        return getAverage(A1Times);
    }

    public static double getPercentage(ArrayList<Double> times, ArrayList<Integer> priority) {
        ArrayList<Double> A1Times = new ArrayList<Double>();

        for (int i = 0; i < times.size(); i++) {
            if (priority.get(i) == 1) {
                A1Times.add(times.get(i));
            }
        }

        int numOverLimit = 0;
        for (double d : A1Times) {
            if (d > 15)
                numOverLimit++;
        }

        return (double) numOverLimit / (A1Times.size());
    }
}
