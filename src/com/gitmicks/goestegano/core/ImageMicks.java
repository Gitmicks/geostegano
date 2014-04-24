package com.gitmicks.goestegano.core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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

	private File inputFile;
	private int rows = 0;
	private int cols = 0;
	private int bitDepth = 8;
	private String name = "";
	private BufferedImage buffImage;

	final static private int RGB = 3;
	final static private int R = 0;
	final static private int G = 1;
	final static private int B = 2;
	final static private int LSB = 0;

	private int[][][][] bitmatrix;

	public ImageMicks() {

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

	private void indent(int level) {
		for (int i = 0; i < level; i++)
			System.out.print("    ");
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

	public ImageMicks(File pFile) throws ImageReadException, IOException {

		inputFile = pFile;
		buffImage = loadImage();
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

	public BufferedImage loadImage() throws ImageReadException, IOException {

		final Map<String, Object> params = new HashMap<String, Object>();

		final BufferedImage image = Imaging.getBufferedImage(inputFile, params);
		cols = image.getWidth();
		rows = image.getHeight();
		name = inputFile.getName();
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
		for (int j = 0; j < bitDepth; j++) {

			bitmatrix[pCol][pRow][ImageMicks.R][j] = Integer.parseInt(rgbInBits
					.substring(bitDepth - 1 - j, bitDepth - j));
		}
	}

	public void setG(int pCol, int pRow, int rgb) {
		String rgbInBits = Tools.intToBits(rgb);
		for (int j = 0; j < bitDepth; j++) {
			bitmatrix[pCol][pRow][ImageMicks.G][j] = Integer.parseInt(rgbInBits
					.substring(bitDepth - 1 - j, bitDepth - j));
		}
	}

	public void setB(int pCol, int pRow, int rgb) {
		String rgbInBits = Tools.intToBits(rgb);
		for (int j = 0; j < bitDepth; j++) {
			bitmatrix[pCol][pRow][ImageMicks.B][j] = Integer.parseInt(rgbInBits
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

	public BufferedImage getBuffImage() {
		return buffImage;
	}

	public void setBuffImage(BufferedImage buffImage) {
		this.buffImage = buffImage;
	}
	
	
	public byte[] writeCustomData( String key, String value) throws Exception {
	    ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();

	    ImageWriteParam writeParam = writer.getDefaultWriteParam();
	    ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);

	    //adding metadata
	    IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);

	    IIOMetadataNode textEntry = new IIOMetadataNode("tEXtEntry");
	    textEntry.setAttribute("keyword", key);
	    textEntry.setAttribute("value", value);

	    IIOMetadataNode text = new IIOMetadataNode("tEXt");
	    text.appendChild(textEntry);

	    IIOMetadataNode root = new IIOMetadataNode("javax_imageio_png_1.0");
	    root.appendChild(text);

	    metadata.mergeTree("javax_imageio_png_1.0", root);

	    //writing the data
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageOutputStream stream = ImageIO.createImageOutputStream(baos);
	    writer.setOutput(stream);
	    writer.write(metadata, new IIOImage(buffImage, null, metadata), writeParam);
	    stream.close();

	    return baos.toByteArray();
	}
	
	public String readCustomData(byte[] imageData, String key) throws IOException{
	    ImageReader imageReader = ImageIO.getImageReadersByFormatName("png").next();

	    imageReader.setInput(ImageIO.createImageInputStream(new ByteArrayInputStream(imageData)), true);

	    // read metadata of first image
	    IIOMetadata metadata = imageReader.getImageMetadata(0);

	    //this cast helps getting the contents
	    PNGMetadata pngmeta = (PNGMetadata) metadata; 
	    NodeList childNodes = pngmeta.getStandardTextNode().getChildNodes();

	    for (int i = 0; i < childNodes.getLength(); i++) {
	        Node node = childNodes.item(i);
	        String keyword = node.getAttributes().getNamedItem("keyword").getNodeValue();
	        String value = node.getAttributes().getNamedItem("value").getNodeValue();
	        if(key.equals(keyword)){
	            return value;
	        }
	    }
	    return null;
	}
	
}
