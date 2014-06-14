package com.ii.subtitle.editor.commands;

import com.ii.subtitle.model.SubtitleItem;
import com.ii.subtitle.model.Subtitles;

public class TranslateCommand extends AbstractSubtitlesCommand
{
	private int firstSelIndex;
	private int secondSelIndex;
	private int translate;
	
	public TranslateCommand(SelectionModel model, Subtitles subtitles, int translate)
	{
		super(model, subtitles);
		
		this.translate = translate;
		this.firstSelIndex = model.getStartSelectionIndex();
		this.secondSelIndex = model.getEndSelectionIndex();
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
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		this.executeTranslate(-this.translate);
		return true;
	}

}
