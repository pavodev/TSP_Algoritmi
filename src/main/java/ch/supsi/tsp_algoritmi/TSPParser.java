package ch.supsi.tsp_algoritmi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TSPParser {
    private File tspFile;
    private int bestKnown;
    private String fileName;

    public TSPParser(File tspFile) {
        if(tspFile == null || tspFile.getName().split("tsp").length < 1)
            throw new NullPointerException("INVALID TSP FILE");

        this.tspFile = tspFile;
        this.bestKnown = Integer.MAX_VALUE;
        this.fileName = tspFile.getName();
    }

    public List<City> parse() {

        System.out.println(tspFile.getName());
        List<City> cities = new ArrayList<>();

        try {
            String regex = "^\\s+";

            BufferedReader in = new BufferedReader(new FileReader(this.tspFile));
            String line;
            line = in.readLine();

            while(!(line.contains("BEST"))){
                line = in.readLine();
            }

            this.bestKnown = Integer.parseInt(line.split(" ")[2]);
            System.out.println("Best known: " + bestKnown);

            while(!(line.split(" ")[0].equals("1"))){
                line = in.readLine();
                line = line.replaceAll(regex, "");
            }

            while(!line.equals("EOF")){
                line = line.replaceAll(regex, "");
                String[] elements = line.split(" ");
                cities.add(new City(
                                Integer.parseInt(elements[0]),
                                Double.parseDouble(elements[1]),
                                Double.parseDouble(elements[2])
                        )
                );

                line = in.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }

    public double getPercentError(double computedDistance){
        return (Math.abs(this.bestKnown-computedDistance)/this.bestKnown) * 100.0;
    }

    public String getFileName() {
        return fileName;
    }

    public int getBestKnown() {
        return bestKnown;
    }
}
