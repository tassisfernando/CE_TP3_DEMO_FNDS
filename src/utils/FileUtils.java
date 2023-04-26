package utils;

import model.Individual;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    public static void createFile(String path, List<Individual> individuals, boolean isFunctionValues) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (Individual individual : individuals) {
            if (isFunctionValues) {
                bufferedWriter.write(Arrays.toString(individual.getFunctionValues()));
                bufferedWriter.newLine();
            } else {
                bufferedWriter.write(Arrays.toString(individual.getGenes()));
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.close();
        fileWriter.close();
    }
}
