package ch.supsi.tsp_algoritmi;

import java.io.*;
import java.util.*;

public class Main {
    private static final String FILENAME = "/Users/pavo/Documents/Scuola/SUPSI/3_Anno/6_Semestre/Algoritmi_avanzati/TSP_Algoritmi/results.txt";

    public static void main(String[] args) {
        //DA FARE:  creare classe che contiene tutte le info generiche necessarie agli algoritmi

        System.out.println("TSP_ALGORITMI PROJECT");
        System.out.println();

        //Parse the file & load the data into a list of cities
        ClassLoader classLoader = Main.class.getClassLoader();
        File file = new File(classLoader.getResource("ch130.tsp").getFile()) ;

        TSPParser tspParser = new TSPParser(file);
        List<City> cityList = tspParser.parse();

        //compute the distanceMatrix;
        int[][] distanceMatrix = City.getDistanceMatrix(cityList);

        City[] cities = cityList.toArray(new City[0]);

        CandidateListPopulator candidateListPopulator = new CandidateListPopulator(distanceMatrix);
        candidateListPopulator.populateCandidateLists(cities);

        MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(distanceMatrix);
        minimumSpanningTree.compute(cities);

        System.out.println();
        for(int city: cities[0].getCandidateList()){
            System.out.println(city);
        }

        /*
            compute a valid route with the Nearest Neighbor algorithm:
         */
        City[] nearestRoute = new NearestNeighbor(cityList).compute(distanceMatrix);
        System.out.println("NEAREST NEIGHBOR HAS TERMINATED -> "+tspParser.getPercentError(City.getRouteDistanceArray(nearestRoute))+"%");

        /*
            compute the 2-Opt structural algorithm to eliminate "crossed" links:
         */
        TwoOpt twoOpt = new TwoOpt(distanceMatrix);
        City[] nearestPlusTwoOptRoute = twoOpt.computeOptimization(nearestRoute);
        System.out.println(tspParser.getPercentError(City.getRouteDistanceArray(nearestPlusTwoOptRoute)));
        System.out.println("TWO OPT HAS TERMINATED -> "+tspParser.getPercentError(City.getRouteDistanceArray(nearestRoute))+"%");

        /*
            Once a valid initial route has been computed (Nearest Neighbor) and its structure improved with a optimizer,
            apply the Simulated Annealing algorithm.
         */
        int counter = 0;

        long currentBestSeed = 0;
        double currentBestError = 100.0;
        double currentBestAlpha = 0;
        int currentBestTemperature = 0;

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(FILENAME, true);

            BufferedWriter printWriter = new BufferedWriter(fileWriter);
            printWriter.write("Line Added on: " + new java.util.Date()+"\n\n");
            printWriter.flush();

            while (counter < 50) {
                long seed = System.currentTimeMillis();

                SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(seed, twoOpt);
                City[] bestRoute = simulatedAnnealing.simulatedAnnealing(nearestPlusTwoOptRoute);
                double error = tspParser.getPercentError(City.getRouteDistanceArray(bestRoute));
                System.out.println("SIMULATED ANNEALING HAS TERMINATED -> " + error + "%");

                List<City> list = new ArrayList<>(Arrays.asList(bestRoute));
                Set<City> set = new HashSet<>(list);

                if (set.size() == list.size()) {
                    System.out.println("Simulated Annealing computed a valid solution!");

                    if (error < currentBestError) {
                        printWriter.write("File: "+ tspParser.getFileName() + "\n");
                        printWriter.write("Best known distance: " + tspParser.getBestKnown() + "\n");
                        printWriter.write("Best distance calculated: "+City.getRouteDistanceArray(bestRoute) + "\n");
                        printWriter.write("Error is: " + tspParser.getPercentError(City.getRouteDistanceArray(bestRoute)) + "%\n");
                        printWriter.write("Temperature: " + simulatedAnnealing.getTemperature() + "\n");
                        printWriter.write("Alpha: " + simulatedAnnealing.getAlpha() + "\n");
                        printWriter.write("Seed: " + seed + "\n");
                        printWriter.write("\n");
                        printWriter.flush();
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

            printWriter.close();

        } catch (IOException e) {
            System.err.println("Error while writing to file: " +
                    e.getMessage());
        }

        System.out.println();
        System.out.println();
        System.out.println(currentBestSeed + " / " + currentBestError + "% / " + currentBestAlpha + " / " + currentBestTemperature);
        //compute the Simulated Annealing algorithm to try to find the optimal solution
        //show the error in percent
    }
}
