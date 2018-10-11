package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.clientservercommon.StatisticsCalculator;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;

public class HitPointManaPointDialog extends Dialog
{
	private final ProgressBar hitPoints;
	private final ProgressBar manaPoints;
	private final Player player;

	public HitPointManaPointDialog(Player player)
	{
		super(player.getData().getNickname(), Settings.DEFAULT_SKIN);
		this.player = player;
		hitPoints = new ProgressBar(0, getMaxHp(), 1.0f, false, Settings.DEFAULT_SKIN);
		hitPoints.setColor(new Color(1.0f, 0.2f, 0.2f, 0.9f));
		manaPoints = new ProgressBar(0, getMaxMp(), 1.0f, false, Settings.DEFAULT_SKIN);
		manaPoints.setColor(new Color(0.2f, 0.2f, 1.0f, 0.9f));
		
		this.getContentTable().row();
		text("HP: ");
		this.getContentTable().add(hitPoints).top().right();
		this.getContentTable().row();
		text("MP: ");
		this.getContentTable().add(manaPoints).top().right();
		update();
	}

	private int getMaxHp()
	{
		return StatisticsCalculator.calculateMaxHp(player.getProperties());
	}

	private int getMaxMp()
	{
		return StatisticsCalculator.calculateMaxMp(player.getProperties());
	}

	public void update()
	{
		hitPoints.setValue(getMaxHp());
		manaPoints.setValue(getMaxMp());
	}
	

}
