package pl.mmorpg.prototype.server.quests.dialog;

public class DialogTree
{
	private final DialogStep dialogEntryPoint;

	public DialogTree(DialogStep dialogEntryPoint)
	{
		this.dialogEntryPoint = dialogEntryPoint;
	}
	
	public DialogStep getDialogEntryPoint()
	{
		return dialogEntryPoint;
	}
	
}
