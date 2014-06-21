package com.gitmicks.geostegano.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.gitmicks.goestegano.core.Bitmatrix;
import com.gitmicks.goestegano.core.BitmatrixFactory;
import com.gitmicks.goestegano.core.ImageMicks;

public class Main {

	public static final String INPUT_PATH = "src/input/";
	public static final String OUTPUT_PATH = "src/output/";
	public static final String OUTPUT_FILENAME = "output.png";
	
	
	public static void main(String[] args) {
		long top = System.currentTimeMillis();
		System.out.println("begin ");
		

		try {
			String envelop = args[0];
			String hidden = args[1];
			File envelopFile = new File(INPUT_PATH + envelop);
			File hiddenFile = new File(INPUT_PATH + hidden);
	
			BufferedImage envelopImage = ImageIO.read(envelopFile);
			BufferedImage hiddenImage = ImageIO.read(hiddenFile);
			
			Bitmatrix envelopBm = new Bitmatrix(envelopImage);
			Bitmatrix hiddenBm = new Bitmatrix(hiddenImage);
	
			File outputFile = new File(OUTPUT_PATH + OUTPUT_FILENAME);
			File unhiddenFile = new File(OUTPUT_PATH + "unhidden_"+OUTPUT_FILENAME);
			File txtFile = new File(OUTPUT_PATH + "result.txt");

			envelopBm.hideInLSB(hiddenBm);
			
			//envelopBm.writeRawBinaryTxtFile(txtFile);
			//envelopBm.writeRGBbits2BinaryTxtFile(txtFile, 1);
			
			ImageMicks imageOut = new ImageMicks();
			imageOut.writeImage(outputFile, envelopBm);
								
			BufferedImage envelopImageWithHidden = ImageIO.read(outputFile);
			Bitmatrix outputBM = BitmatrixFactory.buildLSBitmatrixFromImage(envelopImageWithHidden);
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
			
			
			
			
			System.out.println("end");
			long stop = System.currentTimeMillis();
			System.out.println("elapsed = "+(stop-top)/1000 +" s");

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
