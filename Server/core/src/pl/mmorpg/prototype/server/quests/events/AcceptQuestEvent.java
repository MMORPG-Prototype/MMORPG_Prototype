package pl.mmorpg.prototype.server.quests.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AcceptQuestEvent extends EventBase
{
    private final String questName;
}
