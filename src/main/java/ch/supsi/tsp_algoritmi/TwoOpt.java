package ch.supsi.tsp_algoritmi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TwoOpt {

    public static City[] twoOpt(List<City> route, int[][] distanceMatrix){
        System.out.println("\n****************************** 2-OPT ALGORITHM *******************************");

        City[] routeToArray = route.toArray(new City[0]);

        System.out.println("Initial route: " + route);
        City[] bestRoute = routeToArray.clone();


        int bestGain = -1;
        int best_i = 0;
        int best_j = 0;
        int gain;
        int swap = 0;
        int better = 0;

        while(bestGain<0){
            bestGain = 0;

            for (int i = 0 ; i < routeToArray.length; i++) {
                for (int j = i + 1; j < routeToArray.length; j++) {
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
        }

        System.out.println("Total swaps: " + swap);
        System.out.println("Better found: " + better);

        System.out.println("\n*****************************************************************************");

        return bestRoute;
    }

    /*
        This method swaps two route with the given i and j indexes and returns the new city list
     */
    private static City[] swap(City[] route, int i, int j) {

        System.out.println("i="+i + "j="+j);

        int h = 0;
        for(int k = 0; k <= (j-i)/2; k++){
            City temp = route[i + h];
            route[i + h] = route[j - h];
            route[j - h] = temp;

            h++;
        }

        return route;
    }

    private static int routeIsBetter(City[] route, int i, int j){
        int a;
        int b;

        if(i==0)
            a = route.length-1;
        else
            a = i - 1;


        if(j == route.length)
            b = 0;
        else
            b = j - 1;

        int A = City.getDistance(route[i], route[a]) + City.getDistance(route[j], route[b]);
        int B = City.getDistance(route[i], route[j]) + City.getDistance(route[a], route[b]);
        return  B-A;
    }
}

//va invertito il percorso tra l'indice i e l'indice j