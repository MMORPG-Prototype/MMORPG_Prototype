package pl.mmorpg.prototype.client.objects.interactive;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.resources.Assets;

public class QuestBoard extends InteractiveGameObject
{
	public QuestBoard(long id, PacketHandlerRegisterer registerer)
	{
		super(Assets.get("Static enviroment/questBoard.png"), id, registerer);
		setSize(32, 50);
	}
}
