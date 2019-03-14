package ch.supsi.tsp_algoritmi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TSPParser {
    private static double bestKnown;

    public static List<City> parse(File tspFile) {
        if(tspFile == null)
            return null;

        List<City> cities = new ArrayList<City>();

        try {

            BufferedReader in = new BufferedReader(new FileReader(tspFile));
            String line;
            line = in.readLine();

            while(!(line.contains("BEST"))){
                line = in.readLine();
            }

            bestKnown = Double.parseDouble(line.split(" ")[2]);

            while(!(line.split(" ")[0].equals("1"))){
                line = in.readLine();
            }

            while(!line.equals("EOF")){
                cities.add(new City(
                                Integer.parseInt(line.split(" ")[0]),
                                Double.parseDouble(line.split(" ")[1]),
                                Double.parseDouble(line.split(" ")[2])
                        )
                );

                line = in.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }

    public static void printPercentError(double computedDistance){
        double error = (Math.abs(bestKnown-computedDistance)/bestKnown) * 100.0;

        System.out.println("Error(%): " + error);
    }
}
