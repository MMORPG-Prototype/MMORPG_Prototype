package pl.mmorpg.prototype.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.ObjectsFactory;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;

public class ClientListener extends Listener
{

	private Client client;
	private PlayState playState;

	public ClientListener(Client client, PlayState playState)
	{
		this.client = client;
		this.playState = playState;
	}

	@Override
	public void connected(Connection connection)
	{
		playState.initialize(client);
		Log.info("Connected to server, id: " + connection.getID());
	}

	@Override
	public void disconnected(Connection connection)
	{
		Log.info("Disconnected from server, id: " + connection.getID());
	}

	@Override
	public void received(Connection connection, Object object)
	{
		if (object instanceof ObjectRepositionPacket)
		{
			ObjectRepositionPacket packet = (ObjectRepositionPacket) object;
			GameObject operationTarget = playState.getObject(packet.id);
			operationTarget.setX(packet.x);
			operationTarget.setY(packet.y);
		}
		else if (object instanceof ObjectCreationPacket)
		{
			ObjectCreationPacket packet = (ObjectCreationPacket) object;
			GameObject newObject = ObjectsFactory.produce(packet);
			playState.add(newObject);
		}
		else if (object instanceof ObjectRemovePacket)
		{
			ObjectRemovePacket packet = (ObjectRemovePacket) object;
			playState.removeObject(packet.id);
		}
		Log.info("Packet received from server, id: " + connection.getID() + "packet: " + object);
	}

}
