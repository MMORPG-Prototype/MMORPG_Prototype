package pl.mmorpg.prototype.server.quests.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonsterKilledEvent extends EventBase
{
    private final String monsterIdentifier;
}
