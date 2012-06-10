package com.ii.subtitle.editor;


public class DeleteCommand extends AbstractSubtitlesCommand
{
	private int startSubtitle;
	private int selectedCount;
	
	public DeleteCommand(Interface interf, Subtitles subtitles, int startSubtitle, int selectedCount)
	{
		super(interf, subtitles);
		
		this.startSubtitle = startSubtitle;
		this.selectedCount = selectedCount;
	}

	@Override
	protected boolean internalExecute()
	{
		subtitles.removeInRange(startSubtitle, selectedCount);
		interf.manipulateEditPanelValues("", "", "", "");
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		return true;
	}
}
