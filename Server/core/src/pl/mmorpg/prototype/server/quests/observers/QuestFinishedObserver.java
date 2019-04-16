package pl.mmorpg.prototype.server.quests.observers;

import pl.mmorpg.prototype.data.entities.Quest;

public interface QuestFinishedObserver
{
    void playerFinishedQuest(int characterId, Quest quest);
}
