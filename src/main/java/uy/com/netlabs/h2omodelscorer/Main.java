package uy.com.netlabs.h2omodelscorer;


import hex.genmodel.easy.exception.PredictException;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		String modelPath = args[0];
		String inputFilePath = args[1];
		String outputFilePath = args[2];

		ModelExecutor.getInstance().init(modelPath);
		try {
			ModelExecutor.getInstance().score(inputFilePath, outputFilePath);
		} catch (PredictException | IOException e) {
			e.printStackTrace();
		}
	}

}
