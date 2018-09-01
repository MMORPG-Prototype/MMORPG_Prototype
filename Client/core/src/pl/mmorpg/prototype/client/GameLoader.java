package pl.mmorpg.prototype.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.userinterface.dialogs.DialogUtils;
import pl.mmorpg.prototype.client.userinterface.dialogs.GameLoadingDialog;
import pl.mmorpg.prototype.clientservercommon.Settings;

public class GameLoader extends ApplicationAdapter
{
	private static final String BACKGROUND_PATH = "background.jpg";
	public static final String SKIN_PATH = "UISkins/clean-crispy/skin/clean-crispy-ui.json";
	private Texture backgroundTexture;
	private boolean loaded;
	private GameClient gameClient;
	private GameLoadingDialog gameLoadingDialog;
	private Stage stage;
	private Image background;
	private Skin skin;

	@Override
	public void create()
	{
		Assets.loadPreparationSteps();
		initializeResources();
		setupStage();
	}

	private void initializeResources()
	{
		backgroundTexture = new Texture(Gdx.files.classpath(BACKGROUND_PATH));
		background = new Image(new TextureRegion(backgroundTexture));
		skin = new Skin(Gdx.files.classpath(SKIN_PATH));
	}

	private void setupStage()
	{
		stage = new Stage(new ScalingViewport(Scaling.stretch, Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT));
		gameLoadingDialog = new GameLoadingDialog(skin);
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(background);
		stage.addActor(gameLoadingDialog);
		DialogUtils.centerPosition(gameLoadingDialog);
	}

	@Override
	public void render()
	{
		if(!loaded)
		{
			stage.draw();
			update();
		}
		else
			gameClient.render();

	}

	private void update()
	{
		stage.act();
		if (!Assets.preparationFinished())
			Assets.executeNextPreparationStep();
		else
		{
			float loadingProgress = Assets.getLoadingProgress();
			gameLoadingDialog.updateProgress(loadingProgress);
			if (loadingProgress < 1.0f)
				Assets.update();
			else
				launchGame();
		}
	}

	private void launchGame()
	{
		disposeTemporaryResources();
		gameClient = new GameClient();
		gameClient.create();
		loaded = true;
	}

	private void disposeTemporaryResources()
	{
		backgroundTexture.dispose();
		skin.dispose();
		stage.dispose();
	}
}
