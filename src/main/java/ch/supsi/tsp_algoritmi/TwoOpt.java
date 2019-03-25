package ch.supsi.tsp_algoritmi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TwoOpt {

    public static List<City> twoOpt(List<City> route){
        System.out.println("\n****************************** 2-OPT ALGORITHM *******************************");

        System.out.println("Initial route: " + route);
        List<City> bestRoute = new ArrayList<>(route);
        List<City> newRoute = new ArrayList<>();

        int bestGain = -1;
        int best_i = 0;
        int best_j = 0;
        int gain;
        int newDistance = 0;
        int bestDistance = City.getRouteDistance(route);
        int swap = 0;
        int better = 0;

        while(bestGain<0){
            bestGain = 0;

            for (int i = 0 ; i < route.size(); i++) {
                for (int j = i + 2; j < route.size(); j++) {
                    //check if distance AB + CD >= distance AC + BD
                    gain=routeIsBetter(bestRoute, i, j);

                    //compute the gain and update the reference variables
                    if(gain < bestGain){
                        better++;
                        bestGain = gain;
                        best_j = j-1;
                        best_i = i;
                    }
                }

            }

            if(bestGain < 0) {
                bestRoute = swap(bestRoute, best_i, best_j);
                swap++;
            }
            System.out.println(bestRoute);
            System.out.println("Best distance: " + City.getRouteDistance(bestRoute));
        }

        System.out.println("Total swaps: " + swap);
        System.out.println("Better found: " + better);
        System.out.println("Best route: " + bestRoute);

        System.out.println("\n*****************************************************************************");
        return bestRoute;
    }

    /*
        This method swaps two route with the given i and j indexes and returns the new city list
     */
    private static List<City> swap(List<City> route, int i, int j) {
        System.out.println("i="+i + "j="+j);
        List<City> swappedRoute = new ArrayList<>();

        //Add all the route before the 'i' index to the final list
        int size = route.size();
        for (int k = 0; k <= i - 1; k++) {
            swappedRoute.add(route.get(k));
        }

        swappedRoute.add(route.get(j));

        //Swap the route: add the 'j' city before the 'i' city
        for(int k = j-1; k>i; k--){
            swappedRoute.add(route.get(k));
        }

        swappedRoute.add(route.get(i));

        //Add the remaining route
        for (int k = j + 1; k < size; k++) {
            swappedRoute.add(route.get(k));
        }

        return swappedRoute;
    }

    private static int routeIsBetter(List<City> route, int i, int j){
        int a;
        int b;

        if(i==0)
            a = route.size()-1;
        else
            a = i - 1;


        if(j == route.size())
            b = 0;
        else
            b = j - 1;

        int A = ( City.getDistance(route.get(i), route.get(a)) + City.getDistance(route.get(j),route.get(b)) );
        int B = ( City.getDistance(route.get(i), route.get(j)) + City.getDistance(route.get(a), route.get(b)) );
        return  B-A;
    }
}

//va invertito il percorso tra l'indice i e l'indice j