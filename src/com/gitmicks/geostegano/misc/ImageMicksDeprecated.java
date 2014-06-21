package com.gitmicks.geostegano.misc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.gitmicks.goestegano.core.ImageMicks;

public class ImageMicksDeprecated extends ImageMicks {

	public void writeBit0BinaryTxtFile(File txtFile) throws IOException {

		BufferedWriter txtWriter = new BufferedWriter(new FileWriter(txtFile));

		for (int i = 0; i < rows; i++) {

			String line = "";
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < ImageMicks.RGB; k++) {

					int b0 = bitmatrix[j][i][k][0];

					String alpha = "" + b0;

					line = line + alpha;

				}

			}
			txtWriter.write(line + "\n");
		}
		txtWriter.close();

	}

	public void writeBit1BinaryTxtFile(File txtFile) throws IOException {
		// TODO : write bit n

		BufferedWriter txtWriter = new BufferedWriter(new FileWriter(txtFile));

		for (int i = 0; i < 1; i++) {

			String line = "";
			for (int j = 0; j < 96; j++) {

				int b0 = bitmatrix[j][i][0][1];
				int b1 = bitmatrix[j][i][1][1];
				int b2 = bitmatrix[j][i][2][1];

				String alpha = "" + b0 + "" + b1 + "" + b2;

				line = line + alpha;

			}
			txtWriter.write(line + "\n");
		}
		txtWriter.close();

	}

}
