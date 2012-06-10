package com.ii.subtitle.editor;

import javax.swing.ListSelectionModel;

public class TranslateCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private ListSelectionModel selModel;
	private int firstSelIndex;
	private int secondSelIndex;
	private String translate;
	
	public TranslateCommand(Interface interf, Subtitles subtitles, int subtitleIndex, ListSelectionModel selModel, String translate)
	{
		super(interf, subtitles);
		
		this.subtitleIndex = subtitleIndex;
		this.selModel = selModel;
		this.translate = translate;
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
		subtitles.translate(this.translate, firstSelIndex, secondSelIndex - firstSelIndex + 1);
		interf.manipulateTranslateValues(!subtitles.isInFrames() ? "00:00:00,000" : "0");
		interf.manipulateEditPanelValues(subtitles.getStart(subtitleIndex), subtitles.getEnd(subtitleIndex), subtitles.getDuration(subtitleIndex), subtitles.getSubtitleHTMLFormattedText(subtitleIndex, false));
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		return true;
	}

}
