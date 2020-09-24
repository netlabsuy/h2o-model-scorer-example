package uy.com.netlabs.h2omodelscorer;

import hex.genmodel.MojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.BinomialModelPrediction;

import java.io.*;

public class ModelExecutor {
    private static final String LABEL_HEADER = "label";
    private static final String CSV_SEPARATOR = ",";

    private static ModelExecutor instance = new ModelExecutor();

    private EasyPredictModelWrapper model;

    public static ModelExecutor getInstance() {
        return instance;
    }

    public void init(String path) {
        try {
            model = new EasyPredictModelWrapper(MojoModel.load(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void score(String inputFilePath, String outputFilePath) throws PredictException, IOException {

        try (FileReader reader = new FileReader(inputFilePath);
             BufferedReader br = new BufferedReader(reader);
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            boolean isFirstLine = true;
            String[] header = null;
            RowData row = new RowData();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);
                if (isFirstLine) {
                    isFirstLine = false;
                    header = data;
                    writer.append(line + CSV_SEPARATOR + LABEL_HEADER);
                    writer.append("\n");
                } else {
                    for (int i = 0; i < data.length; i++) {
                        if (data[i] != null && !data[i].equals("NULL")) {
                            row.put(header[i], data[i]);
                        }
                    }
                    BinomialModelPrediction p = model.predictBinomial(row);
                    String label = p.label;
                    writer.append(line + CSV_SEPARATOR + label);
                    writer.append("\n");
                }
            }
        }
    }

    private ModelExecutor() { }
}
