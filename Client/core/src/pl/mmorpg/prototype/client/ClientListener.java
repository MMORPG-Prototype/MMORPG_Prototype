package pl.mmorpg.prototype.client;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.ObjectsFactory;
import pl.mmorpg.prototype.client.states.AuthenticationState;
import pl.mmorpg.prototype.client.states.ChoosingCharacterState;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.RegisterationState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.packets.AuthenticatonReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.GameObjectTargetPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.RegisterationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;

public class ClientListener extends Listener
{
	private Client client;
	private PlayState playState;
	private StateManager states;
	
	private Collection<PacketInfo> unhandledPackets = new LinkedList<>();

	public ClientListener(Client client, PlayState playState, StateManager states)
	{
		this.client = client;
		this.playState = playState;
		this.states = states;
	}

	@Override
	public void connected(Connection connection)
	{
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
		if (canBeHandled(object))
			handlePacket(object);
		else
			unhandledPackets.add(new PacketInfo(connection.getID(), object));
		Log.info("Packet received from server, id: " + connection.getID() + "packet: " + object);
	}

	private boolean canBeHandled(Object packet)
	{
		if (packet instanceof GameObjectTargetPacket && !objectExist((GameObjectTargetPacket) packet))
			return false;
		return true;
	}

	private boolean objectExist(GameObjectTargetPacket packet)
	{
		return playState.getObject(packet.id) != null;
	}

	private void handlePacket(Object object)
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
		else if(object instanceof AuthenticatonReplyPacket)
		{
			AuthenticatonReplyPacket replyPacket = (AuthenticatonReplyPacket) object;
			AuthenticationState authenticationState = (AuthenticationState) states.usedState();
			authenticationState.authenticationReplyReceived(replyPacket);
		}
		else if (object instanceof RegisterationReplyPacket)
		{
			RegisterationReplyPacket replyPacket = (RegisterationReplyPacket) object;
			RegisterationState registerationState = (RegisterationState) states.usedState();
			registerationState.registerationReplyReceived(replyPacket);
		}
		else if (object instanceof UserCharacterDataPacket[])
		{
			UserCharacterDataPacket[] characterPackets = (UserCharacterDataPacket[]) object;
			ChoosingCharacterState registerationState = (ChoosingCharacterState) states.usedState();
			registerationState.userCharactersDataReceived(characterPackets);
		}
		else if (object instanceof CharacterItemDataPacket)
		{
			playState.newItemPacketReceived((CharacterItemDataPacket) object);
		}
	}

	public void tryHandlingUnhandledPackets()
	{
		Iterator<PacketInfo> it = unhandledPackets.iterator();
		while(it.hasNext())
		{
			PacketInfo packetInfo = it.next();
			if (canBeHandled(packetInfo.packet))
			{
				handlePacket(packetInfo.packet);
				it.remove();
			}
		}
	}

}
