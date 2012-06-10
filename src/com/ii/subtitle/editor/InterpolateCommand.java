package com.ii.subtitle.editor;

import javax.swing.ListSelectionModel;

public class InterpolateCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private ListSelectionModel selModel;
	private int firstSelIndex;
	private int secondSelIndex;
	private String start;
	private String end;
	
	public InterpolateCommand(Interface interf, Subtitles subtitles, int subtitleIndex, ListSelectionModel selModel, String start, String end)
	{
		super(interf, subtitles);
		
		this.subtitleIndex = subtitleIndex;
		this.selModel = selModel;
		this.start = start;
		this.end = end;
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
		
		firstSelIndex = selModel.getAnchorSelectionIndex();
		secondSelIndex = selModel.getLeadSelectionIndex();
		subtitles.interpolate(start, firstSelIndex, end, secondSelIndex);
		interf.manipulateInterpolateValues(subtitles.getStart(firstSelIndex), subtitles.getStart(secondSelIndex));
		interf.manipulateEditPanelValues(subtitles.getStart(subtitleIndex), subtitles.getEnd(subtitleIndex), subtitles.getDuration(subtitleIndex), subtitles.getSubtitleHTMLFormattedText(subtitleIndex, false));
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		return true;
	}

}
