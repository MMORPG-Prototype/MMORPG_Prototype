package pl.mmorpg.prototype.server.objects.ineractivestaticobjects;

import java.util.Collection;
import java.util.LinkedList;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.repositories.QuestRepository;
import pl.mmorpg.prototype.server.resources.Assets;

public class QuestBoard extends InteractiveStaticObject
{
	public QuestBoard(long id)
	{
		super(Assets.get("Static enviroment/questBoard.png"), id);
		setSize(32, 50);
	}
	
	public Collection<Quest> getQuests()
	{
		QuestRepository questRepo = SpringContext.getBean(QuestRepository.class);
		Collection<Quest> quests = new LinkedList<>();
		questRepo.findAll().forEach(quests::add);
		return quests;
	}
}
