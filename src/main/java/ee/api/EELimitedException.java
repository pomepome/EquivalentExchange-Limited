package ee.api;

public class EELimitedException extends Exception
{
	public EELimitedException()
	{
		super();
	}
	public EELimitedException(String message)
	{
		super(message);
	}
	public EELimitedException(String message,Throwable cause)
	{
		super(message,cause);
	}
}
