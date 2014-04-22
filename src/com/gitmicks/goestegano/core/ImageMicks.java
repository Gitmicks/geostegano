package com.gitmicks.goestegano.core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;

import com.gitmicks.geostegano.tools.Tools;

public class ImageMicks {

	private int rows = 0;
	private int cols = 0;
	private int bitDepth = 8;
	private String name = "";
	final static private int RGB = 3;
	final static private int R = 0;
	final static private int G = 1;
	final static private int B = 2;
	final static private int LSB = 0;

	private int[][][][] bitmatrix;

	public ImageMicks() {

	}

	public ImageMicks(File pFile) throws ImageReadException, IOException {

		loadImage(pFile);
	}

	public void writeImage(File pFile) throws IOException {
		final BufferedImage imageOut = new BufferedImage(cols, rows,
				BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				
				imageOut.setRGB(j, i, getRGB(j,i));
				
			}
		}

		ImageIO.write(imageOut, "png", pFile);
	}

	public int getRGB(int pCol, int pRow) {

		String rInBits = "";
		String gInBits = "";
		String bInBits = "";			

		
		for (int k = 0; k < bitDepth; k++) {
			rInBits = rInBits + bitmatrix[pCol][pRow][ImageMicks.R][bitDepth-1-k];
			gInBits = gInBits + bitmatrix[pCol][pRow][ImageMicks.G][bitDepth-1-k];
			bInBits = bInBits + bitmatrix[pCol][pRow][ImageMicks.B][bitDepth-1-k];
		}
				
		int r = Tools.bitsToInt(rInBits);
		int g = Tools.bitsToInt(gInBits);
		int b = Tools.bitsToInt(bInBits);
		
		Color c = new Color(r,g,b);
		return c.getRGB();
	}

	public BufferedImage loadImage(File pFile) throws ImageReadException,
			IOException {

		final Map<String, Object> params = new HashMap<String, Object>();

		final BufferedImage image = Imaging.getBufferedImage(pFile, params);
		cols = image.getWidth();
		rows = image.getHeight();
		name = pFile.getName();
		bitmatrix = new int[cols][rows][ImageMicks.RGB][bitDepth];
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				Color c = new Color(image.getRGB(j, i));
				
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				setRGB(j, i, r, g, b);
			}
		}
		return image;
	}

	public void setRGB(int pCol, int pRow, int r, int g, int b) {
		setR(pCol, pRow, r);
		setG(pCol, pRow, g);
		setB(pCol, pRow, b);
	}

	public void setR(int pCol, int pRow, int rgb) {
		String rgbInBits = Tools.intToBits(rgb);
		for (int j = 0; j < bitDepth ; j++) {
			
			bitmatrix[pCol][pRow][ImageMicks.R][j] = Integer.parseInt(rgbInBits
					.substring(bitDepth-1-j, bitDepth-j));
		}
	}

	public void setG(int pCol, int pRow, int rgb) {
		String rgbInBits = Tools.intToBits(rgb);
		for (int j = 0; j < bitDepth ; j++) {
			bitmatrix[pCol][pRow][ImageMicks.G][j] = Integer.parseInt(rgbInBits
					.substring(bitDepth-1-j, bitDepth-j));
		}
	}

	public void setB(int pCol, int pRow, int rgb) {
		String rgbInBits = Tools.intToBits(rgb);
		for (int j = 0; j < bitDepth ; j++) {
			bitmatrix[pCol][pRow][ImageMicks.B][j] = Integer.parseInt(rgbInBits
					.substring(bitDepth-1-j, bitDepth-j));
		}
	}
}
