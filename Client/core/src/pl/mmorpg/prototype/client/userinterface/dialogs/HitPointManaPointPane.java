package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.clientservercommon.packets.HpChangeByItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.HpUpdatePacket;
import pl.mmorpg.prototype.clientservercommon.packets.MpChangeByItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.MpUpdatePacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class HitPointManaPointPane extends ScrollPane
{
	private static HitPointManaPointDialog dialogInitializer;
	private final HitPointManaPointDialog dialog;
	
	public HitPointManaPointPane(UserCharacterDataPacket character, PacketHandlerRegisterer packetHandlerRegisterer)
	{
		super(createDialog(character));
		dialog = dialogInitializer;
		initiazlizePosition();
		packetHandlerRegisterer.registerPrivateClassPacketHandlers(this);
	} 
	
	private static HitPointManaPointDialog createDialog(UserCharacterDataPacket character)
	{
		dialogInitializer = new HitPointManaPointDialog(character);
		return dialogInitializer;
	}

	private void initiazlizePosition()
	{
		this.setHeight(100);
		this.setWidth(200);
		this.setX(0);
		this.setY(Gdx.graphics.getHeight() - this.getHeight());
	}
	
	public void updateValues()
	{
		dialog.update();
	}
	
	@SuppressWarnings("unused")
	private class HpChangeByItemUsagePacketHandler extends PacketHandlerBase<HpChangeByItemUsagePacket>
	{
		@Override
		protected void doHandle(HpChangeByItemUsagePacket packet)
		{
			dialog.update();
		}
	}

	@SuppressWarnings("unused")
	private class HpUpdatePacketHandler extends PacketHandlerBase<HpUpdatePacket>
	{
		@Override
		protected void doHandle(HpUpdatePacket packet)
		{
			dialog.update();
		}
	}

	@SuppressWarnings("unused")
	private class MpChangeByItemUsagePacketHandler extends PacketHandlerBase<MpChangeByItemUsagePacket>
	{
		@Override
		protected void doHandle(MpChangeByItemUsagePacket packet)
		{
			dialog.update();
		}
	}

	@SuppressWarnings("unused")
	private class MpUpdatePacketHandler extends PacketHandlerBase<MpUpdatePacket>
	{
		@Override
		protected void doHandle(MpUpdatePacket packet)
		{
			dialog.update();
		}
	}

}
