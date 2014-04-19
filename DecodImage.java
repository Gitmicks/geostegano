import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

	public static void main(String args[])
	{
		try
		{

			
			BufferedImage image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Furashova.png"));
			//BufferedImage image = ImageIO.read(ClassLoader.getSystemResourceAsStream("c:\\geo\\out.png"));
			String tmp = "";
			for (String s : args)
				tmp += s + ".";

			List<Boolean> result = new ArrayList<Boolean>();

			int w = image.getWidth();
			int h = image.getHeight();

			for (int i = 0; i < h; i++)
			{
				for (int j = 0; j < w; j++)
				{
					int pixel = image.getRGB(j, i);
					for (String s : args)
					{
						int p = Integer.parseInt(s);
						result.add(getBitValue(pixel, p));
					}
				}
			}

			FileOutputStream fOut = new FileOutputStream(new File("result_" + tmp + "bin"));
			writeBooleans(fOut, result);
			fOut.close();
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Some problem has occurred. Please try again");
		}
	}
}
