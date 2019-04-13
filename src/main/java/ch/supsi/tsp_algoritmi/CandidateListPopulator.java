package ch.supsi.tsp_algoritmi;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CandidateListPopulator {
    private int[][] distanceMatrix;

    public CandidateListPopulator(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public void populateCandidateLists(City[] cities){
        int[][] distances = distanceMatrix.clone();

        Map<Integer, Integer> distancesForCity = new HashMap<>();

        for(int i = 0; i<cities.length; i++){
            for(int j = 0; j < cities.length; j++) {
                distancesForCity.put(j, distances[i][j]);
            }

            final Map<Integer, Integer> sortedByCount = distancesForCity.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));


            int count = 0;

            for(int city: sortedByCount.keySet()){
                if(count >= 15)
                    break;

                if(sortedByCount.get(city) != 0 && !cities[i].getCandidateList().contains(city)){
                    cities[i].getCandidateList().add(city);
                    count++;
                }
            }
        }
    }
}
