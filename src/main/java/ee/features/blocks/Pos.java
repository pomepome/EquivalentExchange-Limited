package ee.features.blocks;

public class Pos
{
	private double px,py,pz;
	public Pos()
	{
		px = py = pz = 0;
	}
	public void setPos(double x,double y,double z)
	{
		px = x;py = y;pz = z;
	}
	public double getX()
	{
		return px;
	}
	public double getY()
	{
		return py;
	}
	public double getZ()
	{
		return pz;
	}
}
