package com.ii.subtitle.editor.commands;

import com.ii.subtitle.model.Subtitles;

public class InterpolateCommand extends AbstractSubtitlesCommand
{
	private int firstSelIndex;
	private int secondSelIndex;
	private int start;
	private int end;
	private Subtitles.SavedState savedState;
	
	private void interpolate(int start, int startIndex, int end, int count)
	{
		
		startIndex = Math.max(0, startIndex);
		int endIndex = Math.min(startIndex + count - 1, this.subtitles.size() - 1);
		
		int oldStart = this.subtitles.getStart(startIndex);
		int oldEnd = this.subtitles.getStart(endIndex);
		
		//start = a * oldStart + b
		//end = a * oldEnd + b
		double a = (end - start) / (double) (oldEnd - oldStart);
		double b = end - a * oldEnd;
		for (int i = startIndex; i <= endIndex; i++)
		{
			int itemStart = (int) Math.round(this.subtitles.getStart(i) * a + b);
			int itemEnd = (int) Math.round(this.subtitles.getEnd(i) * a + b);
			this.subtitles.setStart(i, itemStart);
			this.subtitles.setEnd(i, itemEnd);
		}
	}
	
	public InterpolateCommand(SelectionModel model, Subtitles subtitles, int start, int end)
	{
		super(model, subtitles);
		
		this.start = start;
		this.end = end;
		this.firstSelIndex = model.getStartSelectionIndex();
		this.secondSelIndex = model.getEndSelectionIndex();
	}
	
	@Override
	protected boolean internalExecute()
	{
		if (start >= end)
		{
			return false;
		}
		
		if (firstSelIndex >= secondSelIndex || firstSelIndex == -1 || secondSelIndex == -1){
			return false;
		}
		
		this.savedState = this.subtitles.createSavedState();
		this.interpolate(start, firstSelIndex, end, secondSelIndex);
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		this.subtitles.restoreToState(this.savedState);
		return true;
	}

}
