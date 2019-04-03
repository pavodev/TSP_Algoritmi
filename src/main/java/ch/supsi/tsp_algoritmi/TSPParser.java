package ch.supsi.tsp_algoritmi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TSPParser {
    private static double bestKnown;

    public static List<City> parse(File tspFile) {
        if(tspFile == null)
            return null;

        System.out.println(tspFile.getName());
        List<City> cities = new ArrayList<>();

        try {
            String regex = "^\\s+";

            BufferedReader in = new BufferedReader(new FileReader(tspFile));
            String line;
            line = in.readLine();

            while(!(line.contains("BEST"))){
                line = in.readLine();
            }

            bestKnown = Double.parseDouble(line.split(" ")[2]);
            System.out.println("Best known: " + bestKnown);

            while(!(line.split(" ")[0].equals("1"))){
                line = in.readLine();
                line = line.replaceAll(regex, "");
            }

            while(!line.equals("EOF")){
                line = line.replaceAll(regex, "");
                String[] elements = line.split(" ");
                cities.add(new City(
                                Integer.parseInt(elements[0]),
                                Double.parseDouble(elements[1]),
                                Double.parseDouble(elements[2])
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
