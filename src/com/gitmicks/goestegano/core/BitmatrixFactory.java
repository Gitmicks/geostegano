package com.gitmicks.goestegano.core;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gitmicks.geostegano.tools.Tools;
import com.gitmicks.logging.Logging;

public class BitmatrixFactory {

	/**
	 * From a given image, build a bitmatrix from the LSB of each pixel. 
	 * 
	 * 
	 * @param image
	 * @return
	 * @throws IOException 
	 */
	public static Bitmatrix buildBitmatrixFromLSB(File inputFile) throws IOException {

		Bitmatrix bmSource = new Bitmatrix(inputFile);

		// defining width and height
		// 1 pixel (origin) = 1/24 pixel (destination)
		int width = (bmSource.getCols() - (bmSource.getCols() % (24))) / (24);
		int height = bmSource.getRows();

		Bitmatrix bmDestination = new Bitmatrix(bmSource.getBitDepth(),
				bmSource.getColorDepth(), width, height);

		for (int y = 0; y < bmSource.getRows(); y++) {

			String sourceLine = bmSource.flattenRow(y);
			
			int firstBitPosition = 23;
			int numberOfBitsInApixel = 24;
			String LSBline = Tools.buildString(sourceLine,firstBitPosition,
					numberOfBitsInApixel);
			// build bitmatrix from line
			bmDestination.setRowFromBitLine(LSBline, y);
			

		}

		return bmDestination;
	}
	
	
	/**
	 * Creates a Bitmatrix by getting the R,G and B bit (at the bitPosition) of every RGB
	 * of the input (a Bitmatrix). 
	 * 
	 * 
	 */
	public static Bitmatrix buildBitmatrixfromRGB(Bitmatrix bmSource, int bitPosition)
			throws IOException {
		
		Logging.logger.info("BitmatrixFactory.buildLSBitmatrix " + bitPosition);

		// 1 pixel (origin) = 1/8 pixel (destination)
		int width = (bmSource.getCols() - (bmSource.getCols() % (8))) / (8);
		int height = bmSource.getRows();

		Bitmatrix bmDestination = new Bitmatrix(bmSource.getBitDepth(),
				bmSource.getColorDepth(), width, height);
		
		for (int y = 0; y < bmSource.getRows(); y++) {

			String sourceLine = bmSource.flattenRow(y);
			
			// bit position = 7 : most significant bit
			// bit position = 0 : least significant bit
			int firstBitPosition = 7-bitPosition;
			int step = 8;
			String LSBline = Tools.buildString(sourceLine,firstBitPosition,
					step);
			bmDestination.setRowFromBitLine(LSBline, y);
			
		}

		return bmDestination;
	}


	/**
	 * Builds a bitmatrix from a text file, converting ascii to binary.
	 * One line in the text = one row in the matrix
	 * Width of the matrix (in pixels = rows) : (length of longest line)/3 
	 *  
	 * @param inputFile
	 * @return
	 * @throws IOException 
	 */
	public static Bitmatrix buildBitmatrixFromText(File inputFile) throws IOException {

		List<String> lines = Tools.textFileToStringList(inputFile);
		
		int numberOfLines = 0;
		int maxLength =0;

		String[] stringList = new String[lines.size()];
		
		int index = 0;
		for (String line : lines) {
			numberOfLines++;
			if (line.length() > maxLength) {
				maxLength = line.length();
			}
			stringList[index] = line;
			index++;
        }
		
		// defining width and height
		// 1 pixel (origin) = 1/24 pixel (destination)
		
		
		// width must include all characters
		int width = (maxLength - (maxLength % 3)) / 3 ;
		if (maxLength % 3 > 0) {
			width++;
		}
			
		int height = numberOfLines; 

		Bitmatrix bmDestination = new Bitmatrix(width, height);
		
		for (int y = 0; y < height; y++) {

			// all lines must have the same size
			String bitLine = Tools.AsciiToBinary(StringUtils.rightPad(stringList[y],width*24));
			
			Logging.logger.debug("BitmatrixFactory.buildBitmatrixFromText "+bitLine);			
			bmDestination.setRowFromBitLine(bitLine, y);			
		}

		return bmDestination;
	}
	
}
