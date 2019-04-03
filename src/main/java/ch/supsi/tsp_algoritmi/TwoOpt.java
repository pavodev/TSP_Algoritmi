package ch.supsi.tsp_algoritmi;


//public class TwoOpt {
//
//    private static int numberOfNodes;
//
//    public static int getGain(City[] tour, int i, int j) {
//        int a = i == 0 ? tour.length - 1 : i - 1;
//        int b = j == tour.length ? 0 : j - 1;
//
//        int dist1 = City.getDistance(tour[i],tour[a]) + City.getDistance(tour[j],tour[b]);
//        int dist2 = City.getDistance(tour[i],tour[j]) + City.getDistance(tour[a],tour[b]);
//
//        return dist2 - dist1;
//    }
//
//    public static City[] twoOpt(City[] nearestNeighborTour) {
//
//        numberOfNodes = nearestNeighborTour.length;
//        int bestGain = -1;
//        int gain;
//        int best_i = 0;
//        int best_j = 0;
//
//        City[] bestTour = nearestNeighborTour;
//
//        while (bestGain < 0) {
//            bestGain = 0;
//
//            for (int i = 0; i < numberOfNodes; i++) {
//                for (int j = i + 1; j < numberOfNodes; j++) {
//                    //System.out.println("***" + i + " " + j);
//                    gain = getGain(bestTour, i, j);
//
//                    if (gain < bestGain) {
//                        bestGain = gain;
//                        best_i = i;
//                        best_j = j - 1;
//                    }
//                }
//            }
//            if (bestGain < 0) {
//                bestTour = swap(bestTour, best_i, best_j);
//            }
//        }
//
//        return bestTour;
//    }
//
//    public static City[] swap(City[] cities, int i, int j) {
//
//        int t = 0;
//        for (int index = 0; index <= (j-i)/2; index++) {
//            City city = cities[i+t];
//            cities[i+t] = cities[j-t];
//            cities[j-t] = city;
//
//            t++;
//        }
//
//        return cities;
//    }
//
//
//}


class TwoOpt {
    /*
        Compute the 2-Opt algorithm.
    */
    static City[] twoOpt(City[] nearestNeighbourRoute){

        City[] bestRoute = nearestNeighbourRoute;

        int bestGain = -1;
        int best_i = 0;
        int best_j = 0;
        int gain;

        while(bestGain<0){
            bestGain = 0;
            for (int i = 0 ; i < nearestNeighbourRoute.length; i++) {
                for (int j = i + 1; j < nearestNeighbourRoute.length; j++) {
                    //check if distance AB + CD >= distance AC + BD
                    gain=computeGain(bestRoute, i, j);
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
            }
        }

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
    private static int computeGain(City[] route, int i, int j){
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