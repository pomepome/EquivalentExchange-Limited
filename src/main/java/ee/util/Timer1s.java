package ee.util;

public class Timer1s {
	private static int timer = 0;
	public static void Tick()
	{
		timer++;
		if(timer == 40)
		{
			timer = 0;
		}
	}
	public static boolean isTime()
	{
		return timer == 0;
	}
}
