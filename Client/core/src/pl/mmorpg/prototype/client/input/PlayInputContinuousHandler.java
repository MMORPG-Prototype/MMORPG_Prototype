package pl.mmorpg.prototype.client.input;

import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveDownPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveLeftPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveRightPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveUpPacket;

public class PlayInputContinuousHandler extends InputProcessorAdapter
{
    private Client client;
    private UserCharacterDataPacket character;

    public PlayInputContinuousHandler(Client client, UserCharacterDataPacket character)
    {
        this.client = client;
        this.character = character;
    }

    public class WKeyHandler implements KeyHandler
    {
        @Override
        public void handle()
        {
            MoveUpPacket packet = new MoveUpPacket();
            packet.id = character.getId();
            client.sendTCP(packet);
        }
    }

    public class AKeyHandler implements KeyHandler
    {
        @Override
        public void handle()
        {
            MoveLeftPacket packet = new MoveLeftPacket();
            packet.id = character.getId();
            client.sendTCP(packet);
        }
    }

    public class SKeyHandler implements KeyHandler
    {
        @Override
        public void handle()
        {
            MoveDownPacket packet = new MoveDownPacket();
            packet.id = character.getId();
            client.sendTCP(packet);
        }
    }

    public class DKeyHandler implements KeyHandler
    {
        @Override
        public void handle()
        {
            MoveRightPacket packet = new MoveRightPacket();
            packet.id = character.getId();
            client.sendTCP(packet);
        }
    }
}
