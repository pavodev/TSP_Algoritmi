package ch.supsi.tsp_algoritmi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    private static final String FILENAME = "/Users/pavo/Documents/Scuola/SUPSI/3_Anno/6_Semestre/Algoritmi_avanzati/TSP_Algoritmi/results.txt";

    public static void main(String[] args) {
        //DA FARE:  creare classe che contiene tutte le info generiche necessarie agli algoritmi

        System.out.println("TSP_ALGORITMI PROJECT");
        System.out.println();

        //Read the file & load the data into a list of cities
        ClassLoader classLoader = Main.class.getClassLoader();
        File file = new File(classLoader.getResource("rat783.tsp").getFile()) ;

        TSPParser tspParser = new TSPParser(file);
        List<City> cityList = tspParser.parse();

        //compute the distanceMatrix;
        int[][] distanceMatrix = City.getDistanceMatrix(cityList);

        /*
            compute a valid route with the Nearest Neighbor algorithm:
         */
        City[] nearestRoute = new NearestNeighbor(cityList).compute(distanceMatrix);
        System.out.println(tspParser.getPercentError(City.getRouteDistanceArray(nearestRoute)));
        System.out.println("NEAREST NEIGHBOR HAS TERMINATED");

        /*
            compute the 2-Opt structural algorithm to eliminate "crossed" links:
         */
        TwoOpt twoOpt = new TwoOpt(distanceMatrix);
        City[] nearestPlusTwoOptRoute = twoOpt.computeOptimization(nearestRoute);
        System.out.println(tspParser.getPercentError(City.getRouteDistanceArray(nearestPlusTwoOptRoute)));
        System.out.println("TWO OPT HAS TERMINATED");

        /*
            Once a valid initial route has been computed (Nearest Neighbor) and its structure improved with a optimizer,
            apply the Simulated Annealing algorithm.
         */
        int counter = 0;

        long currentBestSeed = 0;
        double currentBestError = 100.0;
        double currentBestAlpha = 0;
        int currentBestTemperature = 0;

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(FILENAME, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(fileWriter!=null) {

            while (counter < 2) {
                long seed = System.currentTimeMillis();

                SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(seed, twoOpt);
                City[] bestRoute = simulatedAnnealing.simulatedAnnealing(nearestPlusTwoOptRoute);
                double error = tspParser.getPercentError(City.getRouteDistanceArray(bestRoute));
                System.out.println("SIMULATED ANNEALING HAS TERMINATED");

                List<City> list = new ArrayList<>(Arrays.asList(bestRoute));
                Set<City> set = new HashSet<>(list);

                if (set.size() == list.size()) {
                    System.out.println("Simulated Annealing computed a valid solution!");

                    if (error < currentBestError) {
                        PrintWriter printWriter = new PrintWriter(fileWriter);
                        printWriter.printf("File: %s \n", tspParser.getFileName());
                        printWriter.printf("Best known distance: %d \n", tspParser.getBestKnown());
                        printWriter.printf("Best distance calculated: %d \n", City.getRouteDistanceArray(bestRoute));
                        printWriter.printf("Error is: %f%s \n", tspParser.getPercentError(City.getRouteDistanceArray(bestRoute)), "%");
                        printWriter.printf("Temperature: %d \n", simulatedAnnealing.getTemperature());
                        printWriter.printf("Alpha: %f \n", simulatedAnnealing.getAlpha());
                        printWriter.printf("Seed: %d \n", seed);
                        printWriter.printf("\n");
                        printWriter.close();
                        currentBestSeed = seed;
                        currentBestError = error;
                        currentBestAlpha = simulatedAnnealing.getAlpha();
                        currentBestTemperature = simulatedAnnealing.getTemperature();
                    }

                }

                System.out.println();
                System.out.println();
                counter++;
            }
        }

        System.out.println();
        System.out.println();
        System.out.println(currentBestSeed + " / " + currentBestError + "% / " + currentBestAlpha + " / " + currentBestTemperature);
        //compute the Simulated Annealing algorithm to try to find the optimal solution

        //show the error in percent
    }
}
