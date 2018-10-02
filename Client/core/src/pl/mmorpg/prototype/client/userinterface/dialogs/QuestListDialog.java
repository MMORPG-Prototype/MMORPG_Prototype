package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.packethandlers.UserInterfacePacketHandlerBase;
import pl.mmorpg.prototype.client.quests.Quest;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.quest.QuestListPane;
import pl.mmorpg.prototype.clientservercommon.packets.QuestFinishedRewardPacket;

public class QuestListDialog extends Dialog
{
    private QuestListPane questListPane;

    public QuestListDialog(PacketHandlerRegisterer registerer)
    {
        super("Quests", Settings.DEFAULT_SKIN);
        questListPane = new QuestListPane();
        this.add(questListPane);
        
        registerer.registerPrivateClassPacketHandlers(this);
    }
    
    public void addQuest(Quest quest)
    {
        questListPane.addQuest(quest);
        this.pack();
    }
    
    @SuppressWarnings("unused")
    private class QuestFinishedRewardPacketHandler extends UserInterfacePacketHandlerBase<QuestFinishedRewardPacket>
    {
		@Override
		protected void doHandle(QuestFinishedRewardPacket packet)
		{
			// TODO
		}
    }

}
