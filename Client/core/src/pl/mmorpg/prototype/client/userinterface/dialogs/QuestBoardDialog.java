package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.util.Collection;
import java.util.LinkedList;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.AutoCleanupOnCloseButtonDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.MouseHoverVisibleDescriptionDialog;
import pl.mmorpg.prototype.clientservercommon.packets.entities.QuestDataPacket;

public class QuestBoardDialog extends AutoCleanupOnCloseButtonDialog
{
	private final Collection<MouseHoverVisibleDescriptionDialog> popUpDialogs = new LinkedList<>();

	public QuestBoardDialog(ActorManipulator linkedContainer, long id, QuestDataPacket[] quests,
			Stage containerForPopUpDialog)
	{
		super("Quests", linkedContainer, id);

		for (int i = 0; i < quests.length; i++)
		{
			Label questNameLabel = new Label(quests[i].getName(), getSkin());
			MouseHoverVisibleDescriptionDialog mouseHoverVisibleDescriptionDialog = new MouseHoverVisibleDescriptionDialog(
					questNameLabel, quests[i].getName(), quests[i].getDescription());
			containerForPopUpDialog.addActor(mouseHoverVisibleDescriptionDialog);
			popUpDialogs.add(mouseHoverVisibleDescriptionDialog);
			getContentTable().add(questNameLabel);
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
