package pl.mmorpg.prototype.server.database.seeders;

import java.util.Arrays;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.entities.QuestItemReward;
import pl.mmorpg.prototype.server.database.repositories.QuestRepository;
import pl.mmorpg.prototype.server.quests.AcceptQuestTask;
import pl.mmorpg.prototype.server.quests.KillMonstersTask;

public class QuestTableSeeder implements TableSeeder
{
	private final QuestRepository questRepository = SpringContext.getBean(QuestRepository.class);

	@Override
	public void seed()
	{
		questRepository.save(createQuest("Green dragon epidemy",
				"There is to many green dragons in the area, they are eating all crops, please help us, kill 3 of them"));
		questRepository.save(createQuest("Green dragon epidemy 2",
				"Debug quest"));
	}

	private Quest createQuest(String name, String description)
	{
		Quest quest = new Quest();
		quest.setName(name);
		quest.setDescription(description);
		AcceptQuestTask rootTask = new AcceptQuestTask(name);
		KillMonstersTask killMonsterTask = new KillMonstersTask(ObjectsIdentifiers.GREEN_DRAGON, 3);
		rootTask.addNextTask(killMonsterTask);
		quest.setQuestTask(rootTask);
		quest.setGoldReward(100);
		quest.setItemsReward(Arrays.asList(
                new QuestItemReward(ItemIdentifiers.SMALL_HP_POTION, 2, quest), 
                new QuestItemReward(ItemIdentifiers.SMALL_MP_POTION, 1, quest)
                ));
		return quest;
	}

}
