package com.ii.subtitle.editor;

public class SwitchToFramesCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private double framesPerSec;

	public SwitchToFramesCommand(Interface interf, Subtitles subtitles, int subtitleIndex, double framesPerSec)
	{
		super(interf, subtitles);

		this.subtitleIndex = subtitleIndex;
		this.framesPerSec = framesPerSec;
	}

	@Override
	protected boolean internalExecute()
	{
		interf.isFrames = true;

		subtitles.setFrameRatePerSecond(framesPerSec);
		subtitles.switchToFrames();

		interf.manipulateRadioButtonsValues(false, true);
		if (subtitleIndex >= 0 && subtitleIndex < subtitles.getCount())
		{
			interf.manipulateEditPanelValues(subtitles.getStart(subtitleIndex), subtitles.getEnd(subtitleIndex),
					subtitles.getDuration(subtitleIndex), subtitles.getSubtitleHTMLFormattedText(subtitleIndex, false));
		}
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		return true;
	}

}
