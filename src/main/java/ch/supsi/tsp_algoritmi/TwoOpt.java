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

        while(bestGain<0){
            bestGain = 0;
            for (int i = 0 ; i < route.length; i++) {
                for (int j = i + 1; j < route.length; j++) {
                    //cycles++;
                    //check if distance AB + CD >= distance AC + BD
                    gain = computeGain(bestRoute, i, j);
                    //compute the gain and update the reference variables
                    if (gain < bestGain) {
                        bestGain = gain;
                        best_j = j - 1;
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

        return  B-A;
    }
}