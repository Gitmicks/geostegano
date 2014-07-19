package com.gitmicks.geostegano.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import com.gitmicks.geostegano.tools.Tools;
import com.gitmicks.goestegano.core.Bitmatrix;
import com.gitmicks.goestegano.core.ImageMicks;
import com.gitmicks.logging.Logging;

public class Main {

	public static final String INPUT_PATH = "src/input/";
	public static final String OUTPUT_PATH = "src/output/";
	public static final String OUTPUT_PNG_FILENAME = "output.png";
	public static final String OUTPUT_TXT_FILENAME = "output.txt";

	public static void logConfig() {
		String log4jConfigFile = System.getProperty("user.dir")
				+ File.separator + "\\config\\log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
		LogManager.getLogger(Logging.LOGGER).setLevel(Level.DEBUG);

	}

	public static void OldMain() {
		/*
		 * String envelop = args[0]; String textFile = args[1]; File envelopFile
		 * = new File(INPUT_PATH + envelop); File inputTextFile = new
		 * File(INPUT_PATH + textFile);
		 * 
		 * File resultTxtFile = new File(OUTPUT_PATH + "result.txt"); File
		 * outputPNGFile = new File(OUTPUT_PATH + OUTPUT_FILENAME); //Bitmatrix
		 * envelopBM = new Bitmatrix(envelopFile);
		 * //envelopBM.writeRawBinaryTxtFile(txtFile);
		 * //envelopBM.writeRGBbits2BinaryTxtFile(txtFile, 1);
		 * //envelopBM.writeAsciiTxtFile(txtFile);
		 * 
		 * //Bitmatrix outputBM =
		 * BitmatrixFactory.buildBitmatrixfromRGB(envelopBM, 1);
		 * //outputBM.writeAsciiTxtFile(txtFile);
		 * 
		 * //ImageMicks imageOut = new ImageMicks();
		 * //imageOut.writeImage(outputFile, outputBM);
		 * 
		 * Bitmatrix outputBM =
		 * BitmatrixFactory.buildBitmatrixFromText(inputTextFile);
		 * outputBM.writeRawBinaryTxtFile(resultTxtFile);
		 * 
		 * 
		 * //String hidden = args[1]; //File hiddenFile = new File(INPUT_PATH +
		 * hidden); //BufferedImage hiddenImage = ImageIO.read(hiddenFile);
		 * 
		 * 
		 * 
		 * 
		 * /* Bitmatrix hiddenBm = new Bitmatrix(hiddenImage);
		 * 
		 * 
		 * File unhiddenFile = new File(OUTPUT_PATH +
		 * "unhidden_"+OUTPUT_FILENAME);
		 * 
		 * 
		 * envelopBm.hideInLSB(hiddenBm);
		 * 
		 * 
		 * ImageMicks imageOut = new ImageMicks();
		 * imageOut.writeImage(outputFile, envelopBm);
		 * 
		 * BufferedImage envelopImageWithHidden = ImageIO.read(outputFile);
		 * Bitmatrix outputBM =
		 * BitmatrixFactory.buildBitmatrixFromLSB(envelopImageWithHidden);
		 * imageOut.writeImage(unhiddenFile, outputBM);
		 * 
		 * 
		 * //imageIn.displayMetadata();
		 * 
		 * //imageIn.BtoR(); //imageIn.setLSBtoMSB(); //imageIn.bitInversion();
		 * //imageIn.shift1(); //imageIn.setTestComparaison();
		 * //imageIn.setLSBtoMSB(); //imageIn.testAlpha();
		 * //imageIn.compression(4,2,0); //imageIn.setLSBtoMSB();
		 * //imageIn.setBit1();
		 * 
		 * //imageIn.writeRawBinaryTxtFile(txtFile);
		 * //imageIn.writeBit1BinaryTxtFile(txtFile);
		 * //imageIn.writeRGBbits2BinaryTxtFile(txtFile,1);
		 * //imageIn.writeRGBbits2BinaryTxtFile(txtFile,0,1,2);
		 * //imageIn.writeImage(outputFile);
		 */

	}

