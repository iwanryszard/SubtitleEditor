package com.ii.subtitle.editor.commands;

import com.ii.subtitle.model.Subtitles;

public class SwitchModeCommand extends AbstractSubtitlesCommand
{
	private double framesPerSec;
	private double framesPerSecOriginal;
	private boolean isInFrames;

	public SwitchModeCommand(SelectionModel model, Subtitles subtitles, double framesPerSec, boolean isFrames)
	{
		super(model, subtitles);
		this.framesPerSecOriginal = subtitles.getFrameRatePerSecond();
		this.framesPerSec = framesPerSec;
		this.isInFrames = isFrames;
	}

	@Override
	protected boolean internalExecute()
	{
		if (this.subtitles.isInFrames() == this.isInFrames)
		{
			return false;
		}

		this.subtitles.setFrameRatePerSecond(this.framesPerSec);
		this.model.setFrameRatePerSecond(this.framesPerSec);
		this.subtitles.setInFrames(this.isInFrames);
		
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		this.subtitles.setFrameRatePerSecond(this.framesPerSecOriginal);
		this.model.setFrameRatePerSecond(this.framesPerSec);
		this.subtitles.setInFrames(!this.isInFrames);
		
		return true;
	}

}
