package com.gitmicks.geostegano;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

import ar.com.hjg.pngj.ChunkReader;
import ar.com.hjg.pngj.IImageLine;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineByte;
import ar.com.hjg.pngj.ImageLineHelper;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngReader;
import ar.com.hjg.pngj.PngReaderByte;
import ar.com.hjg.pngj.PngWriter;
import ar.com.hjg.pngj.chunks.ChunksList;
import ar.com.hjg.pngj.chunks.PngChunk;
import ar.com.hjg.pngj.chunks.PngChunkITXT;
import ar.com.hjg.pngj.chunks.PngChunkTEXT;
import ar.com.hjg.pngj.chunks.PngMetadata;

import javax.imageio.ImageIO;

public class DecodImage
{

	private static void writeBooleans(OutputStream out, List<Boolean> ar) throws IOException
	{
		for (int i = 0; i < ar.size(); i += 8)
		{
			int b = 0;
			for (int j = Math.min(i + 7, ar.size() - 1); j >= i; j--)
			{
				b = (b << 1) | (ar.get(j) ? 1 : 0);
			}
			out.write(b);
		}
	}

	private static boolean getBitValue(int n, int location)
	{
		int v = (n >> location) & 1;
		return v == 1;
	}

	private static int extractBits(int x, int numBits) {
	    if (numBits < 1) {
	        return 0;
	    }
	    if (numBits > 32) {
	        return x;
	    }
	    int mask = (1 << numBits) - 1;
	    return x & mask;			
	}	
	
	private static int getLSB(int x, int numBits) 
	{
		return x & 1 << numBits - 1;
	}	
	
	private static int getLSBn(int x, int n) 
	{
		String s = Integer.toBinaryString(x);
		if (s.length() < n+1) 
			
			return 0;
		else
		
		return Integer.parseInt(s.substring(s.length()-n-1, s.length()-n)) ;
		
	}	

	
	
	private static int buildRGB1(int x) 
	{
		
		String s = new String();
		s = x + "00000000" ;
		int result =  Integer.parseInt(s, 2);
		
		if (result == 256) {
			result = 255;
		}
		else
		{
			result = 0;
				
		}
		
		return result;		
	}	
	
	
	
	private static void main1(String args[])
	{
		try
		{
			String filename = args[0];
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			PngReader reader = new PngReaderByte (classLoader.getResourceAsStream(filename));
			System.out.println(reader.imgInfo); 
			PngMetadata metadata = reader.getMetadata();
			System.out.println(metadata.getTimeAsString());
			System.out.println(metadata.getTxtForKey("tEXt"));
			ChunksList list = reader.getChunksList();
			//PngChunk chunk =  list.getById1("tEXt");
			
            System.out.println(list.toStringFull());
            PngChunkTEXT  text =  (PngChunkTEXT) list.getById1("tEXt");
			System.out.println(text.getKey());
			System.out.println(text.getVal());

//			 ImageLineByte line = (ImageLineByte) reader.readRow();
//			byte[] bytes = new byte[100000] ;
//			int len = 700;
//			int offset = 0;
//			int step = 0;
//			
//			System.out.println(line.getScanline().length);
//			for (int i = 0; i < line.getScanline().length; i++) {
//			
//				//System.out.println(line.getScanline()[i]);
//				Byte b = new Byte(line.getScanline()[i]);
//				System.out.println(Integer.toBinaryString(Byte.toUnsignedInt(b)));
//				System.out.println(Byte.toUnsignedInt(b));	
//			}; 
			
			ImageInfo info = new ImageInfo(778,796,8,false);
			
			FileOutputStream fOut = new FileOutputStream(new File("result.png"));
			
			PngWriter writer = new PngWriter(fOut,info);
			
			ImageLineByte line = (ImageLineByte) reader.readRow();
			for (int i = 0; i < 796; i++) {
			
				
				writer.writeRow(line);
				line = (ImageLineByte) reader.readRow();
			}
			writer.close();
			
			
			
			
			fOut.close();
			
//			for (int i = 0; i < bytes.length; i++) 
//				{
//					Byte b = new Byte(bytes[i]);
//					int btoi = Byte.toUnsignedInt(b);
//					String btos = Integer.toBinaryString(btoi);
//					System.out.println(bytes[i]);
//					System.out.println(btos);
//					System.out.println(btoi);
//					
//					System.out.println(getLSB(btoi,1));
//					System.out.println(buildRGB(getLSB(btoi,1)));
//				
//					System.out.println("---");
//					
//				}
			
		
//			line.readFromPngRaw(bytes, len, offset, step);
//			System.out.println(bytes.length);
//			
//			for (int i = 0; i < 100; i++) {
//				byte b = bytes[i];
//				
//				System.out.println(b);
//			}
//			
			 
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Some problem has occurred. Please try again");
		}
	
	}
	
	
	public static void main(String args[])
	{

		try
		{
			String filename = args[0];
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			PngReader reader = new PngReaderByte (classLoader.getResourceAsStream(filename));
			System.out.println(reader.imgInfo); 

			
			ImageInfo info = new ImageInfo(778,796,8,false);
			
			FileOutputStream fOut = new FileOutputStream(new File("result.png"));
			
			PngWriter writer = new PngWriter(fOut,info);
			
			ImageLineByte lineIn = (ImageLineByte) reader.readRow();
			
			ImageLineInt lineOut = new ImageLineInt(info);
			
			
			ImageLineInt[] lineOutArray =  new ImageLineInt[796];
			
			
			
			for (int i = 0; i < 796; i++) {
			
				
				byte[] bytesIn =  lineIn.getScanlineByte();
				//System.out.println("bytes length " + bytesIn.length);
				
				int index = 0;
				for (int j = 0; j < 778; j++) 
				{
					Byte b = new Byte(bytesIn[index]);
					int btoi = Byte.toUnsignedInt(b);
					//System.out.println(index+" " +btoi);
					//ImageLineHelper.setPixelRGB8(lineOut,j, buildRGB(getLSB(btoi,1)), buildRGB(getLSB(btoi,1)), buildRGB(getLSB(btoi,1)));
					//ImageLineHelper.setPixelRGB8(lineOut,j, buildRGB1(getLSB(btoi,1)), buildRGB1(getLSB(btoi,1)), buildRGB1(getLSB(btoi,1)));
					ImageLineHelper.setPixelRGB8(lineOut,j, buildRGB1(getLSBn(btoi,3)), buildRGB1(getLSBn(btoi,8)), buildRGB1(getLSBn(btoi,0)));
										
					index= index+3;
					
				}				
				
				
				lineOutArray[i] = lineOut;
				lineOut = new ImageLineInt(info);
				
				//writer.writeRow(lineOut);
				lineIn = (ImageLineByte) reader.readRow();
			}
			
			int index = 0;
			for (int i = 0; i < lineOutArray.length; i++) {
				
				writer.writeRow(lineOutArray[index]);
				index=index+1;
				
				//Random r = new Random(i);
				
				//writer.writeRow(lineOutArray[r.nextInt(795 - 0 + 1) + 0]);
			}
			
			writer.close();
			fOut.close();
			System.out.println("end of main");
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Some problem has occurred. Please try again");
		}
	}
}
