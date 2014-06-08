package com.ii.subtitle.editor.commands;

import javax.swing.ListSelectionModel;

import com.ii.subtitle.editor.SubtitleItem;
import com.ii.subtitle.editor.Subtitles;
import com.ii.subtitle.editor.TextLeaf;

public class AddNewCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private ListSelectionModel selModel;
	private int firstSelIndex;
	private int secondSelIndex;
	
	public AddNewCommand(Subtitles subtitles, int subtitleIndex, ListSelectionModel selModel)
	{
		super(subtitles);
		this.subtitleIndex = subtitleIndex;
		this.selModel = selModel;
	}
	
	@Override
	protected void executeAfterFireTableDataChanged()
	{
		selModel.setSelectionInterval(subtitleIndex, subtitleIndex);
	}
	
	@Override
	protected void undoAfterFireTableDataChanged()
	{
		selModel.setSelectionInterval(firstSelIndex, secondSelIndex);
	}
	
	@Override
	protected boolean internalExecute()
	{
		subtitles.add(subtitleIndex,  new SubtitleItem(0, 0,  new TextLeaf("")));
		
		firstSelIndex = selModel.getAnchorSelectionIndex();
		secondSelIndex = selModel.getLeadSelectionIndex();
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		subtitles.remove(subtitleIndex);
		return true;
	}

}
