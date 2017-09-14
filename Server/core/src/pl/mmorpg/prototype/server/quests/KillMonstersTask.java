package pl.mmorpg.prototype.server.quests;

import pl.mmorpg.prototype.server.quests.events.MonsterKilledEvent;

public class KillMonstersTask extends QuestTaskBase<MonsterKilledEvent>
{
    private final String monsterIdentifier;
    private int monstersToKill;

    private KillMonstersTask()
    {
        super(MonsterKilledEvent.class);
        monsterIdentifier = null;
    }
 
    public KillMonstersTask(String monsterIdentifier, int monstersToKill)
    {
        super(MonsterKilledEvent.class);
        this.monsterIdentifier = monsterIdentifier;
        this.monstersToKill = monstersToKill;
    }

    @Override
    public boolean isApplicable(MonsterKilledEvent event)
    {
        return monsterIdentifier.equalsIgnoreCase(event.getMonsterIdentifier());
    }

    @Override
    public void apply(MonsterKilledEvent event)
    {
        monstersToKill--;
        if(monstersToKill < 0)
            monstersToKill = 0;
    }
    
    @Override
    public boolean isFinished()
    {
        return monstersToKill <= 0;
    }

    public String getMonsterIdentifier()
    {
        return monsterIdentifier;
    }

    public Integer getMonstersToKill()
    {
        return monstersToKill;
    }
}
