package com.ii.subtitle.editor.commands;

import java.util.ArrayList;
import java.util.List;

import com.ii.subtitle.model.SubtitleItem;
import com.ii.subtitle.model.Subtitles;


public class DeleteCommand extends AbstractSubtitlesCommand
{
	private List<SubtitleItem> removedItems;
	private int firstSelIndex;
	private int secondSelIndex;
	
	public DeleteCommand(SelectionModel model, Subtitles subtitles)
	{
		super(model, subtitles);
		
		this.firstSelIndex = model.getStartSelectionIndex();
		this.secondSelIndex = model.getEndSelectionIndex();
	}

	@Override
	protected boolean internalExecute()
	{
		List<SubtitleItem> range = subtitles.getItems().subList(this.firstSelIndex, this.secondSelIndex);
		removedItems = new ArrayList<>(range);
		range.clear();
		model.setSelection(-1, -1);
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		subtitles.getItems().addAll(this.firstSelIndex, removedItems);
		model.setSelection(firstSelIndex, secondSelIndex);
		return true;
	}
}
