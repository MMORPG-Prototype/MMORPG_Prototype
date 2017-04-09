package pl.mmorpg.prototype.server.objects;

import pl.mmorpg.prototype.clientservercommon.CharacterStatsCalculator;
import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.MonsterProperties;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public class PlayerCharacter extends Monster
{
	private UserCharacter userCharacter;

	public PlayerCharacter(UserCharacter userCharacter, PlayState linkedState)
	{
		super(Assets.get("MainChar.png"), userCharacter.getId(), linkedState,
				new PlayerPropertiesBuilder(userCharacter).build());
		this.userCharacter = userCharacter;
		setPacketSendingInterval(0.0f);
	}

	@Override
	public String getIdentifier()
	{
		return ObjectsIdentifiers.PLAYER;
	}

	private static class PlayerPropertiesBuilder extends MonsterProperties.Builder
	{
		private UserCharacter userCharacter;

		PlayerPropertiesBuilder(UserCharacter userCharacter)
		{
			this.userCharacter = userCharacter;
		}
		
		@Override
		public MonsterProperties build()
		{
			UserCharacterDataPacket dataPacket = PacketsMaker.makeCharacterPacket(userCharacter);
			 		attackPower(10)
					.attackRange(30)
					.attackSpeed(1.0f)
					.defense(5)
					.maxHp(CharacterStatsCalculator.getMaxHP(dataPacket))
					.maxMp(CharacterStatsCalculator.getMaxMP(dataPacket));
			 return super.build();
		}
	}
}
