package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import java.util.function.Supplier;

public class DialogIdSupplier implements Supplier<Integer>
{
	private int currentId = 0;

	@Override
	public Integer get()
	{
		currentId++;
		return currentId;
	}

}
