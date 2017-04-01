package pl.mmorpg.prototype.client.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.dialogs.GDXProgressDialog;
import de.tomgrill.gdxdialogs.core.dialogs.GDXTextPrompt;
import de.tomgrill.gdxdialogs.core.listener.TextPromptListener;

public class SettingsChoosingState implements State
{
	private Client client;
	private GDXDialogs dialogs = GDXDialogsSystem.install();
	private Stage stage = new Stage();

	public SettingsChoosingState(Client client)
	{
		this.client = client;
		Gdx.input.setInputProcessor(stage);
		
		//showTextPrompt();
	}


	private void showDialog()
	{
		GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
		bDialog.setTitle("Buy a item");
		bDialog.setMessage("Do you want to buy the mozarella?");

		bDialog.setClickListener((button) ->
		{
		});

		bDialog.addButton("No");
		bDialog.addButton("Never");
		bDialog.addButton("Yes, nomnom!");

		bDialog.build().show();

	}

	private void showProgressBar()
	{
		GDXProgressDialog progressDialog = dialogs.newDialog(GDXProgressDialog.class);

		progressDialog.setTitle("Download");
		progressDialog.setMessage("Loading new level from server...");

		progressDialog.build().show();
		
	}

	private void showTextPrompt()
	{
		GDXTextPrompt textPrompt = dialogs.newDialog(GDXTextPrompt.class);

		textPrompt.setTitle("Your name");
		textPrompt.setMessage("Please tell me your name.");

		textPrompt.setCancelButtonLabel("Cancel");
		textPrompt.setConfirmButtonLabel("Save name");

		textPrompt.setTextPromptListener(new TextPromptListener()
		{

			@Override
			public void confirm(String text)
			{
				// do something with the user input
			}

			@Override
			public void cancel()
			{
				// handle input cancel
			}
		});

		textPrompt.build().show();
	}


	@Override
	public void render(SpriteBatch batch)
	{
	}

}
