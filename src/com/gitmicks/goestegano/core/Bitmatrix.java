package com.gitmicks.goestegano.core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.gitmicks.geostegano.tools.Tools;

public class Bitmatrix {

	protected int[][][][] bitmatrix;
	protected int bitDepth = 8;

	protected int colorDepth = 3;
	public int getBitDepth() {
		return bitDepth;
	}

	public void setBitDepth(int bitDepth) {
		this.bitDepth = bitDepth;
	}

	public int getColorDepth() {
		return colorDepth;
	}

	public void setColorDepth(int colorDepth) {
		this.colorDepth = colorDepth;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	protected int cols = 0;
	protected int rows = 0;

	final static protected int B = 2;
	final static protected int G = 1;
	final static protected int R = 0;

	public Bitmatrix(int pBitDepth, int pColorDepth, int pCols, int pRows) {
		super();
		this.bitDepth = pBitDepth;
		this.colorDepth = pColorDepth;
		this.cols = pCols;
		this.rows = pRows;
		this.bitmatrix = new int[cols][rows][colorDepth][bitDepth];
	}

	public Bitmatrix(BufferedImage image) {
		cols = image.getWidth();
		rows = image.getHeight();
		bitmatrix = new int[cols][rows][colorDepth][bitDepth];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				int pixel = image.getRGB(x, y);
				Color c = new Color(pixel);
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				setRGB(x, y, r, g, b);
			}
		}
	}

	public void setR(int pCol, int pRow, int rgb) {
		String rgbInBits = Tools.intToBits(rgb);
		for (int j = 0; j < bitDepth; j++) {

			bitmatrix[pCol][pRow][R][j] = Integer.parseInt(rgbInBits.substring(
					bitDepth - 1 - j, bitDepth - j));
		}
	}

	public void hideInLSB(Bitmatrix bm2hide) {
		
		// TODO : add catch on mb2hide size (24 lesser than envelop)		
		for (int y = 0; y < bm2hide.getCols(); y++) {
			
			int index = 0;
			String bitLine = bm2hide.flattenRow(y);			
			for (int x = 0; x < cols; x++) {
												
				bitmatrix[x][y][B][0] = Integer.parseInt(bitLine.substring(index, index+1));
				index = index+1;
				
				
			}
		}

	}

	public int getBit(int x, int y, int c, int pos) {
		return bitmatrix[x][y][c][pos] ;
	}

	public void setBit(int x, int y, int c, int pos, int bit) {
		bitmatrix[x][y][c][pos] = bit;
	}

	
	public void setG(int pCol, int pRow, int rgb) {
		String rgbInBits = Tools.intToBits(rgb);
		for (int j = 0; j < bitDepth; j++) {
			bitmatrix[pCol][pRow][G][j] = Integer.parseInt(rgbInBits.substring(
					bitDepth - 1 - j, bitDepth - j));
		}
	}

	public void setB(int pCol, int pRow, int rgb) {
		String rgbInBits = Tools.intToBits(rgb);
		for (int j = 0; j < bitDepth; j++) {
			bitmatrix[pCol][pRow][B][j] = Integer.parseInt(rgbInBits.substring(
					bitDepth - 1 - j, bitDepth - j));
		}
	}

	public void setRGB(int pCol, int pRow, int r, int g, int b) {
		setR(pCol, pRow, r);
		setG(pCol, pRow, g);
		setB(pCol, pRow, b);
	}

	public int getRGB(int pCol, int pRow) {

		String rInBits = "";
		String gInBits = "";
		String bInBits = "";

		for (int k = 0; k < bitDepth; k++) {
			rInBits = rInBits
					+ bitmatrix[pCol][pRow][ImageMicks.R][bitDepth - 1 - k];
			gInBits = gInBits
					+ bitmatrix[pCol][pRow][ImageMicks.G][bitDepth - 1 - k];
			bInBits = bInBits
					+ bitmatrix[pCol][pRow][ImageMicks.B][bitDepth - 1 - k];
		}

		int r = Tools.bitsToInt(rInBits);
		int g = Tools.bitsToInt(gInBits);
		int b = Tools.bitsToInt(bInBits);

		//System.out.println(r+ " "+g+" "+b);
		
		Color c = new Color(r, g, b);
		return c.getRGB();
	}

	/**
	 * Gets all the "n-position (0..7)" RGB bits from bitmatrix and exports them
	 * to a text file, row by row.
	 * 
	 * @param bitPosition
	 *            the bit position to be extracted from RGB and written
	 * @param txtFile
	 *            output text file
	 * @throws IOException
	 */
	public void writeRGBbits2BinaryTxtFile(File txtFile, int bitPosition)
			throws IOException {

		System.out.println("   writeRGBbits2BinaryTxtFile " + bitPosition);
		BufferedWriter txtWriter = new BufferedWriter(new FileWriter(txtFile));

		for (int y = 0; y < rows; y++) {

			String line = "";
			for (int x = 0; x < cols; x++) {

				int r = bitmatrix[x][y][R][bitPosition];
				int g = bitmatrix[x][y][G][bitPosition];
				int b = bitmatrix[x][y][B][bitPosition];

				// bits R,G and B converted to string
				String alpha = "" + r + "" + g + "" + b;
				line = line + alpha;
			}
			txtWriter.write(line + "\n");
		}
		txtWriter.close();
	}

	/**
	 * Gets all the "n-position (0..7)" RGB bits from bitmatrix and exports them
	 * to a text file, row by row. Here, each RGB has its defined position.
	 * 
	 * @param Rposition
	 *            the bit position from R
	 * @param Gposition
	 *            the bit position from G
	 * @param Bposition
	 *            the bit position from B
	 * @param txtFile
	 *            output text file
	 * @throws IOException
	 */
	public void writeRGBbits2BinaryTxtFile(File txtFile, int Rposition,
			int Gposition, int Bposition) throws IOException {

		System.out.println("   writeRGBbits2BinaryTxtFile " + "R" + Rposition
				+ "G" + Gposition + "B" + Bposition);
		BufferedWriter txtWriter = new BufferedWriter(new FileWriter(txtFile));

		for (int y = 0; y < rows; y++) {

			String line = "";
			for (int x = 0; x < cols; x++) {

				int r = bitmatrix[x][y][R][Rposition];
				int g = bitmatrix[x][y][G][Gposition];
				int b = bitmatrix[x][y][B][Bposition];

				// bits R,G and B converted to string
				String alpha = "" + r + "" + g + "" + b;
				line = line + alpha;
			}
			txtWriter.write(line + "\n");
		}
		txtWriter.close();
	}

	public String flattenRow(int rowNumber)
	{
		String stringRow = "";
		
		for (int x = 0; x < cols; x++) {
			for (int k = 0; k < colorDepth; k++) {

				int b0 = bitmatrix[x][rowNumber][k][0];
				int b1 = bitmatrix[x][rowNumber][k][1];
				int b2 = bitmatrix[x][rowNumber][k][2];
				int b3 = bitmatrix[x][rowNumber][k][3];
				int b4 = bitmatrix[x][rowNumber][k][4];
				int b5 = bitmatrix[x][rowNumber][k][5];
				int b6 = bitmatrix[x][rowNumber][k][6];
				int b7 = bitmatrix[x][rowNumber][k][7];

				String alpha = b7 + "" + b6 + "" + b5 + "" + b4 + "" + b3
						+ "" + b2 + "" + b1 + "" + b0;

				stringRow = stringRow + alpha;

			}

		}
		return stringRow;
		
	}
	
	public void writeRawBinaryTxtFile(File txtFile) throws IOException {

		BufferedWriter txtWriter = new BufferedWriter(new FileWriter(txtFile));

		for (int y = 0; y < rows; y++) {

			String line = flattenRow(y);
			
			txtWriter.write(line + "\n");
		}
		txtWriter.close();

	}
	
	public void setPixelFromBitString(int x, int y, String bitString)
	{
		int index = 0;
		for (int k = 0; k < colorDepth; k++) {
			for (int pos = 0; pos < bitDepth ; pos++) {
				int bit = Integer.valueOf(bitString.substring(index, index+1));
				// setting bits from left to right, then 7-pos
				setBit(x, y, k, 7-pos, bit);
				index = index+1;				
			}			
		}
	}
	
	
	public void setRowFromBitLine (String bitLine,int rowIndex)
	{
		int numberOfBitsInApixel = colorDepth*bitDepth;
		// a vector of pixels formatted in RGB bits strings
		String[] stringVector = Tools.getStringVector(bitLine, cols, numberOfBitsInApixel);
		for (int x = 0; x < cols; x++) {
			setPixelFromBitString(x,rowIndex,stringVector[x]);		
		}
		
		
	}

}
