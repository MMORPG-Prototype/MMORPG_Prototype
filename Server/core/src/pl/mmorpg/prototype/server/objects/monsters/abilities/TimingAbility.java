package pl.mmorpg.prototype.server.objects.monsters.abilities;

public interface TimingAbility extends Ability
{
	void updateWithDeltaTime(float deltaTime);
}
