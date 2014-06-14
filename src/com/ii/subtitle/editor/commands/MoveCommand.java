package com.ii.subtitle.editor.commands;

import java.util.Collections;

import com.ii.subtitle.model.Subtitles;

public class MoveCommand extends AbstractSubtitlesCommand
{
	private int startSubtitle;
	private int selectedCount;
	private int direction; // +/-1

	public MoveCommand(SelectionModel model, Subtitles subtitles, int direction)
	{
		super(model, subtitles);

		this.direction = direction != 1 ? -1 : direction;
		this.startSubtitle = model.getStartSelectionIndex();
		this.selectedCount = model.getEndSelectionIndex() - this.startSubtitle + 1;
	}

	@Override
	protected boolean internalExecute()
	{
		moveSubtitles(startSubtitle, selectedCount, direction);
		model.setSelection(startSubtitle + direction, startSubtitle + selectedCount - 1 + direction);
		return true;
	}

	private void moveSubtitles(int startSubtitle, int count, int direction)
	{
		int endIndex = startSubtitle + count - 1;
		int start = (direction < 0 ? startSubtitle : endIndex) + direction;
		int end = direction < 0 ? endIndex : startSubtitle;
		int i = start;
		while (i != end)
		{
			Collections.swap(subtitles.getItems(), i, i - direction);
			i -= direction;
		}
	}

	@Override
	protected boolean internalUndo()
	{
		moveSubtitles(startSubtitle + direction, selectedCount, -direction);
		model.setSelection(this.startSubtitle, this.startSubtitle + this.selectedCount - 1);
		return true;
	}
}