	public static void GC2TMAK() {
		// http://www.geocaching.com/geocache/GC2TMAK_gc2tmak

		//

		int nC = 2158786; // 4357000-2158786 = 2198214
							// 4362000-2158786 = 2203214
		int eC = 1520441;
		// 677000+1520441 = 2197441
		// 682500+1520441 = 2202941

		int index = 0;

		for (int n = 4357000; n < 4362000; n++) {
			for (int e = 677000; e < 682500; e++) {
				for (int x = 2197441; x < 2203214; x++) {
					// System.out.println(nC+x -n);
					// System.out.println(n-e - 3679227);

					if ((n - e == 3679227) && (nC + x == n) && (x - eC == e)) {

						System.out.println("n :" + n + " e :" + e + " x :" + x);
						index++;
					}

				}

			}

		}
		System.out.println(index);
	}

	public static void colorsAscii2Hex2PNG(String arg1) throws IOException {

		// input (arg1) : any text file
		// text is right-padded with blanks
		// longest line must be modulo 3 (automatic)
		// each line is converted to hex
		// full hex transferred to "hexmatrix" (number of pixels, number of
		// lines)
		// 6 characters => 1 pixel (hex)
		// Bitmatrix(hexmatrix) => PNG

		File inputTextFile = new File(INPUT_PATH + arg1);
		File outputPNGFile = new File(OUTPUT_PATH + OUTPUT_PNG_FILENAME);

		List<String> stringList = Tools.textFileToStringList(inputTextFile);

		int maxLength = 0;
		for (Iterator<String> iterator = stringList.iterator(); iterator
				.hasNext();) {
			String string = (String) iterator.next();
			if (string.length() > maxLength) {
				maxLength = string.length();
			}

		}

		maxLength = maxLength + (maxLength % 3);

		String[][] hexMatrix = new String[maxLength / 3][stringList.size()];

		int index = 0;
		for (Iterator<String> iterator = stringList.iterator(); iterator
				.hasNext();) {
			String string = (String) iterator.next();
			String paddedString = StringUtils.rightPad(string, maxLength);
			String hexString = Tools.AsciiToHex(paddedString);

			int position = 0;
			for (int i = 0; i < hexString.length() / 6; i++) {

				hexMatrix[i][index] = hexString.substring(position,
						position + 6);
				position = position + 6;

			}

			index++;
		}
		Bitmatrix bm = new Bitmatrix(hexMatrix, maxLength / 3,
				stringList.size());

		ImageMicks imageOut = new ImageMicks();

		imageOut.writeImage(outputPNGFile, bm);

	}

	public static void colorsPNG2Hex2Ascii(String pngFile) throws IOException {

		// input : PNG file coded via colorsAscii2Hex2PNG
		// Bitmatrix(File pngFile)
		// each line is converted to hex
		// each hex is coverted to ascii

		File inputPNGFile = new File(INPUT_PATH + pngFile);
		File outputTxtFile = new File(OUTPUT_PATH + OUTPUT_TXT_FILENAME);

		Bitmatrix bm = new Bitmatrix(inputPNGFile);

		String[] hexStringList = new String[bm.getRows()]; 
		
		for (int y = 0; y < bm.getRows(); y++) {			
			String line = "";
			for (int x = 0; x < bm.getCols(); x++) {
				line = line + Tools.color2hex(bm.getColor(x,y));
			}
			hexStringList[y] = line;			 						
		}
		
		BufferedWriter txtWriter = new BufferedWriter(new FileWriter(outputTxtFile));
		
		for (int i = 0; i < hexStringList.length; i++) {
			txtWriter.write( Tools.hex2Ascii(hexStringList[i]) + "\n");
		}
		txtWriter.close();
	}

	public static void main(String[] args) {
		logConfig();

		String arg0 = args[0]; // PNG file
		String arg1 = args[1]; // text file

		long top = System.currentTimeMillis();
		Logging.logger.info("Start ...");

		try {

			colorsPNG2Hex2Ascii(arg0);
			//colorsAscii2Hex2PNG(arg1);
			
			Logging.logger.info("... end.");
			long stop = System.currentTimeMillis();
			Logging.logger.info("elapsed = " + (stop - top) / 1000 + " s");

		} catch (ArrayIndexOutOfBoundsException ai) {
			System.out.println("Bad aruments !");
			ai.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
