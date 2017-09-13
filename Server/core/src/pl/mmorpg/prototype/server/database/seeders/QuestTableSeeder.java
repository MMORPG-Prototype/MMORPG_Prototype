package pl.mmorpg.prototype.server.database.seeders;

import java.util.Arrays;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.repositories.QuestRepository;
import pl.mmorpg.prototype.server.quests.KillMonsterTask;

public class QuestTableSeeder implements TableSeeder
{
	private final QuestRepository questRepository = SpringContext.getBean(QuestRepository.class);

	@Override
	public void seed()
	{
		questRepository.save(createQuest("Green dragon epidemy",
				"There is to many green dragons in the area, they are eating all crops, please help us, kill 10 of them"));
		questRepository.save(createQuest("Some quest",
				"Weclome, i hope the application won't crash for you in a second :)"));
	}

	private Quest createQuest(String name, String description)
	{
		Quest quest = new Quest();
		quest.setName(name);
		quest.setDescription(description);
		quest.setQuestTask(new KillMonsterTask(ObjectsIdentifiers.GREEN_DRAGON, 3));
		quest.setGoldReward(100);
		quest.setItemsReward(Arrays.asList(ItemIdentifiers.SMALL_HP_POTION, ItemIdentifiers.SMALL_MP_POTION));
		return quest;
	}

}
