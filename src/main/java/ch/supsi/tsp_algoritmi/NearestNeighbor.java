package ch.supsi.tsp_algoritmi;

import java.util.Arrays;
import java.util.List;

class NearestNeighbor {

    /*
    This method computes the nearest neighbor algorithm.

    Parameters:
        - A list of cities
        - The distanceMatrix of the list of cities

    Returns:
        - the totalDistance
     */
    static City[] computeNearest(List<City> citiesFromTspFile, int[][] distanceMatrix) {
        System.out.println("\n************************* NEAREST NEIGHBOR ALGORITHM *************************");

        int totalDistance = 0;

        City[] cities = citiesFromTspFile.toArray(new City[0]);

        City[] visitedCities = new City[cities.length];

        int index = 0;
        int newMinIndex = 0;
        int current = 0;
        int min = Integer.MAX_VALUE;

        //add the initial city to the visited cities list
        visitedCities[0] = cities[0];

        while (index < cities.length-1) {
            for (int i = 0; i < cities.length; i++) {
                if (i != current && distanceMatrix[current][i] < min && !Arrays.asList(visitedCities).contains(cities[i])) {
                    newMinIndex = i;
                    min = distanceMatrix[current][i];
                }
            }
            totalDistance += min; //update the total distance

            current = newMinIndex;
            min = Integer.MAX_VALUE; //reset minimum distance to a large number

            index++;

            visitedCities[index] = cities[current]; //add the nearest city to the visited list
        }

        //add the final distance between the start city and the last city to complete the cycle
        City firstCity = visitedCities[0];
        City lastCity = visitedCities[cities.length-1];

        totalDistance += City.getDistance(firstCity, lastCity);

        System.out.println("Total distance: " + totalDistance);
        System.out.print("******************************************************************************\n");

        return visitedCities;
    }

}
