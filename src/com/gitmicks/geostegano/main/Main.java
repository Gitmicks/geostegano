package com.gitmicks.geostegano.main;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.IImageMetadata.IImageMetadataItem;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.png.PngImageParser;
import org.apache.commons.imaging.formats.png.chunks.PngChunk;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gitmicks.goestegano.core.ImageMicks;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

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
			File txtFile = new File(OUTPUT_PATH + "result.txt");

			ImageMicks imageIn = new ImageMicks(inputFile);
			
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
			
			//imageIn.writeRawBinaryTxtFile(txtFile);	
			imageIn.writeBit0BinaryTxtFile(txtFile);
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
