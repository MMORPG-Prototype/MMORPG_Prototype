package pl.mmorpg.prototype.server.quests;

import com.fasterxml.jackson.annotation.JsonProperty;

import pl.mmorpg.prototype.server.quests.events.MonsterKilledEvent;

public class KillMonstersTask extends QuestTaskBase<MonsterKilledEvent>
{
    private final String monsterIdentifier;
    @JsonProperty
    private final int totalMonstersToKill;
    private int monstersLeftToKill;

    private KillMonstersTask()
    {
        super(MonsterKilledEvent.class);
        monsterIdentifier = null;
        totalMonstersToKill = 0;
    }

    public KillMonstersTask(String monsterIdentifier, int monstersToKill)
    {
        super(MonsterKilledEvent.class);
        this.monsterIdentifier = monsterIdentifier;
        this.totalMonstersToKill = monstersToKill;
        this.monstersLeftToKill = monstersToKill;
    }

    @Override
    public boolean isApplicable(MonsterKilledEvent event)
    {
        return monsterIdentifier.equalsIgnoreCase(event.getMonsterIdentifier());
    }

    @Override
    public void apply(MonsterKilledEvent event)
    {
        monstersLeftToKill--;
        if (monstersLeftToKill < 0)
            monstersLeftToKill = 0;
    }

    @Override
    public boolean isFinished()
    {
        return monstersLeftToKill <= 0;
    }

    public String getMonsterIdentifier()
    {
        return monsterIdentifier;
    }

    public Integer getMonstersLeftToKill()
    {
        return monstersLeftToKill;
    }

    @Override
    public String getDescription()
    {
        return "Kill " + monstersLeftToKill + ' ' + monsterIdentifier;
    }

    @Override
    public float getPercentFinished()
    {
        return (float) (totalMonstersToKill - monstersLeftToKill) / (float) totalMonstersToKill * 100.0f;
    }
}
