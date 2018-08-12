package pl.mmorpg.prototype.client.exceptions;

import com.esotericsoftware.minlog.Log;

import java.util.Arrays;

public class ExceptionHandler
{
	public static void handle(Runnable runnableWichThrowsExceptions)
	{
		try
		{
			runnableWichThrowsExceptions.run();
		} catch (Exception e)
		{
			handle(e);
		}
	}

	public static void handle(Exception e, Object... data)
	{
		Log.error("Could not handle process operation with data: " + Arrays.toString(data));
		e.printStackTrace();
	}
}
