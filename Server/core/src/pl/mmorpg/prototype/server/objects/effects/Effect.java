package pl.mmorpg.prototype.server.objects.effects;

public interface Effect
{
	void activate();
	
	void deactivate();
	
	void update(float deltaTime);
	
	boolean shouldDeactivate();
	
	void stackWithSameTypeEffect(Effect effect);
}
