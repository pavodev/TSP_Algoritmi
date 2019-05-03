package ch.supsi.tsp_algoritmi;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String FILENAME = "/Users/pavo/Documents/Scuola/SUPSI/3_Anno/6_Semestre/Algoritmi_avanzati/TSP_Algoritmi/results.txt";

    public static void main(String[] args) {
    }

    public static void start(String fileName, long seed){

        long start = System.nanoTime();

        //Parse the file & load the data into a list of cities
        ClassLoader classLoader = Main.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName+ ".tsp").getFile());

        System.out.println(fileName);

        TSPParser tspParser = new TSPParser(file);
        List<City> cityList = tspParser.parse();

        //compute the distanceMatrix;
        int[][] distanceMatrix = City.getDistanceMatrix(cityList);

        City[] cities = cityList.toArray(new City[0]);

        /*
            compute the candidate list for every city
         */
        CandidateListPopulator candidateListPopulator = new CandidateListPopulator(distanceMatrix);
        candidateListPopulator.populateCandidateLists(cities);

        /*
            compute a minimum spanning tree for every city
         */
        MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(distanceMatrix);
        minimumSpanningTree.compute(cities);

        /*
            compute a valid route with the Nearest Neighbor algorithm:
         */
        City[] nearestRoute = new NearestNeighbor(cityList).compute(distanceMatrix);

        //Update positions
        int[] positions = new int[nearestRoute.length];

        for (int x = 0; x < nearestRoute.length; x++) {
            for (int y = 0; y < nearestRoute.length; y++) {
                if (x == nearestRoute[y].getId()) {
                    positions[x] = y;
                    break;
                }
            }
        }

        /*
            compute the 2-Opt structural algorithm to eliminate "crossed" links:
         */
        TwoOpt twoOpt = new TwoOpt(distanceMatrix, positions);
        City[] nearestPlusTwoOptRoute = twoOpt.computeOptimization(nearestRoute);

        /*
            Once a valid initial route has been computed (Nearest Neighbor) and its structure improved with a optimizer,
            apply the Simulated Annealing algorithm.
         */
        long elapsed = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start);
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(seed, twoOpt, positions, elapsed);
        City[] bestRoute = simulatedAnnealing.simulatedAnnealing(nearestPlusTwoOptRoute);

        System.out.println("Best computed: " + City.getRouteDistanceArray(bestRoute));
        System.out.println("Error: " + tspParser.getPercentError(City.getRouteDistanceArray(bestRoute)));
        ResultWriter.writeResults(fileName, bestRoute);

        System.out.println();
    }
}
