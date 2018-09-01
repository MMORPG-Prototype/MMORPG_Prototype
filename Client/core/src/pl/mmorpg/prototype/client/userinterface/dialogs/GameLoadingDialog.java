package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameLoadingDialog extends Dialog
{
	private ProgressBar progressBar;

	public GameLoadingDialog(Skin skin)
	{
		super("Loading...", skin);
		progressBar = new ProgressBar(0, 1, 0.01f, false, skin);
		progressBar.setRound(true);
		getContentTable().add(progressBar);
		this.pack();
	}

	public void updateProgress(float progress)
	{
		if (progress >= 0.90f)
			progressBar.setValue(1);
		else
			progressBar.setValue(progress);
	}
}
