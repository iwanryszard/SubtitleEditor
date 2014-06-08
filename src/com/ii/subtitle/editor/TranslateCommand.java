package com.ii.subtitle.editor;

import javax.swing.ListSelectionModel;

public class TranslateCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private ListSelectionModel selModel;
	private int firstSelIndex;
	private int secondSelIndex;
	private int translate;
	
	public TranslateCommand(Interface interf, Subtitles subtitles, int subtitleIndex, ListSelectionModel selModel, int translate)
	{
		super(interf, subtitles);
		
		this.subtitleIndex = subtitleIndex;
		this.selModel = selModel;
		this.translate = translate;
		this.firstSelIndex = selModel.getAnchorSelectionIndex();
		this.secondSelIndex = selModel.getLeadSelectionIndex();
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
	
	private void executeTranslate(int translate){
		int start = Math.max(0, firstSelIndex);
		int end = Math.min(secondSelIndex, this.subtitles.size() - 1);
		for (int i = start; i <= end; i++)
		{
			SubtitleItem item = this.subtitles.get(i);
			item.setStart(item.getStart() + translate);
			item.setEnd(item.getEnd() + translate);
		}
	}
	
	@Override
	protected boolean internalExecute()
	{
		this.executeTranslate(this.translate);
		interf.manipulateEditPanelValues(subtitles.getStart(subtitleIndex), subtitles.getEnd(subtitleIndex), subtitles.getDuration(subtitleIndex), subtitles.getSubtitleHTMLFormattedText(subtitleIndex, false));
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		this.executeTranslate(-this.translate);
		return true;
	}

}
