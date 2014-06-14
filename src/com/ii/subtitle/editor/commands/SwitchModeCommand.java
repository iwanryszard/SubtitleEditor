package com.ii.subtitle.editor.commands;

import com.ii.subtitle.model.Subtitles;

public class SwitchModeCommand extends AbstractSubtitlesCommand
{
	private double framesPerSec;
	private boolean isInFrames;
	private Subtitles.SavedState savedState;

	private void switchMode()
	{
		this.subtitles.setInFrames(this.isInFrames);
		this.subtitles.setFrameRatePerSecond(framesPerSec);
		double factor = this.isInFrames ? (framesPerSec / 1000) : (1000 / framesPerSec);
		for (int i = 0; i < this.subtitles.size(); i++)
		{
			int itemStart = (int) Math.round(this.subtitles.getStart(i) * factor);
			int itemEnd = (int) Math.round(this.subtitles.getEnd(i) * factor);
			this.subtitles.setStart(i, itemStart);
			this.subtitles.setEnd(i, itemEnd);
		}
	}

	public SwitchModeCommand(SelectionModel model, Subtitles subtitles, double framesPerSec, boolean isFrames)
	{
		super(model, subtitles);
		this.framesPerSec = framesPerSec;
		this.isInFrames = isFrames;
	}

	@Override
	protected boolean internalExecute()
	{
		if (this.subtitles.size() == 0 || this.subtitles.isInFrames() == this.isInFrames)
		{
			return false;
		}

		this.savedState = this.subtitles.createSavedState();
		this.switchMode();
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		this.subtitles.restoreToState(this.savedState);
		return true;
	}

}
