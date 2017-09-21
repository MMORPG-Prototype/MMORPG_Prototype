package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.util.Collection;
import java.util.LinkedList;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.AutoCleanupOnCloseButtonDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.ButtonCreator;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.MouseHoverVisibleDescriptionDialog;
import pl.mmorpg.prototype.clientservercommon.packets.entities.QuestDataPacket;

public class QuestBoardDialog extends AutoCleanupOnCloseButtonDialog
{
	private final Collection<MouseHoverVisibleDescriptionDialog> popUpDialogs = new LinkedList<>();

	public QuestBoardDialog(ActorManipulator linkedContainer, long id, QuestDataPacket[] quests,
			Stage containerForPopUpDialog, PacketsSender packetsSender)
	{
		super("Quests", linkedContainer, id);

		for(QuestDataPacket quest : quests)
		{
		    Label questNameLabel = new Label(quest.getName(), getSkin());
            MouseHoverVisibleDescriptionDialog mouseHoverVisibleDescriptionDialog = new MouseHoverVisibleDescriptionDialog(
                    questNameLabel, quest.getName(), quest.getDescription());
            containerForPopUpDialog.addActor(mouseHoverVisibleDescriptionDialog);
            popUpDialogs.add(mouseHoverVisibleDescriptionDialog);
            getContentTable().add(questNameLabel);
            Button acceptQuestButton = ButtonCreator.createTextButton("Take", () -> 
                    packetsSender.send(PacketsMaker.makeAcceptQuestPacket(quest.getName())));
            getContentTable().add(acceptQuestButton);
            getContentTable().row();
		}

		this.pack();
	}

	@Override
	public void onClose()
	{
		popUpDialogs.forEach(d -> d.remove());
	}

}
