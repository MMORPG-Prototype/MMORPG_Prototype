package pl.mmorpg.prototype.server.objects.effects.timed;

import java.util.Collection;
import java.util.LinkedList;

import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.effects.Effect;
import pl.mmorpg.prototype.server.objects.effects.MultiEffect;
import pl.mmorpg.prototype.server.objects.effects.timed.HpRegenerationEffect;
import pl.mmorpg.prototype.server.objects.effects.timed.MpRegenerationEffect;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public class FoodNaturalRegenerationEffect extends MultiEffect
{

	public FoodNaturalRegenerationEffect(Monster eater, float activeTime, PacketsSender packetSender)
	{
		super(getEffects(eater, activeTime, packetSender));
	}
	
	private static Collection<Effect> getEffects(Monster target, float activeTime, PacketsSender packetSender)
	{
		Collection<Effect> effects = new LinkedList<>();
		effects.add(new HpRegenerationEffect(activeTime, 3.0f, 1, target, packetSender));
		effects.add(new MpRegenerationEffect(activeTime, 2.5f, 1, target, packetSender));
		return effects;
	}
	
}
