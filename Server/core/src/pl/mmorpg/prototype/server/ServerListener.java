package pl.mmorpg.prototype.server;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveDownPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveLeftPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveRightPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveUpPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.MovableGameObject;
import pl.mmorpg.prototype.server.objects.Player;
import pl.mmorpg.prototype.server.states.PlayState;

public class ServerListener extends Listener
{
	private Server server;
	private PlayState playState;

	public ServerListener(Server server, PlayState playState)
	{
		this.server = server;
		this.playState = playState;
	}

	@Override
	public void connected(Connection connection)
	{
		sendCurrentGameObjectsInfo(connection.getID());
		Player newPlayer = new Player(connection.getID());
		playState.add(newPlayer);
		server.sendToAllExceptTCP(connection.getID(), PacketsMaker.makeCreationPacket(newPlayer));
		repositionNewlyAddedPlayer(newPlayer.getId());
		Log.info("User connected, id: " + connection.getID());
		super.connected(connection);
	}

	private void repositionNewlyAddedPlayer(long id)
	{
		server.sendToAllTCP(PacketsMaker.makeRepositionPacket(id, 100.0f, 100.0f));
	}

	private void sendCurrentGameObjectsInfo(int id)
	{
		Map<Long, GameObject> gameObjects = playState.getGameObjects();
		for (GameObject object : gameObjects.values())
			server.sendToTCP(id, PacketsMaker.makeCreationPacket(object));
	}

	@Override
	public void disconnected(Connection connection)
	{
		playState.remove(connection.getID());
		server.sendToAllTCP(PacketsMaker.makeRemovalPacket(connection.getID()));
		Log.info("User disconnected, id: " + connection.getID());
		super.disconnected(connection);
	}

	@Override
	public void received(Connection connection, Object object)
	{
		if (object instanceof MoveRightPacket)
		{
			MoveRightPacket packet = (MoveRightPacket) object;
			MovableGameObject operationTarget = (MovableGameObject) playState.getObject(packet.id);
			operationTarget.moveRight(playState.getCollisionMap());
			server.sendToAllTCP(PacketsMaker.makeRepositionPacket(connection.getID(), operationTarget.getX(),
					operationTarget.getY()));
		} else if (object instanceof MoveLeftPacket)
		{
			MoveLeftPacket packet = (MoveLeftPacket) object;
			MovableGameObject operationTarget = (MovableGameObject) playState.getObject(packet.id);
			operationTarget.moveLeft(playState.getCollisionMap());
			server.sendToAllTCP(PacketsMaker.makeRepositionPacket(connection.getID(), operationTarget.getX(),
					operationTarget.getY()));
		} else if (object instanceof MoveUpPacket)
		{
			MoveUpPacket packet = (MoveUpPacket) object;
			MovableGameObject operationTarget = (MovableGameObject) playState.getObject(packet.id);
			operationTarget.moveUp(playState.getCollisionMap());
			server.sendToAllTCP(PacketsMaker.makeRepositionPacket(connection.getID(), operationTarget.getX(),
					operationTarget.getY()));
		} else if (object instanceof MoveDownPacket)
		{
			MoveDownPacket packet = (MoveDownPacket) object;
			MovableGameObject operationTarget = (MovableGameObject) playState.getObject(packet.id);
			operationTarget.moveDown(playState.getCollisionMap());
			server.sendToAllTCP(PacketsMaker.makeRepositionPacket(connection.getID(), operationTarget.getX(),
					operationTarget.getY()));
		}

		Log.info("Packet received, client id: " + connection.getID() + ", packet: " + object);
		super.received(connection, object);
	}

}
