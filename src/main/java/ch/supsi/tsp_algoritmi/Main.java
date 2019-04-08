package ch.supsi.tsp_algoritmi;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("TSP_ALGORITMI PROJECT");
        System.out.println();

        //Read the file & load the data into a list of cities
        ClassLoader classLoader = Main.class.getClassLoader();
        File file = new File(classLoader.getResource("u1060.tsp").getFile()) ;

        List<City> cityList = TSPParser.parse(file);

        //compute the distanceMatrix;
        int[][] distanceMatrix = City.getDistanceMatrix(cityList);
//
//        int[] numbers = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
//        int[] bridgeNumbers = SimulatedAnnealing.doubleBridge(numbers);
//
//        System.out.println("After bridge");
//        for(int number: bridgeNumbers){
//            System.out.print(number + " ");
//        }
        //compute a valid route with the Nearest Neighbor algorithm:
        City[] nearestRoute = NearestNeighbor.computeNearest(cityList, distanceMatrix);

        System.out.println("NEAREST NEIGHBOR HAS TERMINATED");

        //compute the 2-Opt structural algorithm to eliminate "crossed" links:
        City[] nearestPlusTwoOptRoute = TwoOpt.twoOpt(nearestRoute);

        /*
        Once a valid initial route has been computed (Nearest Neighbor) and its structure improved,
        apply the Simulated Annealing algorithm.
         */

        System.out.println("TWO OPT HAS TERMINATED");

        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(346357432343546L);

        City[] bestRoute = simulatedAnnealing.simulatedAnnealing(nearestPlusTwoOptRoute);

        List<City> list = new ArrayList<>(Arrays.asList(bestRoute));
        Set<City> set = new HashSet<>(list);

        if(set.size() == list.size()){
            System.out.println("Simulated Annealing computed a valid solution!");
        }
        //compute the Simulated Annealing algorithm to try to find the optimal solution

        //show the error in percent:
        TSPParser.printPercentError(City.getRouteDistanceArray(nearestRoute));
        TSPParser.printPercentError(City.getRouteDistanceArray(nearestPlusTwoOptRoute));
        TSPParser.printPercentError(City.getRouteDistanceArray(bestRoute));
    }
}
