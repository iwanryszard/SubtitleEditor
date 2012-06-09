package com.ii.subtitle.editor;


public class DeleteCommand extends AbstractSubtitlesCommand
{
	private int startSubtitle;
	private int selectedCount;
	
	public DeleteCommand(Interface interf, SubsTableModel model, int startSubtitle, int selectedCount)
	{
		super(interf, model);
		
		this.startSubtitle = startSubtitle;
		this.selectedCount = selectedCount;
	}

	@Override
	protected boolean internalExecute()
	{
		model.removeSubtitles(startSubtitle, selectedCount);
		interf.manipulateEditPanelValues("", "", "", "", false, false, false);
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		return true;
	}
}
