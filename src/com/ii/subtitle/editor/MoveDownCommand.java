package com.ii.subtitle.editor;

import javax.swing.ListSelectionModel;

public class MoveDownCommand extends AbstractSubtitlesCommand
{
	private int startSubtitle;
	private int selectedCount;
	private ListSelectionModel selModel;
	private int firstSelIndex;
	private int secondSelIndex;
	
	public MoveDownCommand(Interface interf, Subtitles subtitles, int startSubtitle, int selectedCount, 
			ListSelectionModel selModel)
	{
		super(interf, subtitles);
		
		this.startSubtitle = startSubtitle;
		this.selectedCount = selectedCount;
		this.selModel = selModel;
	}
	
	@Override
	protected void executeAfterFireTableDataChanged()
	{
		selModel.setSelectionInterval(startSubtitle + 1, startSubtitle + selectedCount);
	}
	
	@Override
	protected void undoAfterFireTableDataChanged()
	{
		selModel.setSelectionInterval(firstSelIndex, secondSelIndex);
	}
	
	@Override
	protected boolean internalExecute()
	{
		subtitles.moveDownByOne(startSubtitle, selectedCount);
		
		firstSelIndex = selModel.getAnchorSelectionIndex();
		secondSelIndex = selModel.getLeadSelectionIndex();
		
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		return true;
	}
}
