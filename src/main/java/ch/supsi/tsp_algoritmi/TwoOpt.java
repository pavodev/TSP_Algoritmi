package ch.supsi.tsp_algoritmi;

class TwoOpt {
    /*
        Compute the 2-Opt algorithm.
    */
    static City[] twoOpt(City[] route, int[][] distanceMatrix){
        System.out.println("\n****************************** 2-OPT ALGORITHM *******************************");

        City[] bestRoute = route.clone();

        int bestGain = -1;
        int best_i = 0;
        int best_j = 0;
        int gain;
        int swap = 0;

        while(bestGain<0){
            bestGain = 0;
            for (int i = 0 ; i < route.length; i++) {
                for (int j = i + 1; j < route.length; j++) {
                    //check if distance AB + CD >= distance AC + BD
                    gain=routeIsBetter(bestRoute, i, j);

                    //compute the gain and update the reference variables
                    if(gain < bestGain){
                        bestGain = gain;
                        best_j = j-1;
                        best_i = i;
                    }
                }
            }
            if(bestGain < 0) {
                swap(bestRoute, best_i, best_j);
                swap++;
            }
        }

        System.out.println("Total swaps: " + swap);
        System.out.print("Best distance: " + City.getRouteDistanceArray(bestRoute));
        System.out.println("\n******************************************************************************");

        return bestRoute;
    }

    /*
        Swap the given i and j indexes and everything that stays between the two.
    */
    private static void swap(City[] route, int i, int j) {
        int h = 0;
        for(int k = 0; k <= (j-i)/2; k++){
            City temp = route[i + h];
            route[i + h] = route[j - h];
            route[j - h] = temp;

            h++;
        }
    }

    /*
        Check if the distance between 2 nodes is better if swapped and return the gain.
    */
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

        //compute the distance without swapped indexes
        int A = City.getDistance(route[i], route[a]) + City.getDistance(route[j], route[b]);
        //Compute the distance of swapped indexes
        int B = City.getDistance(route[i], route[j]) + City.getDistance(route[a], route[b]);

        return  B-A;
    }
}