package ch.supsi.tsp_algoritmi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultWriter{
    public static void writeResults(String filename, City[] finalRoute){
        StringBuilder sb = new StringBuilder();

        sb.append("NAME : ").append(filename).append(".opt.tour\n");
        sb.append("COMMENT : Optimum tour for ").append(filename).append(".tsp (").append(City.getRouteDistanceArray(finalRoute)).append(")\n");
        sb.append("TYPE : TOUR\n");
        sb.append("DIMENSION : ").append(finalRoute.length).append("\n");
        sb.append("TOUR_SECTION\n");

        for(City city: finalRoute){
            sb.append(city.getId()+1).append("\n");
        }

        sb.append("-1\n");
        sb.append("EOF\n");
        try {
            File file = new File("./src/main/resources/"+filename+".opt.tour");
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(sb.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
