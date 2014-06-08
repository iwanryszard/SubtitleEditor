package com.ii.subtitle.editor.commands;

import com.ii.subtitle.editor.SubtitleItem;
import com.ii.subtitle.editor.Subtitles;
import com.ii.subtitle.editor.TextLeaf;

public class AddNewCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private int firstSelIndex;
	private int secondSelIndex;
	
	public AddNewCommand(SelectionModel model, Subtitles subtitles, int subtitleIndex)
	{
		super(model, subtitles);
		this.subtitleIndex = subtitleIndex;
		this.firstSelIndex = model.getStartSelectionIndex();
		this.secondSelIndex = model.getEndSelectionIndex();
	}
	
	@Override
	protected boolean internalExecute()
	{
		subtitles.add(subtitleIndex,  new SubtitleItem(0, 0,  new TextLeaf("")));
		this.model.setSelection(subtitleIndex, subtitleIndex);
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		subtitles.remove(subtitleIndex);
		this.model.setSelection(firstSelIndex, secondSelIndex);
		return true;
	}

}
