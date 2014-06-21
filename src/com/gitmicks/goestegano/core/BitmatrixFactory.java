package com.gitmicks.goestegano.core;

import java.awt.image.BufferedImage;

import com.gitmicks.geostegano.tools.Tools;

public class BitmatrixFactory {

	/**
	 * From a given image, build a bitmatrix from the LSB of each image pixel. 
	 * 
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmatrix buildLSBitmatrixFromImage(BufferedImage image) {

		Bitmatrix bmSource = new Bitmatrix(image);

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
			//System.out.println(y+" "+LSBline);

			// build bitmatrix from line
			bmDestination.setRowFromBitLine(LSBline, y);
			

		}

		return bmDestination;
	}

}
