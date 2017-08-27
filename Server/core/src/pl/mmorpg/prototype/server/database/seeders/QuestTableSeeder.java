package pl.mmorpg.prototype.server.database.seeders;

import pl.mmorpg.prototype.SpringApplicationContext;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.repositories.QuestRepository;

public class QuestTableSeeder implements TableSeeder
{
	private final QuestRepository questRepository = SpringApplicationContext.getBean(QuestRepository.class);

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
		return quest;
	}

}
