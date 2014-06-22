package com.gitmicks.geostegano.main;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import com.gitmicks.goestegano.core.Bitmatrix;
import com.gitmicks.goestegano.core.BitmatrixFactory;
import com.gitmicks.logging.Logging;

public class Main {
	
	

	public static final String INPUT_PATH = "src/input/";
	public static final String OUTPUT_PATH = "src/output/";
	public static final String OUTPUT_FILENAME = "output.png";
		
	
	public static void logConfig()
	{
		String log4jConfigFile = System.getProperty("user.dir")
                + File.separator + "\\config\\log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        LogManager.getLogger(Logging.LOGGER).setLevel(Level.INFO);
		
	}
	
	public static void main(String[] args) {
		logConfig();
		
		long top = System.currentTimeMillis();
		Logging.logger.info("Start ...");
		
		try {
		
			String envelop = args[0];
			String textFile = args[1];
			File envelopFile = new File(INPUT_PATH + envelop);
			File inputTextFile = new File(INPUT_PATH + textFile);
						
			File resultTxtFile = new File(OUTPUT_PATH + "result.txt");
			File outputPNGFile = new File(OUTPUT_PATH + OUTPUT_FILENAME);
			//Bitmatrix envelopBM = new Bitmatrix(envelopFile);
			//envelopBM.writeRawBinaryTxtFile(txtFile);
			//envelopBM.writeRGBbits2BinaryTxtFile(txtFile, 1);
			//envelopBM.writeAsciiTxtFile(txtFile);
			
			//Bitmatrix outputBM = BitmatrixFactory.buildBitmatrixfromRGB(envelopBM, 1);
			//outputBM.writeAsciiTxtFile(txtFile);
			
			//ImageMicks imageOut = new ImageMicks();
			//imageOut.writeImage(outputFile, outputBM);
			
			Bitmatrix outputBM = BitmatrixFactory.buildBitmatrixFromText(inputTextFile);
			outputBM.writeRawBinaryTxtFile(resultTxtFile);
			
			
			//String hidden = args[1];
			//File hiddenFile = new File(INPUT_PATH + hidden);
			//BufferedImage hiddenImage = ImageIO.read(hiddenFile);
			
			

			
			/*
			Bitmatrix hiddenBm = new Bitmatrix(hiddenImage);
	
			
			File unhiddenFile = new File(OUTPUT_PATH + "unhidden_"+OUTPUT_FILENAME);
			

			envelopBm.hideInLSB(hiddenBm);
			
			
			ImageMicks imageOut = new ImageMicks();
			imageOut.writeImage(outputFile, envelopBm);
								
			BufferedImage envelopImageWithHidden = ImageIO.read(outputFile);
			Bitmatrix outputBM = BitmatrixFactory.buildBitmatrixFromLSB(envelopImageWithHidden);
			imageOut.writeImage(unhiddenFile, outputBM);
			
			
			//imageIn.displayMetadata();
		
			//imageIn.BtoR();
			//imageIn.setLSBtoMSB();
			//imageIn.bitInversion();
			//imageIn.shift1();
			//imageIn.setTestComparaison();
			//imageIn.setLSBtoMSB();
			//imageIn.testAlpha();
			//imageIn.compression(4,2,0);
			//imageIn.setLSBtoMSB();
			//imageIn.setBit1();
			
			//imageIn.writeRawBinaryTxtFile(txtFile);
			//imageIn.writeBit1BinaryTxtFile(txtFile);
			//imageIn.writeRGBbits2BinaryTxtFile(txtFile,1);
			//imageIn.writeRGBbits2BinaryTxtFile(txtFile,0,1,2);
			//imageIn.writeImage(outputFile);
			
			
			*/
			Logging.logger.info("... end.");			
			long stop = System.currentTimeMillis();
			Logging.logger.info("elapsed = "+(stop-top)/1000 +" s");
			
		} catch (ArrayIndexOutOfBoundsException ai) {
			System.out.println("Bad aruments !");
			ai.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
