package ch.supsi.tsp_algoritmi;

import java.util.ArrayList;
import java.util.List;

public class TwoOpt {

    public static List<City> twoOpt(List<City> route){
        System.out.println("\n****************************** 2-OPT ALGORITHM *******************************");
        List<City> newRoute = new ArrayList<>();

        int bestGain = -1;
        int newDistance = 0;
        int bestDistance = City.getRouteDistance(route);
        int gain;

        while(bestGain<0){
            bestGain = 0;

            for (int i = 1; i < route.size() - 2; i++) {
                for (int j = i + 1; j < route.size() - 1; j++) {
                    //check if distance AB + CD >= distance AC + BD
                    if(routeIsBetter(route, i, j)){
                        newRoute = swap(route, i, j);
                        newDistance = City.getRouteDistance(newRoute);

                        //compute the gain and update the reference variables
                        if(newDistance < bestDistance){
                            route = newRoute;
                            bestDistance = newDistance;
                        }
                    }
                }
            }
        }

        System.out.println("Best distance: " + City.getRouteDistance(newRoute));
        System.out.println("******************************************************************************\n");

        return newRoute;
    }

    /*
        This method swaps two route with the given i and j indexes and returns the new city list
     */
    private static List<City> swap(List<City> route, int i, int j) {
        List<City> swappedRoute = new ArrayList<>();

        //Add all the route before the 'i' index to the final list
        int size = route.size();
        for (int k = 0; k <= i - 1; k++) {
            swappedRoute.add(route.get(k));
        }

        //Swap the route: add the 'j' city before the 'i' city
        int reverseCount = 0;
        for (int k = i; k <= j; k++) {
            swappedRoute.add(route.get(j - reverseCount));
            reverseCount++;
        }

        //Add the remaining route
        for (int k = j + 1; k < size; k++) {
            swappedRoute.add(route.get(k));
        }

        return swappedRoute;
    }

    private static boolean routeIsBetter(List<City> route, int i, int j){
        int A = ( City.getDistance(route.get(i), route.get(i - 1)) + City.getDistance(route.get(j + 1),route.get(j)) );
        int B = ( City.getDistance(route.get(i), route.get(j + 1)) + City.getDistance(route.get(i - 1), route.get(j)) );
        return  A >= B;
    }
}
