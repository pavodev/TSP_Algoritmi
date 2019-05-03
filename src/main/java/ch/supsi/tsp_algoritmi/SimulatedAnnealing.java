package ch.supsi.tsp_algoritmi;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static ch.supsi.tsp_algoritmi.City.*;

public class SimulatedAnnealing {

    private long seed;
    private long elapsedTime;
    private double alpha;
    private int temperature;
    private long alreadyElapsed;
    private static Random random;
    private LocalSearchAlgorithm localSearchAlgorithm;
    private int[] positions;

    SimulatedAnnealing(long seed, LocalSearchAlgorithm localSearchAlgorithm, int[] positions, long alreadyElapsed){
        this.seed = seed;
        this.elapsedTime = 0;
        this.alpha = 0;
        this.temperature = 0;
        this.localSearchAlgorithm = localSearchAlgorithm;
        random = new Random(seed);
        this.positions = positions;
        this.alreadyElapsed = alreadyElapsed;
    }

    public City[] simulatedAnnealing(City[] nearestNeighborRoute){
        int temperature = random.nextInt(50) + 100;
        double alpha = random.nextDouble()*(0.9999999 - 0.900000) + 0.900000;

        this.temperature = temperature;
        this.alpha = alpha;

        City[] current = nearestNeighborRoute.clone();
        City[] best = current;
        City[] next;
        City[] candidate;

        long startTime = System.nanoTime();
        long elapsedSimTime = 0;

        while(elapsedSimTime < 178-alreadyElapsed){
            for(int i = 0; i<100; i++){
                next = doubleBridge(current);
                candidate = this.localSearchAlgorithm.computeOptimization(next);

                if(getRouteDistanceArray(candidate) < getRouteDistanceArray(current)) {
                    current = candidate;
                    if (getRouteDistanceArray(current) < getRouteDistanceArray(best)) {
                        best = current;
                    }
                } else if(random.nextDouble() < Math.exp((-((double)getRouteDistanceArray(candidate) - getRouteDistanceArray(current)))/temperature)){
                    current = candidate;
                }
            }

            elapsedSimTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime);
            temperature *= alpha;
        }

        this.elapsedTime = elapsedSimTime + alreadyElapsed;

        return best;
    }

    public City[] doubleBridge(City[] current){
        int[] randomIndexes = new int[8];
        City[] next = new City[current.length];

        for(int i=0; i<8; i=i+2){
            final int randomNumber = random.nextInt(current.length-1);
            if(IntStream.of(randomIndexes).noneMatch(x -> x == randomNumber) && IntStream.of(randomIndexes).noneMatch(x -> x == randomNumber+1) || randomNumber==next.length-1){
                randomIndexes[i] = randomNumber;

                randomIndexes[i+1]=randomIndexes[i]+1;

            } else {
                i -= 2;
            }
        }

//        System.out.println("double bridge");
        Arrays.sort(randomIndexes);

//        for(int randomIndex : randomIndexes){
//            System.out.print(randomIndex + " ");
//        }
//        System.out.println();

        int a = randomIndexes[0];
        int aplus = randomIndexes[1];
        int b = randomIndexes[2];
        int bplus = randomIndexes[3];
        int c = randomIndexes[4];
        int cplus = randomIndexes[5];
        int d = randomIndexes[6];
        int dplus = randomIndexes[7];

        //DEVO FARE ATTENZIONE A NON GENERARE INDICI UGUALI; SE SUCCEDE i--.
        //ALLA FINE DELLA SCELTA DEI NUMERI RANDOM; DEVO METTERLI IN ORDINE COSÌ EVITO GLI INCROCI!
        //RICORDARMI DI METTERE IL SEED VARIABILE E DI SALVARLO SE MIGLIORE!

        System.arraycopy(current, 0, next, 0, a+1);
        System.arraycopy(current, cplus, next, aplus, d-cplus + 1);
        System.arraycopy(current, bplus, next, aplus + (d-cplus + 1), c-bplus + 1);
        System.arraycopy(current, aplus, next, aplus + (d-cplus + 1) + (c-bplus + 1), b-aplus + 1);
        System.arraycopy(current, dplus, next, aplus + (d-cplus + 1) + (c-bplus + 1) + (b-aplus + 1), next.length-1-dplus+1);

        for(int x = 0; x < current.length; x++){
            positions[current[x].getId()] = x;
        }

        return next;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public double getAlpha() {
        return alpha;
    }

    public int getTemperature() {
        return temperature;
    }
}
