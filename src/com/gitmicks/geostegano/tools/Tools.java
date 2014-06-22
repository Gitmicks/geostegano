package com.gitmicks.geostegano.tools;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.gitmicks.logging.Logging;


public class Tools {
	
	

	public static String byteToBits(Byte b) {

		return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(
				' ', '0');
	}

	public static int getLSB8(Byte b) {
		String s = byteToBits(b);
		return Integer.parseInt(s.substring(s.length()-1, s.length())) ;
	}

	public static int getMSB8(Byte b) {
		String s = byteToBits(b);
		return Integer.parseInt(s.substring(s.length()-8, s.length()-7)) ;
	}

	public static int getnLSB8(Byte b, int n) {
		// n=1 : LSB
		// n=8 : MSB
		String s = byteToBits(b);
		return Integer.parseInt(s.substring(s.length()-n, s.length()-n+1)) ;
	}

	public static String intToBits(int integer) {

		return String.format("%8s", Integer.toBinaryString(integer & 0xFF)).replace(
				' ', '0');

	}

	public static int bitsToInt(String s) {

		return Integer.parseInt(s, 2);
		
	}
	
	
	public static int getLSB8(int integer) {
		String s = intToBits(integer);
		return Integer.parseInt(s.substring(s.length()-1, s.length())) ;
	}
	
	public static String buildString(String inputLine, int position, int step)
	{
		String result = "";
		 
		int length = inputLine.length();
		
		while (length > position) {
			
			result = result + inputLine.substring(position, position+1);
			position = position + step;
		}
		
		return result;
		
	}
	
	public static String[] getStringVector (String line, int vectorSize, int stringLength)
	{
			
		String[] stringVector = new String[vectorSize]; 		
		int index = 0;
		for (int x = 0; x < vectorSize; x++) {	
			stringVector[x] = line.substring(index, index+stringLength);
			index = index + stringLength;
		}
		return stringVector;
	}
	
	public static String getASCIIcharacters (String bitString)
	{
		Logging.logger.debug("Tools.getASCIIcharacters ");
		String result = "";
		
		int length = bitString.length();
		int numberOfCharacters = (length - (length % 8)) / 8;
		int index = 0;
		for (int i = 0; i < numberOfCharacters; i++) {
						
			result = result + (char)Integer.parseInt(bitString.substring(index, index+8), 2);
			
			index = index+8;
		}
		
		return result;
		
	}

	
	public static String AsciiToBinary(String asciiString){  

        byte[] bytes = asciiString.getBytes();  
        StringBuilder binary = new StringBuilder();  
        for (byte b : bytes)  
        {  
           int val = b;  
           for (int i = 0; i < 8; i++)  
           {  
              binary.append((val & 128) == 0 ? 0 : 1);  
              val <<= 1;  
           }  
          // binary.append(' ');  
        }  
        return binary.toString();  
  }  

	public static List<String> textFileToStringList(File inputFile) throws IOException
	{
		return Files.readAllLines(Paths.get(inputFile.getPath()), StandardCharsets.ISO_8859_1);
	}
	
	
}
