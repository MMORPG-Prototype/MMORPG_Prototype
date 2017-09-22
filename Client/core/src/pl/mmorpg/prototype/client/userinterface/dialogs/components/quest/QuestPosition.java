package pl.mmorpg.prototype.client.userinterface.dialogs.components.quest;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.ButtonCreator;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.MouseHoverVisibleDescriptionDialog;

public class QuestPosition extends Table
{
    private MouseHoverVisibleDescriptionDialog mouseHoverVisibleDescriptionDialog;

    public QuestPosition(String questName, String questDescription, Stage containerForPopUpDialog, PacketsSender packetsSender)
    {
        setSkin(Settings.DEFAULT_SKIN);
        Label questNameLabel = new Label(questName, getSkin());
        mouseHoverVisibleDescriptionDialog = new MouseHoverVisibleDescriptionDialog(
                questNameLabel, questName, questDescription);
        containerForPopUpDialog.addActor(mouseHoverVisibleDescriptionDialog);
        add(questNameLabel);
        Button acceptQuestButton = ButtonCreator.createTextButton("Take", () -> 
                packetsSender.send(PacketsMaker.makeAcceptQuestPacket(questName)));
        add(acceptQuestButton);
    }
    
    @Override
    public boolean remove()
    {
        mouseHoverVisibleDescriptionDialog.remove();
        return super.remove();
    }
}
