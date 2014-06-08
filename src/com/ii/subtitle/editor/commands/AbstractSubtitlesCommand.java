package com.ii.subtitle.editor.commands;

import com.ii.subtitle.editor.Command;
import com.ii.subtitle.editor.Subtitles;

public abstract class AbstractSubtitlesCommand implements Command
{
	protected SelectionModel model;
	protected Subtitles subtitles;

	public AbstractSubtitlesCommand(SelectionModel model, Subtitles subtitles)
	{
		this.model = model;
		this.subtitles = subtitles;
	}

	protected abstract boolean internalExecute();

	protected abstract boolean internalUndo();

	@Override
	public boolean execute()
	{
		boolean result = internalExecute();
		return result;
	}

	@Override
	public boolean undo()
	{
		boolean result = internalUndo();
		return result;
	}

	public interface SelectionModel
	{
		int getStartSelectionIndex();

		int getEndSelectionIndex();

		void setSelection(int start, int end);
	}

}
