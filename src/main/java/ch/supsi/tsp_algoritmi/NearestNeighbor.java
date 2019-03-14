package ch.supsi.tsp_algoritmi;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighbor {

    /*
    This method computes the nearest neighbor algorithm.

    Parameters:
        - A list of cities
        - The distanceMatrix of the list of cities

    Returns:
        - the totalDistance
     */
    public static List<City> computeNearest(List<City> cities, int[][] distanceMatrix) {
        System.out.println("\n************************* NEAREST NEIGHBOR ALGORITHM *************************");

        int totalDistance = 0;

        List<City> visitedCities = new ArrayList<>();

        int index = 0;

        int newMinIndex = 0;
        int current = 0;
        int min = Integer.MAX_VALUE;

        //add the initial city to the visited cities list
        visitedCities.add(cities.get(current));

        while (index < cities.size()-1) {
            for (int i = 0; i < cities.size(); i++) {
                if (i != current && distanceMatrix[current][i] < min && !visitedCities.contains(cities.get(i))) {
                    newMinIndex = i;
                    min = distanceMatrix[current][i];
                }
            }
            totalDistance += min; //update the total distance

            current = newMinIndex;
            visitedCities.add(cities.get(current)); //add the nearest city to the visited list
            min = Integer.MAX_VALUE; //reset minimum distance to a large number

            index++;
        }

        //add the final distance between the start city and the last city to complete the cycle
        City firstCity = visitedCities.get(0);
        City lastCity = visitedCities.get(cities.size()-1);

        totalDistance += City.getDistance(firstCity, lastCity);

        System.out.println("Total distance: " + totalDistance);
        System.out.println("******************************************************************************\n");

        return visitedCities;
    }

}
