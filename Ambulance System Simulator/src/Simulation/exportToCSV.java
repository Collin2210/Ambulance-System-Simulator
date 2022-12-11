package Simulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

}
