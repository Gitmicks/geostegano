package com.gitmicks.goestegano.core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gitmicks.geostegano.tools.Tools;
import com.sun.imageio.plugins.png.PNGMetadata;

public class ImageMicks {

	final static protected int B = 2;
	final static protected int G = 1;
	final static protected int LSB = 0;
	final static protected int R = 0;
	final static protected int RGB = 3;
	protected int bitDepth = 8;

	protected int[][][][] bitmatrix;
	protected BufferedImage buffImage;
	protected int cols = 0;
	protected File inputFile;
	protected String name = "";

	protected int rows = 0;

	public ImageMicks() {

	}

	public ImageMicks(File pFile) throws ImageReadException, IOException {

		inputFile = pFile;
		buffImage = loadImage();
	}

	public void bitInversion() {

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < ImageMicks.RGB; k++) {

					int b0 = bitmatrix[j][i][k][0];
					int b1 = bitmatrix[j][i][k][1];
					int b2 = bitmatrix[j][i][k][2];
					int b3 = bitmatrix[j][i][k][3];
					int b4 = bitmatrix[j][i][k][4];
					int b5 = bitmatrix[j][i][k][5];
					int b6 = bitmatrix[j][i][k][6];
					int b7 = bitmatrix[j][i][k][7];

					if (b0 == 0) {
						bitmatrix[j][i][k][0] = 1;
					} else {
						bitmatrix[j][i][k][0] = 0;
					}

					if (b1 == 0) {
						bitmatrix[j][i][k][1] = 1;
					} else {
						bitmatrix[j][i][k][1] = 0;
					}

					if (b2 == 0) {
						bitmatrix[j][i][k][2] = 1;
					} else {
						bitmatrix[j][i][k][2] = 0;
					}

					if (b3 == 0) {
						bitmatrix[j][i][k][3] = 1;
					} else {
						bitmatrix[j][i][k][3] = 0;
					}

					if (b4 == 0) {
						bitmatrix[j][i][k][4] = 1;
					} else {
						bitmatrix[j][i][k][4] = 0;
					}

					if (b5 == 0) {
						bitmatrix[j][i][k][5] = 1;
					} else {
						bitmatrix[j][i][k][5] = 0;
					}

					if (b6 == 0) {
						bitmatrix[j][i][k][6] = 1;
					} else {
						bitmatrix[j][i][k][6] = 0;
					}

					if (b7 == 0) {
						bitmatrix[j][i][k][7] = 1;
					} else {
						bitmatrix[j][i][k][7] = 0;
					}

				}
			}
		}

	}

	public void bitRandomize() {

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < ImageMicks.RGB; k++) {

					int b0 = bitmatrix[j][i][k][0];
					int b1 = bitmatrix[j][i][k][1];
					int b2 = bitmatrix[j][i][k][2];
					int b3 = bitmatrix[j][i][k][3];
					int b4 = bitmatrix[j][i][k][4];
					int b5 = bitmatrix[j][i][k][5];
					int b6 = bitmatrix[j][i][k][6];
					int b7 = bitmatrix[j][i][k][7];

					// TODO

					if (b0 == 0) {
						bitmatrix[j][i][k][0] = 0;
					} else {
						bitmatrix[j][i][k][0] = 1;
					}

					if (b1 == 1) {
						bitmatrix[j][i][k][1] = 0;
					} else {
						bitmatrix[j][i][k][1] = 1;
					}

					if (b2 == 0) {
						bitmatrix[j][i][k][2] = 0;
					} else {
						bitmatrix[j][i][k][2] = 1;
					}

					if (b3 == 0) {
						bitmatrix[j][i][k][3] = 0;
					} else {
						bitmatrix[j][i][k][3] = 1;
					}

					if (b4 == 0) {
						bitmatrix[j][i][k][4] = 0;
					} else {
						bitmatrix[j][i][k][4] = 1;
					}

					if (b5 == 0) {
						bitmatrix[j][i][k][5] = 0;
					} else {
						bitmatrix[j][i][k][5] = 1;
					}

					if (b6 == 0) {
						bitmatrix[j][i][k][6] = 1;
					} else {
						bitmatrix[j][i][k][6] = 0;
					}

					if (b7 == 0) {
						bitmatrix[j][i][k][7] = 1;
					} else {
						bitmatrix[j][i][k][7] = 0;
					}

				}
			}
		}

	}

	public void BtoR() {

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {

				bitmatrix[j][i][ImageMicks.G][0] = bitmatrix[j][i][ImageMicks.B][0];
				bitmatrix[j][i][ImageMicks.G][1] = bitmatrix[j][i][ImageMicks.B][1];
				bitmatrix[j][i][ImageMicks.G][2] = bitmatrix[j][i][ImageMicks.B][2];
				bitmatrix[j][i][ImageMicks.G][3] = bitmatrix[j][i][ImageMicks.B][3];
				bitmatrix[j][i][ImageMicks.G][4] = bitmatrix[j][i][ImageMicks.B][4];
				bitmatrix[j][i][ImageMicks.G][5] = bitmatrix[j][i][ImageMicks.B][5];
				bitmatrix[j][i][ImageMicks.G][6] = bitmatrix[j][i][ImageMicks.B][6];
				bitmatrix[j][i][ImageMicks.G][7] = bitmatrix[j][i][ImageMicks.B][7];

			}
		}

	}

	public void compression(int lRatio, int cRatio, int pBit) {

		int i = 0;
		for (int indexL = 0; indexL < rows / lRatio; indexL++) {

			int j = 0;
			for (int indexC = 0; indexC < cols / cRatio; indexC = indexC + 1) {

				for (int k = 0; k < 3; k++) {
					bitmatrix[j][i][k][0] = bitmatrix[indexC * cRatio][indexL
							* lRatio][k][pBit];
					bitmatrix[j][i][k][1] = bitmatrix[indexC * cRatio][indexL
							* lRatio][k][pBit];
					bitmatrix[j][i][k][2] = bitmatrix[indexC * cRatio][indexL
							* lRatio][k][pBit];
					bitmatrix[j][i][k][3] = bitmatrix[indexC * cRatio][indexL
							* lRatio][k][pBit];
					bitmatrix[j][i][k][4] = bitmatrix[indexC * cRatio][indexL
							* lRatio][k][pBit];
					bitmatrix[j][i][k][5] = bitmatrix[indexC * cRatio][indexL
							* lRatio][k][pBit];
					bitmatrix[j][i][k][6] = bitmatrix[indexC * cRatio][indexL
							* lRatio][k][pBit];
					bitmatrix[j][i][k][7] = bitmatrix[indexC * cRatio][indexL
							* lRatio][k][pBit];

				}
				j++;

			}
			i++;
		}

	}

	public void displayMetadata() throws IOException {

		ImageInputStream iis = ImageIO.createImageInputStream(inputFile);
		Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

		if (readers.hasNext()) {

			// pick the first available ImageReader
			ImageReader reader = readers.next();

			// attach source to the reader
			reader.setInput(iis, true);

			// read metadata of first image
			IIOMetadata metadata = reader.getImageMetadata(0);

			String[] names = metadata.getMetadataFormatNames();
			int length = names.length;
			for (int i = 0; i < length; i++) {
				System.out.println("Format name: " + names[i]);
				displayMetadata(metadata.getAsTree(names[i]));
			}
		}

	}

	private void displayMetadata(Node root) {
		displayMetadata(root, 0);
	}

	private void displayMetadata(Node node, int level) {
		// print open tag of element
		indent(level);
		System.out.print("<" + node.getNodeName());
		NamedNodeMap map = node.getAttributes();
		if (map != null) {

			// print attribute values
			int length = map.getLength();
			for (int i = 0; i < length; i++) {
				Node attr = map.item(i);
				System.out.print(" " + attr.getNodeName() + "=\""
						+ attr.getNodeValue() + "\"");
			}
		}

		Node child = node.getFirstChild();
		if (child == null) {
			// no children, so close element and return
			System.out.println("/>");
			return;
		}

		// children, so close current tag
		System.out.println(">");
		while (child != null) {
			// print children recursively
			displayMetadata(child, level + 1);
			child = child.getNextSibling();
		}

		// print close tag of element
		indent(level);
		System.out.println("</" + node.getNodeName() + ">");

	}

	public BufferedImage getBuffImage() {
		return buffImage;
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

		Color c = new Color(r, g, b);
		return c.getRGB();
	}

	private void indent(int level) {
		for (int i = 0; i < level; i++)
			System.out.print("    ");
	}

	public BufferedImage loadImage() throws ImageReadException, IOException {

		// final Map<String, Object> params = new HashMap<String, Object>();

		// final BufferedImage image = Imaging.getBufferedImage(inputFile,
		// params);
		final BufferedImage image = ImageIO.read(inputFile);
		cols = image.getWidth();
		rows = image.getHeight();
		name = inputFile.getName();
		bitmatrix = new int[cols][rows][ImageMicks.RGB][bitDepth];
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				int pixel = image.getRGB(j, i);
				Color c = new Color(pixel);
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				setRGB(j, i, r, g, b);
			}
		}
		return image;
	}

	public String readCustomData(byte[] imageData, String key)
			throws IOException {
		ImageReader imageReader = ImageIO.getImageReadersByFormatName("png")
				.next();

		imageReader.setInput(ImageIO
				.createImageInputStream(new ByteArrayInputStream(imageData)),
				true);

		// read metadata of first image
		IIOMetadata metadata = imageReader.getImageMetadata(0);

		// this cast helps getting the contents
		PNGMetadata pngmeta = (PNGMetadata) metadata;
		NodeList childNodes = pngmeta.getStandardTextNode().getChildNodes();

		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			String keyword = node.getAttributes().getNamedItem("keyword")
					.getNodeValue();
			String value = node.getAttributes().getNamedItem("value")
					.getNodeValue();
			if (key.equals(keyword)) {
				return value;
			}
		}
		return null;
	}

	public void setB(int pCol, int pRow, int rgb) {
		String rgbInBits = Tools.intToBits(rgb);
		for (int j = 0; j < bitDepth; j++) {
			bitmatrix[pCol][pRow][ImageMicks.B][j] = Integer.parseInt(rgbInBits
					.substring(bitDepth - 1 - j, bitDepth - j));
		}
	}

	public void setBit0() {

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < ImageMicks.RGB; k++) {

					int b0 = bitmatrix[j][i][k][0];
					int b1 = bitmatrix[j][i][k][1];
					int b2 = bitmatrix[j][i][k][2];
					int b3 = bitmatrix[j][i][k][3];
					int b4 = bitmatrix[j][i][k][4];
					int b5 = bitmatrix[j][i][k][5];
					int b6 = bitmatrix[j][i][k][6];
					int b7 = bitmatrix[j][i][k][7];

					bitmatrix[j][i][k][0] = b0;
					bitmatrix[j][i][k][1] = b0;
					bitmatrix[j][i][k][2] = b0;
					bitmatrix[j][i][k][3] = b0;
					bitmatrix[j][i][k][4] = b0;
					bitmatrix[j][i][k][5] = b0;
					bitmatrix[j][i][k][6] = b0;
					bitmatrix[j][i][k][7] = b0;

				}
			}
		}

	}

	public void setBit1() {

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < ImageMicks.RGB; k++) {

					int b0 = bitmatrix[j][i][k][0];
					int b1 = bitmatrix[j][i][k][1];
					int b2 = bitmatrix[j][i][k][2];
					int b3 = bitmatrix[j][i][k][3];
					int b4 = bitmatrix[j][i][k][4];
					int b5 = bitmatrix[j][i][k][5];
					int b6 = bitmatrix[j][i][k][6];
					int b7 = bitmatrix[j][i][k][7];

					bitmatrix[j][i][k][0] = b1;
					bitmatrix[j][i][k][1] = b1;
					bitmatrix[j][i][k][2] = b1;
					bitmatrix[j][i][k][3] = b1;
					bitmatrix[j][i][k][4] = b1;
					bitmatrix[j][i][k][5] = b1;
					bitmatrix[j][i][k][6] = b1;
					bitmatrix[j][i][k][7] = b1;

				}
			}
		}

	}

	public void setBit4() {

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < ImageMicks.RGB; k++) {

					int b0 = bitmatrix[j][i][k][0];
					int b1 = bitmatrix[j][i][k][1];
					int b2 = bitmatrix[j][i][k][2];
					int b3 = bitmatrix[j][i][k][3];
					int b4 = bitmatrix[j][i][k][4];
					int b5 = bitmatrix[j][i][k][5];
					int b6 = bitmatrix[j][i][k][6];
					int b7 = bitmatrix[j][i][k][7];

					bitmatrix[j][i][k][0] = b1;
					bitmatrix[j][i][k][1] = b4;
					bitmatrix[j][i][k][2] = b4;
					bitmatrix[j][i][k][3] = b4;
					bitmatrix[j][i][k][4] = b4;
					bitmatrix[j][i][k][5] = b4;
					bitmatrix[j][i][k][6] = b4;
					bitmatrix[j][i][k][7] = b4;

				}
			}
		}

	}

	public void setBuffImage(BufferedImage buffImage) {
		this.buffImage = buffImage;
	}

	public void setG(int pCol, int pRow, int rgb) {
		String rgbInBits = Tools.intToBits(rgb);
		for (int j = 0; j < bitDepth; j++) {
			bitmatrix[pCol][pRow][ImageMicks.G][j] = Integer.parseInt(rgbInBits
					.substring(bitDepth - 1 - j, bitDepth - j));
		}
	}

	public void setLSBtoMSB() {

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < ImageMicks.RGB; k++) {
					bitmatrix[j][i][k][7] = bitmatrix[j][i][k][0];
				}
			}
		}
	}

	public void setR(int pCol, int pRow, int rgb) {
		String rgbInBits = Tools.intToBits(rgb);
		for (int j = 0; j < bitDepth; j++) {

			bitmatrix[pCol][pRow][ImageMicks.R][j] = Integer.parseInt(rgbInBits
					.substring(bitDepth - 1 - j, bitDepth - j));
		}
	}

	public void setRGB(int pCol, int pRow, int r, int g, int b) {
		setR(pCol, pRow, r);
		setG(pCol, pRow, g);
		setB(pCol, pRow, b);
	}

	public void setTest() {

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < ImageMicks.RGB; k++) {

					int b0 = bitmatrix[j][i][k][0];
					int b1 = bitmatrix[j][i][k][1];
					int b2 = bitmatrix[j][i][k][2];
					int b3 = bitmatrix[j][i][k][3];
					int b4 = bitmatrix[j][i][k][4];
					int b5 = bitmatrix[j][i][k][5];
					int b6 = bitmatrix[j][i][k][6];
					int b7 = bitmatrix[j][i][k][7];

					// bitmatrix[j][i][k][0] = b0;
					// bitmatrix[j][i][k][1] = b1;
					// bitmatrix[j][i][k][2] = b2;
					// bitmatrix[j][i][k][3] = b3;
					// bitmatrix[j][i][k][4] = b4;
					// bitmatrix[j][i][k][5] = b5;
					// bitmatrix[j][i][k][6] = b6;
					// bitmatrix[j][i][k][7] = b7;

					bitmatrix[j][i][k][0] = b0;
					bitmatrix[j][i][k][1] = b1;
					bitmatrix[j][i][k][2] = b2;
					bitmatrix[j][i][k][3] = b3;
					bitmatrix[j][i][k][4] = b4;
					bitmatrix[j][i][k][5] = b5;
					bitmatrix[j][i][k][6] = b6;

				}
			}
		}

	}

	public void setTestComparaison() {

		int indexOK = 0;
		int indexKO = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < ImageMicks.RGB; k++) {

					int b0 = bitmatrix[j][i][k][0];
					int b1 = bitmatrix[j][i][k][1];
					int b2 = bitmatrix[j][i][k][2];
					int b3 = bitmatrix[j][i][k][3];
					int b4 = bitmatrix[j][i][k][4];
					int b5 = bitmatrix[j][i][k][5];
					int b6 = bitmatrix[j][i][k][6];
					int b7 = bitmatrix[j][i][k][7];

					bitmatrix[j][i][k][0] = b0;
					bitmatrix[j][i][k][1] = b1;
					bitmatrix[j][i][k][2] = b2;
					bitmatrix[j][i][k][3] = b3;
					bitmatrix[j][i][k][4] = b4;
					bitmatrix[j][i][k][5] = b5;
					bitmatrix[j][i][k][6] = b6;
					bitmatrix[j][i][k][7] = b7;

					if (b7 == b0) {
						bitmatrix[j][i][k][7] = 1;
						indexOK++;
					} else {
						indexKO++;
						bitmatrix[j][i][k][0] = 0;
						bitmatrix[j][i][k][1] = 0;
						bitmatrix[j][i][k][2] = 0;
						bitmatrix[j][i][k][3] = 0;
						bitmatrix[j][i][k][4] = 0;
						bitmatrix[j][i][k][5] = 0;
						bitmatrix[j][i][k][6] = 0;
						bitmatrix[j][i][k][7] = 0;
					}

				}

			}
		}
		System.out.println(indexOK);
		System.out.println(indexKO);

	}

	public void shift1() {

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < ImageMicks.RGB; k++) {

					int b0 = bitmatrix[j][i][k][0];
					int b1 = bitmatrix[j][i][k][1];
					int b2 = bitmatrix[j][i][k][2];
					int b3 = bitmatrix[j][i][k][3];
					int b4 = bitmatrix[j][i][k][4];
					int b5 = bitmatrix[j][i][k][5];
					int b6 = bitmatrix[j][i][k][6];
					int b7 = bitmatrix[j][i][k][7];

					bitmatrix[j][i][k][0] = b7;
					bitmatrix[j][i][k][1] = b0;
					bitmatrix[j][i][k][2] = b1;
					bitmatrix[j][i][k][3] = b2;
					bitmatrix[j][i][k][4] = b3;
					bitmatrix[j][i][k][5] = b4;
					bitmatrix[j][i][k][6] = b5;
					bitmatrix[j][i][k][7] = b6;

				}
			}
		}

	}

	public void test() {

		for (int i = 0; i < rows; i++) {

			int index = 0;
			for (int j = 0; j < 50; j++) {
				for (int k = 0; k < ImageMicks.RGB; k++) {
					bitmatrix[j][i][k][0] = bitmatrix[index][i][k][0];
					bitmatrix[j][i][k][1] = bitmatrix[index + 1][i][k][0];
					bitmatrix[j][i][k][2] = bitmatrix[index + 2][i][k][0];
					bitmatrix[j][i][k][3] = bitmatrix[index + 3][i][k][0];
					bitmatrix[j][i][k][4] = bitmatrix[index + 4][i][k][0];
					bitmatrix[j][i][k][5] = bitmatrix[index + 5][i][k][0];
					bitmatrix[j][i][k][6] = bitmatrix[index + 6][i][k][0];
					bitmatrix[j][i][k][7] = bitmatrix[index + 7][i][k][0];

				}
				index = index + 12;
			}
		}

	}

	public void testAlpha() {

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < ImageMicks.RGB; k++) {

					int b0 = bitmatrix[j][i][k][0];
					int b1 = bitmatrix[j][i][k][1];
					int b2 = bitmatrix[j][i][k][2];
					int b3 = bitmatrix[j][i][k][3];
					int b4 = bitmatrix[j][i][k][4];
					int b5 = bitmatrix[j][i][k][5];
					int b6 = bitmatrix[j][i][k][6];
					int b7 = bitmatrix[j][i][k][7];

					bitmatrix[j][i][k][0] = b0;
					bitmatrix[j][i][k][1] = b1;
					bitmatrix[j][i][k][2] = b2;
					bitmatrix[j][i][k][3] = b3;
					bitmatrix[j][i][k][4] = b4;
					bitmatrix[j][i][k][5] = b5;
					bitmatrix[j][i][k][6] = b6;
					bitmatrix[j][i][k][7] = b7;

					String alpha = b7 + "" + b6 + "" + b5 + "" + b4 + "" + b3
							+ "" + b2 + "" + b1 + "" + b0;
					if ((i == 0)
							&& (Integer.parseInt(alpha) >= 110000 && Integer
									.parseInt(alpha) <= 111001)) {
						System.out.println(alpha + " " + i + " " + j + " " + k
								+ " " + (Tools.bitsToInt(alpha) - 48));
					}

				}

			}

		}

	}

	public void writeBit0ToBitmatrix() throws IOException {

		int[][][][] newbitmatrix = new int[cols][rows][ImageMicks.RGB][bitDepth];

		for (int i = 0; i < rows; i++) {

			int jj = 0;
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < ImageMicks.RGB; k++) {
					int b0 = bitmatrix[j][i][k][0];

					// TODO
					// newbitmatrix[jj][i][k][0]

				}

			}
		}

	}

	public byte[] writeCustomData(String key, String value) throws Exception {
		ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();

		ImageWriteParam writeParam = writer.getDefaultWriteParam();
		ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier
				.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);

		// adding metadata
		IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier,
				writeParam);

		IIOMetadataNode textEntry = new IIOMetadataNode("tEXtEntry");
		textEntry.setAttribute("keyword", key);
		textEntry.setAttribute("value", value);

		IIOMetadataNode text = new IIOMetadataNode("tEXt");
		text.appendChild(textEntry);

		IIOMetadataNode root = new IIOMetadataNode("javax_imageio_png_1.0");
		root.appendChild(text);

		metadata.mergeTree("javax_imageio_png_1.0", root);

		// writing the data
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageOutputStream stream = ImageIO.createImageOutputStream(baos);
		writer.setOutput(stream);
		writer.write(metadata, new IIOImage(buffImage, null, metadata),
				writeParam);
		stream.close();

		return baos.toByteArray();
	}

	public void writeImage(File pFile) throws IOException {
		final BufferedImage imageOut = new BufferedImage(cols, rows,
				BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {

				imageOut.setRGB(j, i, getRGB(j, i));

			}
		}

		ImageIO.write(imageOut, "png", pFile);
	}

	
	public void writeImage(File pFile, Bitmatrix bm) throws IOException {
		final BufferedImage imageOut = new BufferedImage(bm.getCols(), bm.getRows(),
				BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < bm.getRows(); i++) {
			System.out.println("writeImage row "+i);
			for (int j = 0; j < bm.getCols(); j++) {

				imageOut.setRGB(j, i, bm.getRGB(j, i));

			}
		}

		ImageIO.write(imageOut, "png", pFile);
	}
	
	public void writeRawBinaryTxtFile(File txtFile) throws IOException {

		BufferedWriter txtWriter = new BufferedWriter(new FileWriter(txtFile));

		for (int i = 0; i < rows; i++) {

			String line = "";
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < ImageMicks.RGB; k++) {

					int b0 = bitmatrix[j][i][k][0];
					int b1 = bitmatrix[j][i][k][1];
					int b2 = bitmatrix[j][i][k][2];
					int b3 = bitmatrix[j][i][k][3];
					int b4 = bitmatrix[j][i][k][4];
					int b5 = bitmatrix[j][i][k][5];
					int b6 = bitmatrix[j][i][k][6];
					int b7 = bitmatrix[j][i][k][7];

					String alpha = b7 + "" + b6 + "" + b5 + "" + b4 + "" + b3
							+ "" + b2 + "" + b1 + "" + b0;

					line = line + alpha;

				}

			}
			txtWriter.write(line + "\n");
		}
		txtWriter.close();

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

}
