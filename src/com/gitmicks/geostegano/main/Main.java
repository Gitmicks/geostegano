package com.gitmicks.geostegano.main;

import java.io.File;
import java.io.IOException;

import org.apache.commons.imaging.ImageReadException;

import com.gitmicks.goestegano.core.ImageMicks;

public class Main {

	public static final String INPUT_PATH = "src/input/";
	public static final String OUTPUT_PATH = "src/output/";
	public static final String OUTPUT_FILENAME = "output.png";

	public static void main(String[] args) {
		System.out.println("main");

		try {
			String filename = args[0];
			File inputFile = new File(INPUT_PATH + filename);
			File outputFile = new File(OUTPUT_PATH + OUTPUT_FILENAME);

			ImageMicks imageIn = new ImageMicks(inputFile);
			imageIn.writeImage(outputFile);

			System.out.println("end");

		} catch (ImageReadException ire) {
			// TODO Auto-generated catch block
			ire.printStackTrace();
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException ai) {
			System.out.println("Bad aruments !");
			ai.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
