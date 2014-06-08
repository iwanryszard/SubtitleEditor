package com.ii.subtitle.editor;

import javax.swing.ListSelectionModel;

public class AddNewCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private ListSelectionModel selModel;
	private int firstSelIndex;
	private int secondSelIndex;
	
	public AddNewCommand(Interface interf, Subtitles subtitles, int subtitleIndex, ListSelectionModel selModel)
	{
		super(interf, subtitles);
		
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

		interf.manipulateEditPanelValues(subtitles.getStart(subtitleIndex), subtitles.getEnd(subtitleIndex), subtitles.getDuration(subtitleIndex), subtitles.getSubtitleHTMLFormattedText(subtitleIndex, false));
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		subtitles.remove(subtitleIndex);
		return true;
	}

}
