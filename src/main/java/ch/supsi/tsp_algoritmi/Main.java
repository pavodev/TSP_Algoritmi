package ch.supsi.tsp_algoritmi;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("TSP_ALGORITMI PROJECT");
        System.out.println();

        ClassLoader classLoader = new Main().getClass().getClassLoader();
        File file = new File(classLoader.getResource("eil76.tsp").getFile()) ;

        List<City> cityList = TSPParser.parse(file);

//        for(City city: cityList)
//            System.out.println(city);

        int[][] distanceMatrix = City.getDistanceMatrix(cityList);

        City.printDistanceMatrix(cityList, distanceMatrix);
    }
}
