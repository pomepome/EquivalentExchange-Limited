package ee.wailacompat;

public class ColorUtil
{
	private static String[] colors;
	private static String[] chatColors;
	public static void init()
	{
		colors = new String[]
			{
				"white","orange","magenta","light blue",
				"yellow","lime","pink","gray",
				"light glay","cyan","purple","blue",
				"brown","green","red","black"
			};
	}
	public static String getColorFromId(int id)
	{
		return colors[id];
	}
}
