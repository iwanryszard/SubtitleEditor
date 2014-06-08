package com.ii.subtitle.editor;

import java.util.ArrayList;
import java.util.List;


public class DeleteCommand extends AbstractSubtitlesCommand
{
	private int startSubtitle;
	private int selectedCount;
	private List<SubtitleItem> removedItems;
	
	public DeleteCommand(Interface interf, Subtitles subtitles, int startSubtitle, int selectedCount)
	{
		super(interf, subtitles);
		
		this.startSubtitle = startSubtitle;
		this.selectedCount = selectedCount;
	}

	@Override
	protected boolean internalExecute()
	{
		List<SubtitleItem> range = subtitles.subList(startSubtitle, startSubtitle + selectedCount);
		removedItems = new ArrayList<>(range);
		range.clear();
		interf.manipulateEditPanelValues("", "", "", "");
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		subtitles.addAll(startSubtitle, removedItems);
		return true;
	}
}
