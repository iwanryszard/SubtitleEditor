package com.ii.subtitle.editor.commands;

import java.util.ArrayList;
import java.util.List;

import com.ii.subtitle.editor.SubtitleItem;
import com.ii.subtitle.editor.Subtitles;


public class DeleteCommand extends AbstractSubtitlesCommand
{
	private int startSubtitle;
	private int selectedCount;
	private List<SubtitleItem> removedItems;
	
	public DeleteCommand(Subtitles subtitles, int startSubtitle, int selectedCount)
	{
		super(subtitles);
		
		this.startSubtitle = startSubtitle;
		this.selectedCount = selectedCount;
	}

	@Override
	protected boolean internalExecute()
	{
		List<SubtitleItem> range = subtitles.subList(startSubtitle, startSubtitle + selectedCount);
		removedItems = new ArrayList<>(range);
		range.clear();
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		subtitles.addAll(startSubtitle, removedItems);
		return true;
	}
}
