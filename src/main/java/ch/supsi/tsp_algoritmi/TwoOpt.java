package ch.supsi.tsp_algoritmi;

class TwoOpt implements LocalSearchAlgorithm {
    private int[][] distanceMatrix;

    public TwoOpt(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    /*
            Compute the 2-Opt algorithm.
        */
    public City[] computeOptimization(City[] route){

        City[] bestRoute = route;

        int bestGain = -1;
        int best_i = 0;
        int best_j = 0;
        int gain;

        //int cycles = 0;

        boolean improved = false;

        while(bestGain<0){
            bestGain = 0;
            for (int i = 0 ; i < route.length; i++) {
                if(!bestRoute[i].isLook()) {
                    for (int j = i + 1; j < route.length; j++) {
                        //cycles++;
                        //check if distance AB + CD >= distance AC + BD
                        gain = computeGain(bestRoute, i, j);
                        //compute the gain and update the reference variables
                        if (gain < bestGain) {
                            improved = true;
                            bestGain = gain;
                            best_j = j - 1;
                            best_i = i;
                        }
                    }
                    if (!improved) {
                        //System.out.println(i);
                        bestRoute[i].setLook(true);
                    }

                    //improved = false;
                }
                //System.out.println(cycles);
                //cycles = 0;
            }
            if(bestGain < 0) {
                swap(bestRoute, best_i, best_j);
            }

        }

        for(City city: bestRoute){
            city.setLook(false);
        }



        return bestRoute;
    }

    /*
        Swap the given i and j indexes and everything that stays between the two.
    */
    private void swap(City[] route, int i, int j) {
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
    private int computeGain(City[] route, int i, int j){
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
        int A = this.distanceMatrix[route[i].getId()][route[a].getId()] + this.distanceMatrix[route[j].getId()][route[b].getId()];
        //Compute the distance of swapped indexes
        int B = this.distanceMatrix[route[i].getId()][route[j].getId()] + this.distanceMatrix[route[a].getId()][route[b].getId()];

//        //compute the distance without swapped indexes
//        int A1 = City.getDistance(route[i], route[a]) + City.getDistance(route[j], route[b]);
//        //Compute the distance of swapped indexes
//        int B1 = City.getDistance(route[i], route[j]) + City.getDistance(route[a], route[b]);

        if(B-A < 0) {
            route[i].setLook(false);
            route[j].setLook(false);
            route[a].setLook(false);
            route[b].setLook(false);
        }

        return  B-A;
    }
}