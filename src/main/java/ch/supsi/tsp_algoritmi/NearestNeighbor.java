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
    public static int computeNearest(List<City> cities, int[][] distanceMatrix) {

        int totalDistance = 0;

        List<City> visitedCities = new ArrayList<>();

        int index = 0;

        int newMinIndex = 0;
        int current = 56;
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
            System.out.print(newMinIndex+ " --> ");
            totalDistance += min; //update the total distance

            current = newMinIndex;
            visitedCities.add(cities.get(current)); //add the nearest city to the visited list
            min = Integer.MAX_VALUE; //reset minimum distance to a large number

            index++;
        }

        //add the final distance between the start city and the last city to complete the cycle
        City firstCity = visitedCities.get(0);
        System.out.println(firstCity.getId());
        City lastCity = visitedCities.get(cities.size()-1);
        System.out.println(lastCity.getId());

        totalDistance += City.getDistance(firstCity, lastCity);

        return totalDistance;
    }

}
