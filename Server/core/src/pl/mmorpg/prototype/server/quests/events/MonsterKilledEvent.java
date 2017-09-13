package pl.mmorpg.prototype.server.quests.events;

import lombok.Data;

@Data
public class MonsterKilledEvent implements Event
{
    private String monsterIdentifier;
}
