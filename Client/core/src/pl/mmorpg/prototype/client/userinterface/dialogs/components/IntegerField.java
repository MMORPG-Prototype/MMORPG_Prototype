package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class IntegerField extends TextField
{
	
	public IntegerField(Integer value, Skin skin)
	{
		this(value, skin, 4);
	}
	
	public IntegerField(Integer value, Skin skin, Integer maxDigits)
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
				return currentText.length() > maxDigits ? getMaxValue() : currentText.substring(0, currentText.length() - 1);
			}

			private String getMaxValue()
			{
				StringBuilder maxValue = new StringBuilder();
				for(int i=0; i<maxDigits; i++)
					maxValue.append('9');
				
				return maxValue.toString();
			}

			private boolean shouldCutLastLetter(char character)
			{
				return getText().length() > maxDigits || 
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
