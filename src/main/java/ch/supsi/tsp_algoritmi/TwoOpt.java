package ch.supsi.tsp_algoritmi;

class TwoOpt implements LocalSearchAlgorithm {
    private int[][] distanceMatrix;
    private int[] positions;

    public TwoOpt(int[][] distanceMatrix, int[] positions) {
        this.distanceMatrix = distanceMatrix;
        this.positions = positions;
    }

    /*
            Compute the 2-Opt algorithm.
        */
    public City[] computeOptimization(City[] route){

        //City[] bestRoute = route;

        int bestGain = -1;
        int best_i = 0;
        int best_j = 0;
        int gain;
        int count = 0;

        while(bestGain<0){
            bestGain = 0;
            for (int i = 0; i < route.length; i++) {
                int a = i;
                int b;

                if(a+1 < route.length){
                    b=i+1;
                }else{
                    b=0;
                }

                //int b = (route.length + i + 1) % route.length;

                for (int city: route[i].getCandidateList()) {
                    //compute the gain and update the reference variables
                    int c =  positions[city];
                    int d;

                    if(c + 1 < route.length){
                        d=c+1;
                    }else{
                        d=0;
                    }

                    //int d = (c + 1) % route.length;
                    if(a==c || b==c || d==a)
                        continue;

                    gain = computeGain(route, a, b, c, d);

                    if (gain < bestGain) {
                        bestGain = gain;
                        best_i = a;
                        best_j = c;
                        //System.out.println(a + " " + c);
                    }
                }
            }
            if(bestGain < 0) {
                //System.out.println(best_i + " " + best_j);
                //count++;
                optimalSwap(route, best_i, best_j);
            }
        }

        //System.out.println(count);

        return route;
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
        Swaps i and j, even if j is greater than i
     */
    private void optimalSwap(City[] cities, int i, int j){

        int leftSide;

        if(i+1 < cities.length){
            leftSide = i+1;
        }else{
            leftSide = 0;
        }

        int rightSide = j;

        int dimension = ((cities.length + j - i + 1) % cities.length) / 2;

        for (int k = 0; k < dimension; k++) {
            City tmp = cities[leftSide];
            cities[leftSide] = cities[rightSide];
            //positions[cities[leftSide].getId()] = rightSide;
            cities[rightSide] = tmp;
            //positions[cities[rightSide].getId()] = leftSide;

            leftSide = (leftSide + 1) % cities.length;
            rightSide = (cities.length + rightSide - 1) % cities.length;
        }

        for(int x = 0; x < cities.length; x++){
            positions[cities[x].getId()] = x;
        }
    }

    /*
        Check if the distance between 2 nodes is better if swapped and return the gain.
    */
    private int computeGain(City[] route, int a, int b, int c, int d){

        //compute the distance without swapped indexes
        int A = this.distanceMatrix[route[a].getId()][route[b].getId()] + this.distanceMatrix[route[c].getId()][route[d].getId()];
        //Compute the distance of swapped indexes
        int B = this.distanceMatrix[route[b].getId()][route[d].getId()] + this.distanceMatrix[route[c].getId()][route[a].getId()];

        return  B-A;
    }
}