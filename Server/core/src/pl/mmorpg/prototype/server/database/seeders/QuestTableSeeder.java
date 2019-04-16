package pl.mmorpg.prototype.server.database.seeders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.thedeanda.lorem.LoremIpsum;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.clientservercommon.NpcNames;
import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.data.entities.Quest;
import pl.mmorpg.prototype.data.entities.QuestItemReward;
import pl.mmorpg.prototype.data.entities.repositories.QuestRepository;
import pl.mmorpg.prototype.server.quests.AcceptQuestTask;
import pl.mmorpg.prototype.server.quests.KillMonstersTask;
import pl.mmorpg.prototype.server.quests.dialog.DialogStep;
import pl.mmorpg.prototype.server.quests.dialog.NpcDialogTask;

public class QuestTableSeeder implements TableSeeder
{
	private final QuestRepository questRepository = SpringContext.getBean(QuestRepository.class);

	@Override
	public void seed()
	{
		questRepository.save(createKillMonsterQuest("Green dragon epidemy",
				"There is to many green dragons in the area, they are eating all crops, please help us, kill 3 of them"));
		questRepository.save(createKillMonsterQuest("Green dragon epidemy 2",
				"Debug quest")); 
		questRepository.save(createTalkWithNpcQuest("Dialog", "Talk with npc quest"));
	}

	private Quest createKillMonsterQuest(String name, String description)
	{
		Quest quest = new Quest();
		quest.setName(name);
		quest.setDescription(description);
		AcceptQuestTask rootTask = new AcceptQuestTask(null, name);
		KillMonstersTask killMonsterTask = new KillMonstersTask(0, ObjectsIdentifiers.GREEN_DRAGON, 3);
		rootTask.addNextTask(killMonsterTask);
		quest.setQuestTask(rootTask);
		quest.setGoldReward(100);
		quest.setItemsReward(Arrays.asList(
                new QuestItemReward(ItemIdentifiers.SMALL_HP_POTION, 2, quest), 
                new QuestItemReward(ItemIdentifiers.SMALL_MP_POTION, 1, quest)
                ));
		return quest;
	}
	
	private Quest createTalkWithNpcQuest(String name, String description)
	{
		Quest quest = new Quest();
		quest.setName(name);
		quest.setDescription(description);
		AcceptQuestTask rootTask = new AcceptQuestTask(null, name);
		NpcDialogTask talkWithNpcTask = new NpcDialogTask(0, NpcNames.QUEST_DIALOG_NPC, createDialog());
		rootTask.addNextTask(talkWithNpcTask);
		quest.setQuestTask(rootTask);
		quest.setGoldReward(100);
		quest.setItemsReward(Arrays.asList(
                new QuestItemReward(ItemIdentifiers.SMALL_HP_POTION, 2, quest), 
                new QuestItemReward(ItemIdentifiers.SMALL_MP_POTION, 1, quest)
                ));
		return quest;
	}

	private DialogStep createDialog()
	{
		Map<String, DialogStep> furtherDialogs = new HashMap<>();
		furtherDialogs.put("Answer option 1", new DialogStep("I agree with you, here is the reward!"));
		furtherDialogs.put("Answer option 2", new DialogStep("I disagree, but here is the reward anyway..."));
		String speech = new LoremIpsum().getWords(50);
		DialogStep dialogEntryPoint = new DialogStep(speech, furtherDialogs);
		return dialogEntryPoint;
	}

}
