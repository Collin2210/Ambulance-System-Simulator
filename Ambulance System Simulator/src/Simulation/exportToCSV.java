package Simulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class exportToCSV {
    public static void exportArraysToCSV(String[] stringArray, double[] doubleArray, int[] js, String[] strings, String fileName) {
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

    public static void getWaitingTimes(Sink sink){
        // get waiting times
            // get event
        String[] stations = sink.getStations();
        double[] times = sink.getTimes();

        // create storage for waiting times
        ArrayList<ArrayList<Double>> waitingTimes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            waitingTimes.add(new ArrayList<>());
        }

        // separate waiting times from other info
        for (int i = 0; i < stations.length; i++) {
            switch (stations[i]) {
                case "Source A1" -> waitingTimes.get(0).add(times[i+3]);
                case "Source A2" -> waitingTimes.get(1).add(times[i+3]);
                case "Source B" -> waitingTimes.get(2).add(times[i+3]);
            }
        }

//        // print
//        for (ArrayList<Double> l : waitingTimes)
//            System.out.println(l);

        //print averages
        Simulation.prints.add("average waiting time A1: " + getAverage(waitingTimes.get(0)));
        Simulation.prints.add("fraction that were over the 15sec: " + getFraction(waitingTimes.get(0)) + ", " + getPercentage(waitingTimes.get(0)));
        Simulation.prints.add("average waiting time A2: " + getAverage(waitingTimes.get(1)));
        Simulation.prints.add("average waiting time B: " + getAverage(waitingTimes.get(2)));

    }

    public static double getAverage(ArrayList<Double> l){
        double sum = 0;
        for(double d : l)
            sum += d;
        return sum/l.size();
    }

    public static String getFraction(ArrayList<Double> times){
        int numOverLimit = 0;
        for (double d : times){
            if(d > 15)
                numOverLimit++;
        }

        return numOverLimit + "/" + times.size();
    }

    public static double getPercentage(ArrayList<Double> times){
        int numOverLimit = 0;
        for (double d : times){
            if(d > 15)
                numOverLimit++;
        }

        return (double) numOverLimit/(times.size());
    }



}
