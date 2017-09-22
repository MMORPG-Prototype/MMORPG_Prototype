package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.AutoCleanupOnCloseButtonDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.quest.QuestPosition;
import pl.mmorpg.prototype.clientservercommon.packets.entities.QuestDataPacket;

public class QuestBoardDialog extends AutoCleanupOnCloseButtonDialog
{
	private final Map<String, QuestPosition> questPositions = new HashMap<>();

	public QuestBoardDialog(ActorManipulator linkedContainer, long id, QuestDataPacket[] quests,
			Stage containerForPopUpDialog, PacketsSender packetsSender)
	{
		super("Quests", linkedContainer, id);

		for(QuestDataPacket quest : quests)
		{
		    QuestPosition questPosition = new QuestPosition(quest.getName(), quest.getDescription(), containerForPopUpDialog, packetsSender);
		    questPositions.put(quest.getName(), questPosition);
		    getContentTable().add(questPosition);
		    getContentTable().row();
		}
		if(questPositions.isEmpty())
            addNoQuestsAvailableLabel();

		this.pack();
	}
	
    private void addNoQuestsAvailableLabel()
    {
        getContentTable().add(new Label("No quests available", getSkin()));
        getContentTable().row();
    }

	@Override
	public void onClose()
	{
	    questPositions.values().forEach(q -> q.remove());
	}

    public void removeQuestPosition(String questName)
    {
        questPositions.remove(questName).remove();
        if(questPositions.isEmpty())
            addNoQuestsAvailableLabel();
        this.pack();
    }

}
