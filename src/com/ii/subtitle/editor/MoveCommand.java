package com.ii.subtitle.editor;

import java.util.Collections;

import javax.swing.ListSelectionModel;

public class MoveCommand extends AbstractSubtitlesCommand
{
	private int startSubtitle;
	private int selectedCount;
	private ListSelectionModel selModel;
	private int firstSelIndex;
	private int secondSelIndex;
	private int direction; //  +/-1
	
	public MoveCommand(Interface interf, Subtitles subtitles, int startSubtitle, int selectedCount, int direction,
			ListSelectionModel selModel)
	{
		super(interf, subtitles);
		
		this.direction = direction != 1 ? -1 : direction;
		this.startSubtitle = startSubtitle;
		this.selectedCount = selectedCount;
		this.selModel = selModel;
	}
	
	@Override
	protected void executeAfterFireTableDataChanged()
	{
		selModel.setSelectionInterval(startSubtitle + direction, startSubtitle + selectedCount - 1 + direction);
	}
	
	@Override
	protected void undoAfterFireTableDataChanged()
	{
		selModel.setSelectionInterval(firstSelIndex, secondSelIndex);
	}
	
	@Override
	protected boolean internalExecute()
	{
		moveSubtitles(startSubtitle, selectedCount, direction);
		
		firstSelIndex = selModel.getAnchorSelectionIndex();
		secondSelIndex = selModel.getLeadSelectionIndex();
		
		return true;
	}

	private void moveSubtitles(int startSubtitle, int count, int direction) {
		int endIndex = startSubtitle + count - 1;
		int start = (direction < 0 ? startSubtitle : endIndex) + direction;
		int end = direction < 0 ? endIndex : startSubtitle;
		int i = start;
		while (i != end){
			Collections.swap(subtitles, i, i - direction);
			i -= direction;
		}
	}

	@Override
	protected boolean internalUndo()
	{
		moveSubtitles(startSubtitle + direction, selectedCount, -direction);
		return true;
	}
}
