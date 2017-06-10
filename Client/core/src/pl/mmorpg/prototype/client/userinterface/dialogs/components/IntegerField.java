package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class IntegerField extends TextField
{
	
	public IntegerField(Integer value, Skin skin)
	{
		super(value.toString(), skin);
		addListener(new TextFieldClickListener()
		{
			@Override
			public boolean keyTyped(InputEvent event, char character)
			{
				if(shouldCutLastLetter(character))
				{
					String newText = getNewText();					
					setText(newText);
					IntegerField.this.setCursorPosition(newText.length());
				}
				return false;
			}

			private String getNewText()
			{
				String currentText = getText();
				return currentText.length() >= 5 ? "9999" : currentText.substring(0, currentText.length() - 1);
			}

			private boolean shouldCutLastLetter(char character)
			{
				return getText().length() >= 5 || 
						(!Character.isDigit(character) && isWordCharacter(character));
			}
		});
	}
	
	public Integer getValue()
	{
		if(getText().isEmpty())
			return 0;
		return Integer.parseInt(getText());
	}

}
