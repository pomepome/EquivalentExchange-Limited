package ee.util;

public enum EnumSounds
{
	BREAK(0,"random.break"),
	CHARGE(1,"ee:items.charge"),
	ACTION(2,"ee:items.action"),
	UNCHARGE(3,"ee:items.uncharge"),
	TRANSMUTE(4,"ee:items.transmute"),
	HEAL(5,"ee:items.heal");
	
	private int id;
	private String soundPath;
	private EnumSounds(int index,String path)
	{
		id = index;
		soundPath = path;
	}
	public int getID()
	{
		return id;
	}
	public String getPath()
	{
		return soundPath;
	}
	public static EnumSounds getFromID(int id)
	{
		return values()[id];
	}
}
