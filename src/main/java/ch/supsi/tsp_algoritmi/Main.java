package ch.supsi.tsp_algoritmi;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("TSP_ALGORITMI PROJECT");
        System.out.println();

        //Read the file & load the data into a list of cities
        ClassLoader classLoader = new Main().getClass().getClassLoader();
        File file = new File(classLoader.getResource("ch130.tsp").getFile()) ;

        List<City> cityList = TSPParser.parse(file);

        //compute the distanceMatrix;
        int[][] distanceMatrix = City.getDistanceMatrix(cityList);

        City.printDistanceMatrix(cityList, distanceMatrix);
        //System.out.println(distanceMatrix[40][38] + ", " + distanceMatrix[40][49]);
        //compute a valid route with the Nearest Neighbor algorithm:
        List<City> nearestRoute = NearestNeighbor.computeNearest(cityList, distanceMatrix);

        //compute the 2-Opt structural algorithm to reorder the route:
        City[] nearestPlusTwoOptRoute = TwoOpt.twoOpt(nearestRoute, distanceMatrix);

        //show the error in percent:
        TSPParser.printPercentError(City.getRouteDistance(nearestRoute));
        TSPParser.printPercentError(City.getRouteDistanceArray(nearestPlusTwoOptRoute));
    }
}
